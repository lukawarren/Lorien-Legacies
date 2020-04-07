package lorien.legacies.commands;

import java.util.ArrayList;
import java.util.List;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import lorien.legacies.legacies.LegacyManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandLegacyXp implements ICommand
{

	@Override
	public int compareTo(ICommand o)
	{
		return this.getName().compareTo(o.getName());
	}

	@Override
	public String getName()
	{
		return "legacyXP";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "add xp to an existing legacy";
	}

	@Override
	public List<String> getAliases()
	{
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		// Get legacy manager of player
		int index = -1;	
		for (int i = 0; i < LorienLegacies.legacyManagers.size(); i++)
		{
			if (LorienLegacies.legacyManagers.get(i).player.getUniqueID().equals(sender.getCommandSenderEntity().getUniqueID()))
				index = i;
		}
		
		if (index != -1 && LorienLegacies.legacyManagers.get(index).legaciesEnabled)
		{
			// Retrieve arguments and parse XP number
			if (args.length < 2)
			{
				sender.sendMessage(new TextComponentString("Usage: /legacyXP [legacy] [xp]").setStyle(new Style().setColor(TextFormatting.RED)));
				return;
			}
			
			String name = args[0].toLowerCase();
			String addXp = args[1];
			int xp = 0;
			try { xp = Integer.parseInt(addXp); }
			catch (NumberFormatException e)
			{ 
				sender.sendMessage(new TextComponentString("Invalid amount of XP").setStyle(new Style().setColor(TextFormatting.RED))); 
				return;
			}
			
			// Convert name to legacy and do logic accordingly
			Legacy targetLegacy = null;
			for (int i = 0; i < LorienLegacies.legacyManagers.get(index).legacyList.size(); ++i) // Each legacy already has a name attribute, so just go through each one until we find the right one
			{
				Legacy l =  (Legacy) LorienLegacies.legacyManagers.get(index).legacyList.get(i);
				if (l.LEGACY_NAME.toLowerCase().equals(name))
					targetLegacy = (Legacy) LorienLegacies.legacyManagers.get(index).legacyList.get(i);
			}
			if (targetLegacy == null)
			{
				sender.sendMessage(new TextComponentString("Invalid legacy").setStyle(new Style().setColor(TextFormatting.RED)));
				return;
			}
			
			// Add XP and give the player confirmation
			targetLegacy.addXPForPlayer(xp, LorienLegacies.legacyManagers.get(index));
			sender.sendMessage(new TextComponentString("Added " + addXp + " xp to " + targetLegacy.LEGACY_NAME).setStyle(new Style().setColor(TextFormatting.GREEN)));
			
		} else sender.sendMessage(new TextComponentString("You do not have legacies").setStyle(new Style().setColor(TextFormatting.RED)));
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		// Only let the player do this if they're an OP
		final int requiredPermissionLevel = 2;
		return sender.canUseCommand(requiredPermissionLevel, this.getName());
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
