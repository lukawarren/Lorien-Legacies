package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import lorien.legacies.legacies.LegacyManager;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class PondusLegacy extends Legacy {
	
	private boolean previousWaterDecision = false;
	private boolean lastFrameWasToggled = false;
	
	public PondusLegacy()
	{
		LEGACY_NAME = "Pondus";
		DESCRIPTION = "grants water walking";
	}
	
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		//player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("water_breathing"), 1, 255, true, false)); // sorry this took so long to get rid of! xD
		
		if (toggled)
		{
			// Search the ground below the player to identify if there is water or not
			Material m = player.world.getBlockState(new BlockPos(MathHelper.floor(player.posX), 
			          //MathHelper.floor(player.posY - 0.20000000298023224D - (double)player.getYOffset()), 	  
					  MathHelper.floor(player.posY - 0.1), 
			          MathHelper.floor(player.posZ))).getMaterial();
			boolean isWater = m.isLiquid();
			
			if (isWater && !player.isInWater())
			{
				//player.motionX *= -player.getAIMoveSpeed() * 10;
				player.motionX *= 1.0;
				player.motionZ *= 1.0;

				// Check if the player has the accelix legacy and change their speed accordingly. 
				LegacyManager l = (LorienLegacies.instance.legacyManagers.containsKey(player.getUniqueID())) ? LorienLegacies.instance.legacyManagers.get(player.getUniqueID()) : null;
				
				if (l != null)
				{
					if (l.accelixLegacyEnabled && l.accelixLegacy.getToggled())
					{				
						player.motionX *= 1.01;
						player.motionX *= 1.01;
					}
				}
				
				
				
				// This value was 0.005f. Changing it to 0 stops the player from floating up continuously
				player.motionY = 0.000f;
				
				// This allows the player to jump
				player.onGround = true;
			}
		}

	}
	
	@Override
	public int getStaminaPerSecond()
	{
		return toggled ? 10 : 0;
	}

	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowPondus;
	}
	
}
