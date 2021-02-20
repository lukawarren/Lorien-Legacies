package lorienlegacies.legacies.implementations;

import java.util.Map;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Submari extends Legacy
{
	private static final float WATER_VORTEX_FORCE = 5.0f;
	
	public Submari(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Submari";
		DESCRIPTION = "Grants water powers";
		STAMINA_PER_TICK = 0; // Does not need to be toggled
		
		BOOK_DESCRIPTION = "With Submari, you are one with water. You have no problems breathing when submerged, and will learn to see great distances and swim at great speeds.";
		
		AddLevel("Water breathing", 12000);
		AddLevel("Water vision", 16000);
		AddLevel("Greater speed", 20000);
		AddLevel("Water vortex", 22000);
		
		legacyAbilities.put(new LegacyAbility("Water vortex", 4), NAME);
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		
		player.setAir(player.getMaxAir()); // Water breathing
		
		if (level >= 3 && player.isActualySwimming()) // Greater speed
		{
			player.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 5, 5, false, false));
		}
	}
	
	@Override
	public float GetAbilityStamina(String ability) { return 50; }
	
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		// Check the player is underwater
		if (player.isInWater() == false) return;
		
		Vector3d velocity = player.getLookVec().scale(WATER_VORTEX_FORCE);
		player.setMotion(velocity.x, velocity.y, velocity.z);
		
		// Notify the client
		((ServerPlayerEntity) player).connection.sendPacket(new SEntityVelocityPacket(player));
		
		// The toggle GUI stops us swimming!
		player.setSwimming(true);
		player.setSprinting(true);
		
		// Shoot particle ray from player by marching along vector
		Vector3d playerPosition = player.getPositionVec();
		Vector3d bubblePosition = playerPosition.add(player.getLookVec().scale(1000.0f));
		Vector3d substepPosition = new Vector3d(playerPosition.x, playerPosition.y, playerPosition.z);
		final int substeps = 1000;
				
		for (int i = 0; i < substeps; ++i)
		{
			substepPosition = substepPosition.add((bubblePosition.x - playerPosition.x) / substeps, (bubblePosition.y - playerPosition.y) / substeps, (bubblePosition.z - playerPosition.z) / substeps);
			((ServerWorld)player.world).spawnParticle(ParticleTypes.CLOUD, substepPosition.x, substepPosition.y, substepPosition.z, 10, 0, 0, 0, 0);
		}
		
		// Play sound
		SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY() + 10.0f, player.getPosZ());
		entity.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, 10.0f, 1.0f);
		entity.onKillCommand();
	}
	
	private boolean IsSwimmingAndOfRequiredLevel(Entity entity)
	{
		if (entity instanceof PlayerEntity == false || entity == null) return false;
		PlayerEntity player = (PlayerEntity)entity;
				
		// We are on the client so levels work a bit differently
		int level = LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(NAME);
		return level >= 2 && player.getEntity().isInWater();
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void FogDensityEvent(EntityViewRenderEvent.FogDensity event)
	{
		if (!IsSwimmingAndOfRequiredLevel(event.getInfo().getRenderViewEntity())) return;
		
		event.setDensity(0.01f);
		event.setCanceled(true);
		
	}
	
}
