package lorienlegacies.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.MathHelper;

public class BlockLoraliteOre extends OreBlock
{
	public BlockLoraliteOre()
	{
		super(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).setLightLevel(state -> { return 10; }).harvestLevel(3));
	}

	@Override
	protected int getExperience(Random rand)
	{
		return MathHelper.nextInt(rand, 3, 7);
	}	
}
