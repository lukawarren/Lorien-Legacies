package lorienlegacies.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

/*
 * Abstract class to assist
 * create of commands.
 */
public abstract class LorienCommand
{
	
	public static void Register(CommandDispatcher<CommandSource> dispatcher, LorienCommand commandInstance)
	{
		dispatcher.register(
				Commands.literal(commandInstance.GetName())
				.requires(requirement -> requirement.hasPermissionLevel(commandInstance.GetPermissionLevel()))
				.executes(command -> commandInstance.OnCommand(command.getSource()))
		);
	}
	
	protected abstract String GetName();
	protected abstract int GetPermissionLevel();
	protected abstract int OnCommand(CommandSource source);
	
}
