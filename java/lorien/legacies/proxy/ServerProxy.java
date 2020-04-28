package lorien.legacies.proxy;

import lorien.legacies.commands.CommandLegacies;
import lorien.legacies.config.LorienLegaciesConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod.EventBusSubscriber
public class ServerProxy extends CommonProxy
{

	@Override
	public void init(FMLInitializationEvent e)
    {
		//legacyUseData = LorienLegaciesConfig.legacyUse;
    }
	
}
