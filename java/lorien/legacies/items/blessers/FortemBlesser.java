package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class FortemBlesser extends Item {
	
	private static String UNLOCALIZED_NAME = "fortemblesser";
	
	public FortemBlesser()
	{
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.blessersTab);
		setMaxStackSize(1);
		setMaxDamage(1);
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		// Give player using it legacy by finding LegacyManager with player UUID that corresponds to player that is using the item's UUID
		for (LegacyManager l : LorienLegacies.legacyManagers)
		{
			if (l.player.getUniqueID() == player.getUniqueID())
			{
				l.legaciesEnabled = true;
				l.fortemLegacyEnabled = true;
				
				if (worldIn.isRemote) // Stops it being called twice
					new FortemLegacy().blessedMessage(player, true);
			}
		}
		
		player.inventory.deleteStack(player.getHeldItem(handIn));
		LegacyLoader.saveLegacyImplimentations(LorienLegacies.legacyManagers.get(0), LegacyWorldSaveData.get(worldIn));
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
    }
	
	
}
