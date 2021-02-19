package lorienlegacies.blocks;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
	// Register
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LorienLegacies.MODID);

	// Loralite ore
	public static final RegistryObject<Block> loraliteOre = BLOCKS.register("loralite_ore", () -> new BlockLoraliteOre());
	public static final RegistryObject<Item> loraliteOreBlockItem = ModItems.ITEMS.register("loralite_ore", () ->
	{
		return new BlockItem(loraliteOre.get(), new Item.Properties().group(LorienLegacies.creativeTab));
	});
	
	// Loralite block
	public static final RegistryObject<Block> loraliteBlock = BLOCKS.register("loralite_block", () -> new BlockLoraliteBlock());
	public static final RegistryObject<Item> loraliteBlockBlockItem = ModItems.ITEMS.register("loralite_block", () ->
	{
		return new BlockItem(loraliteBlock.get(), new Item.Properties().group(LorienLegacies.creativeTab));
	});
	
	
}
