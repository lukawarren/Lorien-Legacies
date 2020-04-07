package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AvexLegacy extends Legacy {

	private static final float SPEED = 2.0f;
	private static final float UPWARDS_BOOST = 2.0f;
	
	private int ticksSinceToggled = 0;
	
	EntitySnowball entity; // For sound
	
	private boolean hoverMode = false;
	
	public AvexLegacy()
	{
		LEGACY_NAME = "Avex";
		DESCRIPTION = "grants swift flight";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (!hoverMode)
		{
			// If player is touching the ground, disable Avex (otherwise they go straight forward and it ruins Accelix)
			if (player.onGround && toggled && ticksSinceToggled > 20)
			{		
				toggle(player);
				
				entity = new EntitySnowball(player.world);
				entity.posX = player.posX;
				entity.posY = player.posY;
				entity.posZ = player.posZ;
				entity.playSound(SoundEvents.ENTITY_HORSE_LAND, 1f, 0.3f);
				entity.onKillCommand();
				
				
			}
			else if (toggled)
			{
				ticksSinceToggled++;
				
				// If first frame toggled, apply an upward velocity
				if (ticksSinceToggled < 20)
				{
					if (ticksSinceToggled == 1) // Play sound
					{				
						// Play sound audible to everyone by making a new entity at the position of player and making it play the sound
						entity = new EntitySnowball(player.world);
						entity.posX = player.posX;
						entity.posY = player.posY;
						entity.posZ = player.posZ;
						entity.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 1f, 1f);
						entity.onKillCommand();
					}
					
					player.addVelocity(0, 0.1f, 0);
				}
					
				// Then wait for a second or two
				else if (ticksSinceToggled < 80)
				{
					ticksSinceToggled++;
					return;
				}
				
				// Then carry on
				else
				{
					Vec3d direction = player.getLookVec();
		
					// Add velocity until velocity exceeds that which is desired (clamp the velocity m8)
					player.setVelocity(direction.x * SPEED, direction.y * SPEED, direction.z * SPEED);
					
			//		if (player.motionX > direction.x * SPEED)
			//			player.motionX = direction.x * SPEED;
			//		if (player.motionY > direction.y * SPEED)
			//			player.motionY = direction.y * SPEED;
			//		if (player.motionZ > direction.z * SPEED)
			//			player.motionZ = direction.z * SPEED;
					
					// Play sound every X amount of ticks so they don't overlap
					if (ticksSinceToggled % 200 == 0 || ticksSinceToggled == 81)
					{
						entity = new EntitySnowball(player.world);
						entity.posX = player.posX;
						entity.posY = player.posY;
						entity.posZ = player.posZ;
						//entity.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 30f, 1.0f);
						entity.onKillCommand();
					}
				}
				
			}
			else
			{
				ticksSinceToggled = 0;
			}
		}
		else if (toggled)
		{
			player.capabilities.isFlying = true;
		} else if (!toggled) {
			player.capabilities.isFlying = false;
		}
		
	}
	
	public void hover(EntityPlayer player)
	{
		sendMessageClientside(player, "Avex set to " + ((!hoverMode) ? "hover " : "flying ") + "mode");
		hoverMode = !hoverMode;
		
	}
	
}
