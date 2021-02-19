package lorienlegacies.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockLoraliteBlock extends Block
{
	public BlockLoraliteBlock()
	{
		super(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).setLightLevel(state -> { return 15; }).harvestLevel(3));
	}
}
