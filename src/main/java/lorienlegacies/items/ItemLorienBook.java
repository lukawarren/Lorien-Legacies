package lorienlegacies.items;

import java.util.List;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemLorienBook extends Item
{

	public ItemLorienBook()
	{
		super(new Item.Properties().maxStackSize(1).group(LorienLegacies.creativeTab));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{	
		// Make sure client-side
		if (worldIn.isRemote == false) return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
		
		LorienLegacies.proxy.OpenLegacyBookGUI();
		
		return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent("Contains a list of all your current legacies, as well as a handy FAQ at the back."));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
