package lorien.legacies.items.tools;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

public class LoralitePickaxe extends ItemPickaxe {
	
	public static final String UNLOCALIZED_NAME = "loralitepickaxe";
	
	public LoralitePickaxe(ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.lorienTab);
	}
	
}
