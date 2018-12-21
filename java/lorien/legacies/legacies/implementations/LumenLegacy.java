package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent.Fireball;

public class LumenLegacy extends Legacy {
	
	public LumenLegacy()
	{
		LEGACY_NAME = "Lumen";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
		
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("fire_resistance"), 1, 255, true, false));
		//player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 11, 10, true, false));
	}
	
	
	public void fireball(Entity playerEntity)
	{
		
		// Summon the fireball
		EntityFireball fireball = new EntityFireball(playerEntity.world) {
			
			@Override
			protected void onImpact(RayTraceResult result) {
				this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
				if (this.world.isRemote == false && result.entityHit != null)
				{
					result.entityHit.setFire(5);
				}
			}
		};
		
		// Position the fireball
		fireball.posX = playerEntity.posX;
		fireball.posY = playerEntity.posY;
		fireball.posZ = playerEntity.posZ;
		
		Vec3d looking = playerEntity.getLookVec();
		if (looking != null) {
			fireball.motionX = looking.x;
			fireball.motionY = looking.y;
			fireball.motionZ = looking.z;
			fireball.accelerationX = fireball.motionX * 0.1D;
			fireball.accelerationY = fireball.motionY * 0.1D;
			fireball.accelerationZ = fireball.motionZ * 0.1D;
		}
		
		if (playerEntity.world.isRemote == false)
		{
			playerEntity.world.spawnEntity(fireball);
			playerEntity.setFire(5);
		}
		
		fireball.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0f, 1.0f); // According to docs, if called by a PlayerEntity it plays the sound to everyone *except* the player. Like what the frick?
	}
	
}
