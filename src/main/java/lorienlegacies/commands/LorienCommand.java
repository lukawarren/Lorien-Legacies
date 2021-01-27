package lorienlegacies.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

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
		if (commandInstance.HasArgument() == false)
		{
			dispatcher.register(
					Commands.literal(commandInstance.GetName())
					.requires(requirement -> requirement.hasPermissionLevel(commandInstance.GetPermissionLevel()))
					.executes(command -> commandInstance.OnCommand(command.getSource(), ""))
			);
		}
		else
		{
			dispatcher.register(
					Commands.literal(commandInstance.GetName())
					.then(Commands.argument(commandInstance.GetArgument(), StringArgumentType.string())
						.requires(requirement -> requirement.hasPermissionLevel(commandInstance.GetPermissionLevel()))
						.executes(command -> commandInstance.OnCommand(command.getSource(), command.getArgument(commandInstance.GetArgument(), String.class)))
					)
			);
		}
	}
	
	protected abstract String GetName();
	protected abstract int GetPermissionLevel();
	protected abstract int OnCommand(CommandSource source, String argument);
	
	protected boolean HasArgument() { return false; }
	protected String GetArgument() { return ""; }
	
}
