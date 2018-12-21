package lorien.legacies.blocks;

import java.util.Random;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LoraliteOre extends Block {
	
	private static final String UNLOCALIZED_NAME = "loraliteore";
	
	public LoraliteOre()
	{
		super(Material.ROCK);
		
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME); // Used for naming stuff (like in en_US.lang)
		setRegistryName(UNLOCALIZED_NAME); // This is the name forge uses to identify this block
		setCreativeTab(LorienLegacies.instance.lorienTab);
		
		setLightLevel(1.0f);
		setHardness(3.0f);
		setHarvestLevel("pickaxe", 3); // Must be mined by a diamond pickaxe or higher
		
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModItems.loraliteCrystal;
    }
	
	@Override
	public int quantityDropped(Random random)
	{
		// 50% chance of 1 or 2
		float x = random.nextFloat();
		if (x <= 0.5f)
			return 1;
		else
			return 2;
	}
	
	
}