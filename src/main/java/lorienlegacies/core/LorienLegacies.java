package lorienlegacies.core;

import org.apache.logging.log4j.Logger;

import lorienlegacies.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LorienLegacies.MODID, name = LorienLegacies.NAME, version = LorienLegacies.VERSION)
public class LorienLegacies
{
	// Forge mod configuration and naming
    public static final String MODID = "lorienlegacies";
    public static final String NAME = "Lorien Legacies";
    public static final String VERSION = "1.0";

    // Logging
    private static Logger logger;

    // Proxy
    @SidedProxy(clientSide = "lorienlegacies.proxy.ClientProxy", serverSide = "lorienlegacies.proxy.ServerProxy")
	public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Initialising {} version {}", NAME, VERSION);
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    }
}
