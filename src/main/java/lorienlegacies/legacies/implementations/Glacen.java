package lorienlegacies.legacies.implementations;

import java.util.Map;
import java.util.Random;

import lorienlegacies.legacies.Legacy;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Glacen extends Legacy
{
	private static final float DISTANCE = 5.0f;
	private static final float LEVEL_2_DISTANCE_MODIFIER = 2.0f;
	private static final float ICE_BOLT_DAMAGE = 5.0f;
	private static final int SNOW_RADIUS = 10;
	private static final int SNOW_HEIGHT_VARIATION = 2;
	
	private Random random = new Random();
	
	public Glacen(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Glacen";
		DESCRIPTION = "Grants ice powers";
		STAMINA_PER_TICK = 1;
		
		BOOK_DESCRIPTION = "Your aptitude for all things icey allows you to freeze bodies of water, from raindrops to oceans.";
		
		AddLevel("Frost touch", 1200);
		AddLevel("Greater range", 1800);
		AddLevel("Ice bolt", 2200);
		AddLevel("Frost wave", 3000);
		
		legacyAbilities.put(new LegacyAbility("Frost wave", 4), NAME);
	
		MinecraftForge.EVENT_BUS.register(this);
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
	}
	
	@Override
	public float GetAbilityStamina(String ability) { return 50; }
	
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		// Check it's raining
		if (player.world.isRaining() == false || player.world.getBiome(player.getPosition()).getPrecipitation() == RainType.NONE)
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
			for (int z = -SNOW_RADIUS; z < SNOW_RADIUS; ++z)
			{
				// We want to make a circle, so avoid x's and z's outside radius
				int squareDistance = x*x + z*z; // It's Pythagoras, mate
				if (squareDistance >= SNOW_RADIUS*SNOW_RADIUS) continue;
				
				// Decide on a y, and add some variation
				int y = random.nextInt(SNOW_HEIGHT_VARIATION + 1);
				if (random.nextBoolean()) y = -y;
				
				// If the block is not air, fall back!
				if (player.world.isAirBlock(new BlockPos(position.x + x, position.y, position.z + z)) == false)
					y = 0;
				
				player.world.addEntity(new SnowballEntity(player.world, position.x + x, position.y + y, position.z + z)
				{
					@Override
					protected void onImpact(RayTraceResult result)
					{
						// It'd do us good to avoid replacing non-air blocks!
						BlockPos pos = new BlockPos(result.getHitVec());
						BlockState state = player.world.getBlockState(pos);
						if (Blocks.SNOW.isAir(state, player.world, pos) == false && state.getBlock().getDefaultState()!= Blocks.FIRE.getDefaultState()) return;
							
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
	
	private void ShootIceBolt(PlayerEntity player, LivingEntity e)
	{
		// Shoot particle ray from player to entity by marching along vector
		Vector3d playerPosition = player.getPositionVec();
		Vector3d entityPosition = e.getPositionVec();
		Vector3d substepPosition = new Vector3d(playerPosition.x, playerPosition.y, playerPosition.z);
		final int substeps = 10;
		
		for (int i = 0; i < substeps; ++i)
		{
			substepPosition = substepPosition.add((entityPosition.x - playerPosition.x) / substeps, (entityPosition.y - playerPosition.y) / substeps, (entityPosition.z - playerPosition.z) / substeps);
			((ServerWorld)player.world).spawnParticle(ParticleTypes.SPLASH, substepPosition.x, substepPosition.y, substepPosition.z, 10, 0, 0, 0, 0);
		}
		
		// Health
		e.setHealth(e.getHealth() - ICE_BOLT_DAMAGE);
		
		// Sounds and particles
		if (e.getHealth() > 0)
		{
			e.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
				
			Vector3d position = e.getPositionVec().add(0.0f, e.getHeight(), 0.0f);
			final int particleCount = 256;
			((ServerWorld)player.world).spawnParticle(ParticleTypes.SPLASH, position.x, position.y, position.z, particleCount, 0, 0, 0, 0);
		}
	}
	
	@SubscribeEvent
	public void OnLivingAttackEvent(LivingAttackEvent event)
	{	
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		// Check it's a valid living entity
		if (event.getEntityLiving() == null) return;
		
		// Check the source is a player
		if (event.getSource().getTrueSource() == null || event.getSource().getTrueSource() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
		
		// If player does not have Glacen, return
		if (GetLegacyLevel(player) == 0) return;
		
		// If player's Glacen level is less than level 3, return
		if (GetLegacyLevel(player) < 3) return;
		
		ShootIceBolt(player, event.getEntityLiving());
	}
	
}
