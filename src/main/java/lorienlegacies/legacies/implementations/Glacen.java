package lorienlegacies.legacies.implementations;

import java.util.Map;

import lorienlegacies.legacies.Legacy;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class Glacen extends Legacy
{
	private static final float DISTANCE = 5.0f;
	private static final float LEVEL_2_DISTANCE_MODIFIER = 2.0f;
	
	public Glacen(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Glacen";
		DESCRIPTION = "Grants ice powers";
		STAMINA_PER_TICK = 1;
		
		AddLevel("Frost touch", 1200);
		AddLevel("Greater range", 1800);
		AddLevel("Ice spike", 2200);
		AddLevel("Freeze wave", 3000);
	
		legacyAbilities.put(new LegacyAbility("Freeze wave", 4), NAME);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		// Work out distance
		float distance = DISTANCE;
		if (GetLegacyLevel(player) >= 2) distance *= LEVEL_2_DISTANCE_MODIFIER;
		
		// Get block player is looking at
		Vector3d startVec = player.getEyePosition(1.0f).add(player.getLookVec().mul(2.0f, 2.0f, 2.0f)); // Add 1 block minimum distance
		Vector3d endVec = startVec.add(player.getLookVec().scale(distance));
		BlockRayTraceResult rayResult = player.world.rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		
		if (rayResult == null || rayResult.getPos() == null || player.world.getBlockState(rayResult.getPos()) == null) return;
		
		// Set the block to ice if it's water
		if (player.world.getBlockState(rayResult.getPos()).getMaterial() == Material.WATER)
		{
			player.world.setBlockState(rayResult.getPos(), Blocks.ICE.getDefaultState());
				
			// Play sound audible to everyone by making a new entity at the position of player and making it play the sound
			SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ());
			entity.playSound(SoundEvents.ENTITY_BOAT_PADDLE_WATER, 1f, 2f);
			entity.onKillCommand();
		}
		
		OnAbility("", player);
	}
	
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		// Ice spike
		Vector3d position = player.getEyePosition(1);
		double speed = 1.0f;
		int particleCount = 100;
		((ServerWorld)player.world).spawnParticle(ParticleTypes.CLOUD, position.x, position.y, position.z, particleCount, 0, 0, 0, speed);
		
		// Play sound (only to player)
		SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY() + 10.0f, player.getPosZ());
		entity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
		entity.onKillCommand();
	}
	
}
