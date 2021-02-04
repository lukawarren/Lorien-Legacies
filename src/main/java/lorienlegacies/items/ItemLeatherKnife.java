package lorienlegacies.items;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class ItemLeatherKnife extends SwordItem
{
	public ItemLeatherKnife()
	{
		// Material, additional damage (zero index), attack speed (always negative, closer to 1 = faster), and creative menu location.
		super(LoricItemTier.LORICMETAL, -1, -2.2f, new Item.Properties().group(LorienLegacies.creativeTab));
	}
	
}
