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
	
	public void Register(CommandDispatcher<CommandSource> dispatcher)
	{
		dispatcher.register(
			Commands.literal(GetName())
			.requires(requirement -> requirement.hasPermissionLevel(GetPermissionLevel()))
			.executes(command -> OnCommand(command.getSource(), ""))
		);
	}
	
	protected abstract String GetName();
	protected abstract int GetPermissionLevel();
	protected abstract int OnCommand(CommandSource source, Object arguments);
	
}
