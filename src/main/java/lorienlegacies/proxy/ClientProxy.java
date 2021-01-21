package lorienlegacies.proxy;

import lorienlegacies.blocks.ModBlocks;
import lorienlegacies.commands.ModCommands;
import lorienlegacies.entities.ModEntities;
import lorienlegacies.items.ModItems;
import lorienlegacies.keybinds.ModKeybinds;
import lorienlegacies.legacies.PlayerLegacyData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	
    public PlayerLegacyData clientLegacies = new PlayerLegacyData();
	
	@Override
    public void preInit(FMLPreInitializationEvent e) 
	{
        super.preInit(e);
        ModEntities.register();
    }
	
	@Override
	public void init(FMLInitializationEvent e)
    {
    	super.init(e);
    	ModKeybinds.RegisterKeybinds();
    }

	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
		ModCommands.RegisterClientCommands();
	}
	
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
    	ModBlocks.RegisterModels();
    	ModItems.RegisterModels();
    }
	
    @Override
    public EntityPlayer GetPlayerEntityFromContext(MessageContext ctx)
	{
		return Minecraft.getMinecraft().player;
	}
    
    @Override
    public PlayerLegacyData GetClientLegacyData()
	{
		return clientLegacies;
	}
    
}
