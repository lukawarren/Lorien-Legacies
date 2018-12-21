package lorien.legacies.items.tools;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.util.ResourceLocation;

public class LoraliteAxe extends ItemAxe {
	
	public static final String UNLOCALIZED_NAME = "loraliteaxe";
	
	public LoraliteAxe(ToolMaterial material)
	{
		super(material, 12.0f,-2.0f);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.lorienTab);
	}
}
