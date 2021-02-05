package lorienlegacies.legacies.implementations;

import java.util.List;
import java.util.Map;

import lorienlegacies.legacies.Legacy;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class Glacen extends Legacy
{
	private static final float DISTANCE = 5.0f;
	private static final float LEVEL_2_DISTANCE_MODIFIER = 2.0f;
	private static final int AURA_SIZE = 5;
	private static final int SNOW_RADIUS = 15;
	
	public Glacen(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Glacen";
		DESCRIPTION = "Grants ice powers";
		STAMINA_PER_TICK = 1;
		
		AddLevel("Frost touch", 1200);
		AddLevel("Greater range", 1800);
		AddLevel("Ice aura", 2200);
		AddLevel("Freeze wave", 3000);
	
		legacyAbilities.put(new LegacyAbility("Freeze wave", 4), NAME);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		
		// Work out distance
		float distance = DISTANCE;
		if (level >= 2) distance *= LEVEL_2_DISTANCE_MODIFIER;
		
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
		
		// For ice aura, get entities around player's (enlarged) bounding box
		if (level >= 3)
		{
			AxisAlignedBB boundingBox = player.getBoundingBox().grow(AURA_SIZE);
			List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, boundingBox, e -> { return e instanceof LivingEntity; });
			entities.forEach(e -> {
				
				LivingEntity livingEntity = (LivingEntity)e;
				
				// Effects and health
				livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 5));
				livingEntity.addPotionEffect(new EffectInstance(Effects.GLOWING, 2, 5));
				livingEntity.setHealth(livingEntity.getHealth() - 0.5f);
				
				// Sounds and particles
				if (livingEntity.world.getGameTime() % 10 == 0 && livingEntity.getHealth() > 0)
				{
					livingEntity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
					
					Vector3d position = e.getPositionVec().add(0.0f, e.getHeight(), 0.0f);
					int particleCount = 256;
					((ServerWorld)player.world).spawnParticle(ParticleTypes.SPLASH, position.x, position.y, position.z, particleCount, 0, 0, 0, 0);
				}
			});
		}
	}
	
	@Override
	public float GetAbilityStamina(String ability) { return 50; }
	
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		// Check it's raining
		if (player.world.isRaining() == false)
		{
			if (player.world.isRemote == false) player.sendMessage(new StringTextComponent("§cIt is not raining, and you find yourself without the water source you need."), player.getUniqueID());
			return;
		}
		
		// Particles
		Vector3d position = player.getEyePosition(1);
		double speed = 1.0f;
		int particleCount = 100;
		((ServerWorld)player.world).spawnParticle(ParticleTypes.CLOUD, position.x, position.y, position.z, particleCount, 0, 0, 0, speed);
		
		// Snowballs
		for (int x = -SNOW_RADIUS; x < SNOW_RADIUS; ++x)
		{
			for (int y = -SNOW_RADIUS; y < SNOW_RADIUS; ++y)
			{
				// We want to make a circle, so avoid x's and y's outside radius
				int squareDistance = x*x + y*y; // It's Pythagoras, mate
				if (squareDistance >= SNOW_RADIUS*SNOW_RADIUS) continue;
				
				player.world.addEntity(new SnowballEntity(player.world, position.x + x, position.y, position.z + y)
				{
					@Override
					protected void onImpact(RayTraceResult result)
					{
						// It'd do us good to avoid replacing non-air blocks!
						if (Blocks.SNOW.isAir(player.world.getBlockState(new BlockPos(result.getHitVec())), player.world, new BlockPos(result.getHitVec())) == false) return;
						
						// We can't play too many sounds, as that'd fill the sound pool
						this.playSound(SoundEvents.BLOCK_SNOW_FALL, 1.0f, 1.0f);
						
						if (this.world.isRemote == false)
						{
							this.world.setBlockState(new BlockPos(result.getHitVec()), Blocks.SNOW.getDefaultState());
							this.onKillCommand();
						}
					}
				});
			}
		}
				
		// Play sound (only to player)
		SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY() + 10.0f, player.getPosZ());
		entity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
		entity.onKillCommand();
	}
	
}
