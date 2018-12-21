package lorien.legacies.items.tools;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class LoraliteDagger extends ItemSword {
	
	public static final String UNLOCALIZED_NAME = "loralitedagger";
	
	public LoraliteDagger(ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.lorienTab);
	}
}
