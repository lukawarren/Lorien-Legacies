package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.block.material.Material;
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
			Material m = player.world.getBlockState(new BlockPos(MathHelper.floor(player.posX), 
			          //MathHelper.floor(player.posY - 0.20000000298023224D - (double)player.getYOffset()), 
					  MathHelper.floor(player.posY - 0.20000000298023224D - 0.2d), 
			          MathHelper.floor(player.posZ))).getMaterial();
			boolean isWater = m.isLiquid();
			
			if (isWater && !player.isInWater())
			{
				player.motionX = player.motionX;
				player.motionY = 0.0f;
				player.motionZ = player.motionZ;
			}
		}

	}
	
}
