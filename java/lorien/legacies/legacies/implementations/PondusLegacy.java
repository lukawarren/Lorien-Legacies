package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PondusLegacy extends Legacy {
	
	public PondusLegacy()
	{
		LEGACY_NAME = "Pondus";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (toggled)
		{
			/*
			// Work out if player is above water
			int i = MathHelper.floor(player.posX);
			int j = MathHelper.floor(player.getEntityBoundingBox().minY);
			int k = MathHelper.floor(player.posZ);
			Material m = player.world.getBlockState(new BlockPos(i,  j,  k)).getMaterial();
			boolean isWater = (m == Material.WATER); // or m.isLiquid() or whatever you want.
			
			if (isWater)
			{
				player.motionY = 0;
				System.out.println("bob");
			}*/
		}
	}

}
