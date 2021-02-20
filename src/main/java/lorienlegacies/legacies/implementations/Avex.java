package lorienlegacies.legacies.implementations;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToFindMethodException;

public class Avex extends Legacy
{

	private static final int RANGE = 5;
	private static final int FLY_SPEED = 3;
	private static final float LEVEL_2_SPEED_INCREASE = 1.5f;
	private static final float UPDRAFT_FORCE = 3.0f;
	
	private Method setFlagMethod = null;
	private Method getFlagMethod = null;
	private HashMap<UUID, Boolean> flyingFlags = new HashMap<>();
	
	public Avex(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Avex";
		DESCRIPTION = "Grants swift flight";
		STAMINA_PER_TICK = 1;
		
		BOOK_DESCRIPTION = "Take to the skies and soar in the air.";
		
		GENERATION_WEIGHTING = 1;
		GENERATION_POINTS = 3;
		
		AddLevel("Flight", 1200);
		AddLevel("Greater speed", 1800);
		AddLevel("Flight instinct negating all fall damage", 2200);
		AddLevel("Air updraft", 3000);
		
		legacyAbilities.put(new LegacyAbility("Air updraft", 4), NAME);
		
		MinecraftForge.EVENT_BUS.register(this); // Need to receive events
	
		// Reflection
		try
		{
			setFlagMethod = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70052_a", int.class, boolean.class);
			setFlagMethod.setAccessible(true);
			
			getFlagMethod = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70083_f", int.class);
			getFlagMethod.setAccessible(true);
		} catch (UnableToFindMethodException e)
		{
			LorienLegacies.logger.error("Unable to find method Entity#setFlag and/or Entity#getFlag for Pondus! Pondus will not have its flying animation applied.");
		}
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		// Get nearest obstruction by casting 6 rays, one for each direction
		BlockRayTraceResult results[] = new BlockRayTraceResult[6];
		results[0] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, 0, RANGE), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[1] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, 0, -RANGE), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[2] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(-RANGE, 0, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[3] =  player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(RANGE, 0, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[4] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, -RANGE, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[5] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, RANGE, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		
		// Get nearest ray result
		Vector3i playerPositionAsInt = new Vector3i(player.getPosX(), player.getPosY(), player.getPosZ());
		double nearestSquaredDistance = Double.MAX_VALUE;
		for (int i = 0; i < results.length; ++i)
		{
			if (results[i] != null && results[i].getPos() != null)
			{
				double distance = results[i].getPos().distanceSq(playerPositionAsInt);
				if (distance < nearestSquaredDistance) nearestSquaredDistance = distance;
			}
		}
		
		nearestSquaredDistance = Math.max(Math.sqrt(nearestSquaredDistance), 1.0);
		
		// Get player's looking direction and scale with fly speed
		float flySpeed = FLY_SPEED;
		if (GetLegacyLevel(player) >= 2) flySpeed *= LEVEL_2_SPEED_INCREASE;
		Vector3d lookDirection = player.getLookVec().scale(player.abilities.getFlySpeed() * flySpeed * nearestSquaredDistance);
		
		// Apply velocity to player
		player.setMotion(lookDirection.x, lookDirection.y, lookDirection.z);
		player.isAirBorne = true;
		
		// With players, we must notify the client of this change
		((ServerPlayerEntity) player).connection.sendPacket(new SEntityVelocityPacket(player));
	}
	
	@SubscribeEvent
	public void OnLivingAttackEvent(LivingAttackEvent event)
	{
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		// Check the poor sod is a player
		if (event.getEntity() instanceof PlayerEntity == false) return;
		
		// If player does not have Avex, return
		if (GetLegacyLevel((PlayerEntity)event.getEntity()) == 0) return;
		
		// If player does not have Avex toggled and is less than level 3, return
		if (IsLegacyToggled((PlayerEntity)event.getEntity()) == false && GetLegacyLevel((PlayerEntity)event.getEntity()) < 3) return;
		
		// Cancel fall damage
		if (event.getSource().equals(DamageSource.FALL))
		{
			event.setCanceled(true);	
		
			// De-toggle legacy
			if (IsLegacyToggled((PlayerEntity)event.getEntity())) Toggle((PlayerEntity)event.getEntity());
		}
	}
	
	@Override
	public float GetAbilityStamina(String ability) { return 50; }
	
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		// Air updraft
		player.addVelocity(0.0f, UPDRAFT_FORCE, 0.0f);	
		((ServerPlayerEntity) player).connection.sendPacket(new SEntityVelocityPacket(player)); // Notify client
		
		// Play sound
		SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY() + 10.0f, player.getPosZ());
		entity.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, 10.0f, 1.0f);
		entity.onKillCommand();
	}
	
	/*
		Flying animation
	*/
	@SubscribeEvent
	public void OnPreRenderPlayer(RenderPlayerEvent.Pre event)
	{
		if (event.getEntity() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity)event.getEntity();
		
		// Keep track of toggle status
		boolean toggled = player.world.isRemote ? LorienLegacies.proxy.GetClientLegacyData().IsLegacyToggled(NAME) : IsLegacyToggled(player);
		boolean toggledLastFrame = flyingFlags.containsKey(player.getUniqueID()) ? flyingFlags.get(player.getUniqueID()) : false;
		flyingFlags.put(player.getUniqueID(), toggled);
		
		// On toggle (or if we should be flying but aren't), chance flying status (we cannot set this every tick or ElytraSound#tick tops itself)
		if ((toggledLastFrame != toggled && toggled) || (player.isElytraFlying() == false && toggled))
		{
			try {
				setFlagMethod.invoke(player, 7, true);
			} catch (Exception e) { LorienLegacies.logger.error("Pondus failed to call Entity#setFlag!"); e.printStackTrace(); }
		}
		else if (toggledLastFrame != toggled && !toggled)
		{
			try {
				setFlagMethod.invoke(player, 7, false);
			} catch (Exception e) { LorienLegacies.logger.error("Pondus failed to call Entity#setFlag!"); e.printStackTrace(); }
		}
		
		/*
		if (setFlagMethod != null && getFlagMethod != null)
		{
			try {
				boolean flag = (boolean) getFlagMethod.invoke(player, 7);
				flyingFlags.put(player.getUniqueID(), flag);
				
				// To avoid a sound bug(which I think occurs in ElytraSound#tick), we must only do this if airborne
				//if (player.isOnGround() == false)
					setFlagMethod.invoke(player, 7, true);
				
			} catch (Exception e) { LorienLegacies.logger.error("Pondus failed to call Entity#getFlag and/or Entity#setFlag!"); e.printStackTrace(); }
		}*/
	}
	
	/*
		Flying animation
	*/
	@SubscribeEvent
	public void OnPostRenderPlayer(RenderPlayerEvent.Post event)
	{
		if (event.getEntity() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity)event.getEntity();
		
		if (setFlagMethod != null && getFlagMethod != null)
		{
			try {
				//setFlagMethod.invoke(player, 7, flyingFlags.get(player.getUniqueID()));
			} catch (Exception e) { LorienLegacies.logger.error("Pondus failed to call Entity#setFlag!"); e.printStackTrace(); }
		}
	}
}
