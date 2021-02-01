package lorienlegacies.items;

import lorienlegacies.core.LorienLegacies;

import net.minecraft.item.SwordItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemLeatherKnife extends SwordItem
{
	public ItemLeatherKnife(final IItemTier toolMat)
	{
		//super constructor params: material, additionally damage (zero index), attack speed (always negative, closer to 1 = faster), and creative menu location.
		super(toolMat, -1, -2.2f, new Item.Settings().group(LorienLegacies.creativeTab));
	}
	
}
