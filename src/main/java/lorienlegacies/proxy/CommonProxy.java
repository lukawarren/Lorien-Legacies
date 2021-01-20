package lorienlegacies.proxy;

import lorienlegacies.blocks.ModBlocks;
import lorienlegacies.entities.ModEntities;
import lorienlegacies.items.ModItems;
import lorienlegacies.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@Mod.EventBusSubscriber
public class CommonProxy 
{
	
	public void preInit(FMLPreInitializationEvent e)
	{
		NetworkHandler.init();
		ModEntities.register();
    }

    public void init(FMLInitializationEvent e)
    {
    	
    }

    public void postInit(FMLPostInitializationEvent e)
    {
    	
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
	public static void registerRenders(ModelRegistryEvent event)
    {
    	
	}

	public EntityPlayer GetPlayerEntityFromContext(MessageContext ctx)
	{
		return null;
	}
}
