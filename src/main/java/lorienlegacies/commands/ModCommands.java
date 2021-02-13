package lorienlegacies.commands;

import net.minecraftforge.event.RegisterCommandsEvent;

public class ModCommands
{
	
	public static void RegisterCommands(RegisterCommandsEvent event)
	{		
		new CommandRegiveLegacies().Register(event.getDispatcher());
		new CommandLegacies().Register(event.getDispatcher());
		new CommandLegacyLevels().Register(event.getDispatcher());
		new CommandGiveLegacy().Register(event.getDispatcher());
		new CommandMaxLegacyLevels().Register(event.getDispatcher());
	}
	
}
