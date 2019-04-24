package lorien.legacies.legacies.implementations;

import java.util.UUID;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LumenLegacy extends Legacy {

	
	public LumenLegacy()
	{
		LEGACY_NAME = "Lumen";
		DESCRIPTION ="grants fire resistance and fire powers";
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
		
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("fire_resistance"), 1, 255, true, false));
		//player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 11, 10, true, false));
	}
	
	// Immune to burning
	@SubscribeEvent
	public void onBurnDamage(LivingAttackEvent event)
	{
	    if(event.getEntity() instanceof EntityPlayer)
	    {
	        EntityPlayer player = (EntityPlayer)event.getEntity();
	    	if(event.getSource().equals(DamageSource.LAVA) || event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE))
	    	{
	            
	    		if (LorienLegacies.legacyManagers.get(0).lumenLegacyEnabled && LorienLegacies.legacyManagers.get(0).legaciesEnabled)
	    			event.setCanceled(true);
	    		else
	    			event.setCanceled(false);
	    	} 
	       
	   }
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
