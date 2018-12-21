package lorien.legacies.items;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class LoraliteCrystal extends Item {
	
	public static final String UNLOCALIZED_NAME = "loralitecrystal";
	
	public LoraliteCrystal()
	{
		setCreativeTab(LorienLegacies.instance.lorienTab);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
	}
	
}
