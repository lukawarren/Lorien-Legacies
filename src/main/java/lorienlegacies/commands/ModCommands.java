package lorienlegacies.commands;

import net.minecraftforge.event.RegisterCommandsEvent;

public class ModCommands
{
	
	public static void RegisterCommands(RegisterCommandsEvent event)
	{		
		new CommandGiveLegacies().Register(event.getDispatcher());
		new CommandLegacies().Register(event.getDispatcher());
		new CommandLegacyLevels().Register(event.getDispatcher());
		new CommandGiveLegacy().Register(event.getDispatcher());
	}
	
}
