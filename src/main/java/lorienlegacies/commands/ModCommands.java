package lorienlegacies.commands;

import net.minecraftforge.client.ClientCommandHandler;

public class ModCommands
{
	
	public static void RegisterClientCommands()
	{
		ClientCommandHandler.instance.registerCommand(new CommandLegacies());
		ClientCommandHandler.instance.registerCommand(new CommandToggleLegacies());
	}
	
}
