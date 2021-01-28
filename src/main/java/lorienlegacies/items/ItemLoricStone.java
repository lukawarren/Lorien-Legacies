package lorienlegacies.items;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemLoricStone extends Item
{

	public ItemLoricStone()
	{
		super(new Item.Properties().maxStackSize(1).group(LorienLegacies.creativeTab));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
	}
}
