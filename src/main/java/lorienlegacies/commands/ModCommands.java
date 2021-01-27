package lorienlegacies.commands;

import net.minecraftforge.event.RegisterCommandsEvent;

public class ModCommands
{
	
	public static void RegisterCommands(RegisterCommandsEvent event)
	{
		CommandLegacies.Register(event.getDispatcher(), new CommandGiveLegacies());
		CommandLegacies.Register(event.getDispatcher(), new CommandLegacies());
		CommandLegacies.Register(event.getDispatcher(), new CommandLegacyLevels());
	}
	
}
