package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import lorien.legacies.legacies.levels.LegacyLevel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LumenLegacy extends Legacy {
	
	public LumenLegacy()
	{
		LEGACY_NAME = "Lumen";
		DESCRIPTION ="grants fire resistance and fire powers";
		MinecraftForge.EVENT_BUS.register(this);
		
		// Levels - It will overwrite the "Base Level" Message
		legacyLevels.set(0, new LegacyLevel("Your hands glow with the power of lumen", 500));
		legacyLevels.add(new LegacyLevel("Your body becomes accustomed to the flame", 750));
		legacyLevels.add(new LegacyLevel("Focus your lumen into flaming projectiles", 1000));
		legacyLevels.add(new LegacyLevel("You no longer feel any burn", 1250));
		legacyLevels.add(new LegacyLevel("You can burn anything you hit", 1500));
		legacyLevels.add(new LegacyLevel("Flamethrower anyone?", 1750));
		legacyLevels.add(new LegacyLevel("Surround yourself in your flames", 2000));
		legacyLevels.add(new LegacyLevel("Cataclysm Burn everything in your path", 5000));
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		// don't need fire resistance if we cancel fire damage
		//if (player.isPotionActive(Potion.getPotionFromResourceLocation("fire_resistance")) == false)
			//player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("fire_resistance"), 1, 255, true, false));
	}
	
	// Immune to burning
	@SubscribeEvent
	public void onBurnDamage(LivingAttackEvent event)
	{
	    if(event.getEntity() instanceof EntityPlayer)
	    {
	        EntityPlayer player = (EntityPlayer)event.getEntity();
	    	
	        // Only cancel fire damage if greater than level 2
	        if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE)) {
	        	if (LorienLegacies.instance.legacyManagers.containsKey(player.getUniqueID()) && LorienLegacies.instance.legacyManagers.get(player.getUniqueID()).lumenLegacyEnabled && getEnabledInConfig()) {
	        		if (currentLegacyLevel >= 2) {
	        			event.setCanceled(true);
	        		}
	        	}
	        }
	        // Only cancel lava and dragon fire damage if level 4
	        else if (event.getSource().equals(DamageSource.LAVA) || event.getSource().equals(DamageSource.DRAGON_BREATH)) {
	        	if (LorienLegacies.instance.legacyManagers.containsKey(player.getUniqueID()) && LorienLegacies.instance.legacyManagers.get(player.getUniqueID()).lumenLegacyEnabled && getEnabledInConfig()) {
	        		if (currentLegacyLevel >= 4) {
	        			event.setCanceled(true);
	        		}
	        	}
	        }
	        
	        
	        
	        /*
	        if(event.getSource().equals(DamageSource.LAVA) || event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE))
	    	{
	    		if (LorienLegacies.legacyManagers.get(0).lumenLegacyEnabled)
	    			event.setCanceled(true);
	    		else
	    			event.setCanceled(false);
	    	} 
	    	*/  
	   }
	}
	
	// Throwing fireballs
	public void fireball(Entity playerEntity)
	{	
		if (currentLegacyLevel >= 3) {
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
				playerEntity.setFire(1);
			}
			
			
			fireball.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0f, 1.0f); // According to docs, if called by a PlayerEntity it plays the sound to everyone *except* the player. Like what the frick?
		}
		
	}

	// Igniting one's self
	public void ignite(EntityPlayer playerEntity)
	{
		if (currentLegacyLevel >= 7) {
			int duration = 3; // (seconds)
			playerEntity.setFire(duration);
			playerEntity.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), duration, 10, true, false));
			
			// Play fireball sound then kill fireball
			EntitySmallFireball fireball = new EntitySmallFireball(playerEntity.world);
			fireball.posX = playerEntity.posX;
			fireball.posY = playerEntity.posY;
			fireball.posZ = playerEntity.posZ;
			
			if (!playerEntity.world.isRemote)
				playerEntity.world.spawnEntity(fireball);
			
			fireball.playSound(SoundEvents.ENTITY_FIREWORK_BLAST, 1.0f, 1.0f);
			fireball.onKillCommand();
		}	
	}
	
	// Fire Aspect fist
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event) {
		// If a player attacks something...
		if (event.getSource().getTrueSource() instanceof EntityPlayer && !event.getSource().getTrueSource().getEntityWorld().isRemote) {
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			EntityLivingBase enemy = event.getEntityLiving();
			
			if (LorienLegacies.instance.legacyManagers.containsKey(player.getUniqueID()) && LorienLegacies.instance.legacyManagers.get(player.getUniqueID()).lumenLegacyEnabled) {
				if (currentLegacyLevel >= 5) {
					// Check if the player is unarmed
					ItemStack stack = player.inventory.getCurrentItem();
					if (stack.equals(ItemStack.EMPTY)) {
						enemy.setFire(2);
					}
				}
			}
		}
	}

	@Override
	public int getStaminaPerSecond()
	{
		return 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowLumen;
	}
	
}
