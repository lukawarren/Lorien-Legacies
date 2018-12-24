package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PondusLegacy extends Legacy {
	
	private boolean previousWaterDecision = false;
	
	public PondusLegacy()
	{
		LEGACY_NAME = "Pondus";
		
	}
	
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (toggled)
		{
			Material m = player.world.getBlockState(new BlockPos(MathHelper.floor(player.posX), 
			          //MathHelper.floor(player.posY - 0.20000000298023224D - (double)player.getYOffset()), 
					  MathHelper.floor(player.posY - 0.20000000298023224D - 0.2d), 
			          MathHelper.floor(player.posZ))).getMaterial();
			boolean isWater = m.isLiquid();
			
			if (isWater != previousWaterDecision)
			{
				player.motionY = 0;
				player.velocityChanged = true;
			}
				
			
			previousWaterDecision = isWater;
			
			player.setNoGravity(isWater || player.isAirBorne);
		}
	}

}
