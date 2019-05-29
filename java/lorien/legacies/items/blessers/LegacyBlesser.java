package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LegacyBlesser extends Item {
	
	public static final String UNLOCALIZED_NAME = "legacyblesser";
	
	public LegacyBlesser()
	{
		setRegistryName(UNLOCALIZED_NAME);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setCreativeTab(LorienLegacies.instance.blessersTab);
		setMaxStackSize(1);
		setMaxDamage(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		if (!worldIn.isRemote) // Server
		{
			boolean playerAlreadyHasLegacyManager = false;
			LegacyManager legacyManager = null;
			
			for (LegacyManager l : LorienLegacies.legacyManagers)
			{
				if (l.player.getUniqueID() == player.getUniqueID())
				{
					playerAlreadyHasLegacyManager = true;
					legacyManager = l;
				}
			}
			
			if (playerAlreadyHasLegacyManager == false)
				legacyManager = new LegacyManager(player);
			
			LegacyLoader.generateLegacies(legacyManager, true);
		}

		player.inventory.deleteStack(player.getHeldItem(handIn));
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
    }
	
}
