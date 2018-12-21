package lorien.legacies.items.tools;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

public class LoraliteHoe extends ItemHoe {
	
	public static final String UNLOCALIZED_NAME = "loralitehoe";
	
	public LoraliteHoe(ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.lorienTab);
	}
}
