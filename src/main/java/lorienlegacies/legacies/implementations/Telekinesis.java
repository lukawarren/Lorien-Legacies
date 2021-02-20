package lorienlegacies.legacies.implementations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lorienlegacies.legacies.Legacy;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Telekinesis extends Legacy
{	
	private static final float ENTITY_DISTANCE = 20.0f; // EXTREMELY imprecise - doesn't work
	private static final float BLOCK_DISTANCE = 5.0f;
	Map <UUID, UUID> launchedEntities = new HashMap<>(); // Player, entity
	Map <UUID, Integer> selectedEntities = new HashMap<>(); // So as to prevent picking up stuff by accident
	
	public Telekinesis(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Telekinesis";
		DESCRIPTION = "Grants mind powers";
		STAMINA_PER_TICK = 2;
		
		GENERATION_WEIGHTING = 0;
		GENERATION_POINTS = 0;
		
		BOOK_DESCRIPTION = "Telekinesis allows you to pick up and move mobs with your mind. As you gain more experience with it, you will be able to launch mobs, move blocks, and even players.";
		
		AddLevel("Levitate mobs", 1000);
		AddLevel("Launch mobs", 2000);
		AddLevel("Move blocks", 3000);
		AddLevel("Move players", 5000);
		
		legacyAbilities.put(new LegacyAbility("Telekinesis throw", 2), NAME);
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		Entity entity = GetEntityLookedAt(player);
		
		if (entity != null)
		{
			// Ignore blacklisted enemies for one tick
			if (launchedEntities.containsKey(player.getUniqueID()) && launchedEntities.get(player.getUniqueID()).equals(entity.getUniqueID()))
			{
				launchedEntities.remove(player.getUniqueID());
				return;
			}
			
			// Move entity to player's look position
			Vector3d desiredPosition = player.getEyePosition(1).add(player.getLookVec().scale(2));
			Vector3d currentPosition = entity.getPositionVec();
			Vector3d appliedVelocity = desiredPosition.subtract(currentPosition).scale(1.0f);
			entity.setMotion(appliedVelocity.x, appliedVelocity.y, appliedVelocity.z);
			
			// Sync
			((ServerPlayerEntity) player).connection.sendPacket(new SEntityVelocityPacket(entity));
			
			selectedEntities.put(player.getUniqueID(), entity.getEntityId());
		}
		else if (level >= 3)
		{
			// Get block player is looking at
			Vector3d startVec = player.getEyePosition(1.0f).add(player.getLookVec().mul(2.0f, 2.0f, 2.0f)); // Add 1 block minimum distance
			Vector3d endVec = startVec.add(player.getLookVec().scale(BLOCK_DISTANCE));
			BlockRayTraceResult rayResult = player.world.rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
			
			if (rayResult == null || rayResult.getPos() == null || player.world.getBlockState(rayResult.getPos()) == null) return;
			
			// Get eye position and block state
			BlockState blockState = player.world.getBlockState(rayResult.getPos());
			Vector3d position = player.getEyePosition(1).add(player.getLookVec().scale(2));
			
			// Make falling sand (woops, I mean falling block) at player's eye position
			FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(player.world, position.x, position.y, position.z, blockState);
			fallingBlockEntity.fallTime = 2;
			fallingBlockEntity.shouldDropItem = true;
			
			// Replace old block
			player.world.addEntity(fallingBlockEntity);
			player.world.removeBlock(rayResult.getPos(), false);
			
			selectedEntities.put(player.getUniqueID(), fallingBlockEntity.getEntityId());
		}
	}
	
	/*
	 * Detect when un-toggled
	 */
	@SubscribeEvent
	public void OnLivingUpdateEvent(LivingUpdateEvent event)
	{
		if (event.getEntity() != null && event.getEntity().world.isRemote == false && event.getEntity() instanceof PlayerEntity)
		{
			selectedEntities.remove(((PlayerEntity)event.getEntity()).getUniqueID());
		}
	}
	
	private boolean IsValidEntity(int level, Entity e)
	{
		return (e instanceof PlayerEntity == false || level >= 4) && (e instanceof LivingEntity || e instanceof FallingBlockEntity);
	}
	
	private Entity GetEntityLookedAt(PlayerEntity player)
	{
		if (selectedEntities.containsKey(player.getUniqueID())) return player.world.getEntityByID(selectedEntities.get(player.getUniqueID()));
		
		int level = GetLegacyLevel(player);
		
		Vector3d rayStart = player.getEyePosition(1);
		Vector3d rayEnd = rayStart.add(player.getLookVec().scale(ENTITY_DISTANCE));
		EntityRayTraceResult result = ProjectileHelper.rayTraceEntities(player.world, player, rayStart, rayEnd, player.getBoundingBox().grow(ENTITY_DISTANCE), (e) -> { return IsValidEntity(level, e); });
		
		if (result == null) return null;
		if (result.getEntity() == null) return null;
		return result.getEntity();
	}
	
	@Override
	public float GetAbilityStamina(String ability) { return 50; }
	
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		// Launch mob (if any)
		Entity entity = GetEntityLookedAt(player);
		if (entity != null)
		{
			Vector3d launchVelocity = player.getLookVec().scale(4.0f);
			entity.addVelocity(launchVelocity.x, launchVelocity.y, launchVelocity.z);
			
			// Add to blacklist
			launchedEntities.put(player.getUniqueID(), entity.getUniqueID());
			
			// Sync
			((ServerPlayerEntity) player).connection.sendPacket(new SEntityVelocityPacket(entity));
		}
		else
		{
			player.sendMessage(new StringTextComponent("§cYou are not holding anything"), player.getUniqueID());
		}
	}
}
