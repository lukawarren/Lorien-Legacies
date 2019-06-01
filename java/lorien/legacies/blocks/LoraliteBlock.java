package lorien.legacies.blocks;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LoraliteBlock extends Block
{

	public static final String UNLOCALIZED_NAME = "loraliteblock";
	
	public LoraliteBlock()
	{
		super(Material.ROCK);
		
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME); // Used for naming stuff (like in en_US.lang)
		setRegistryName(UNLOCALIZED_NAME); // This is the name forge uses to identify this block
		setCreativeTab(LorienLegacies.instance.lorienTab);
		
		setLightLevel(1.0f);
		setHardness(3.0f);
		setHarvestLevel("pickaxe", 3); // Must be mined by a diamond pickaxe or higher
		
	}

}
