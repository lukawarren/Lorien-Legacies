package lorienlegacies.items;

import lorienlegacies.core.LorienLegacies;
import.lorienlegacies.items.ToolMaterialLoricMetal;

import net.minecraft.recipe.Ingredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ToolMaterial;

public class ItemLeatherKnife extends ItemSword
{
	public ItemLeatherKnife(ToolMaterial toolMat)
	{
		//super constructor params: material, additionally damage (zero index), attack speed (always negative, closer to 1 = faster), and creative menu location.
		super(toolMat, -1, -2.2f, new Item.Settings().group(LorienLegacies.creativeTab));
	}
	
}
