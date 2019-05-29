package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class Blesser extends Item
{
	
	protected abstract void handleClient(EntityPlayer player);
	protected abstract void handleServer(LegacyManager l);
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		
		if (worldIn.isRemote) // Client
		{
			handleClient(player);
		}
		else // Server
		{
			for (LegacyManager l : LorienLegacies.legacyManagers)
			{
				if (l.player.getUniqueID().equals(player.getUniqueID()))
				{
					handleServer(l);
					LegacyLoader.saveLegaciesToSave(l, LegacyWorldSaveData.get(worldIn));
				}
			}
			
		}
		
		player.inventory.deleteStack(player.getHeldItem(handIn));
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
    }
	
}
