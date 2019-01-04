package lorien.legacies.proxy;

import lorien.legacies.blocks.ModBlocks;
import lorien.legacies.entities.ModEntities;
import lorien.legacies.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		ModEntities.register();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
    	ModBlocks.register(event.getRegistry());
    }
    
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
    	ModItems.registerItems(event);
    }
    
    @SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event)
	{
    	ModBlocks.registerItemBlocks(event.getRegistry());
	}
    
    public static void registerRender(Item item)
    {
    	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
    
    
    @SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
    	// Don't do this server side, silly!
	}
	
}
