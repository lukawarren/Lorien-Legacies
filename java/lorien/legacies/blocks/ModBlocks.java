package lorien.legacies.blocks;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import lorien.legacies.proxy.ClientProxy;
import lorien.legacies.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	
	@GameRegistry.ObjectHolder("lorienlegacies:loraliteore")
	public static LoraliteOre loraliteore = new LoraliteOre();
	
	@GameRegistry.ObjectHolder("lorienlegacies:loraliteblock")
	public static LoraliteBlock loraliteblock = new LoraliteBlock();
	
	public static void register(IForgeRegistry<Block> registry) {
		registry.register(loraliteore);
		registry.register(loraliteblock);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		 registry.register(new ItemBlock(loraliteore).setRegistryName(loraliteore.getRegistryName()));
		 registry.register(new ItemBlock(loraliteblock).setRegistryName(loraliteblock.getRegistryName()));
	}

	public static void registerModels()
	{
		CommonProxy.registerRender(Item.getItemFromBlock(loraliteore));
		CommonProxy.registerRender(Item.getItemFromBlock(loraliteblock));
	}
	
}


