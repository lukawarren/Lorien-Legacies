package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyManager;
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
		setMaxDamage(0);
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		// Give player using it legacy by finding LegacyManager with player UUID that corresponds to player that is using the item's UUID
		for (LegacyManager l : LorienLegacies.legacyManagers)
		{
			if (l.player.getUniqueID() == player.getUniqueID() && player.world.isRemote == false)
			{
				l.legaciesEnabled = true;
				l.fortemLegacyEnabled = true;
				l.player.sendMessage(new TextComponentString("You have been blessed with fortem - grants toggleable super strength (currently enabled)").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
    }
	
	
}
