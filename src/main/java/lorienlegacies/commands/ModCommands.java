package lorienlegacies.commands;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ModCommands
{
	
	public static void RegisterClientCommands()
	{
		ClientCommandHandler.instance.registerCommand(new CommandLegacies());
	}
	
	public static void RegisterServerCommands(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandGiveLegacies());
	}
	
}
