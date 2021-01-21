package lorienlegacies.commands;

import java.util.ArrayList;
import java.util.List;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.gui.GuiLegacyToggle;
import lorienlegacies.gui.ModGUIs;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandToggleLegacies implements ICommand
{

	@Override
	public int compareTo(ICommand o)
	{
		 return this.getName().compareTo(o.getName());
	}

	@Override
	public String getName()
	{
		return "toggleLegacies";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "toggleLegacies";
	}

	@Override
	public List<String> getAliases()
	{
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		int numLegacies = 0;
		for (Integer legacy : LorienLegacies.proxy.GetClientLegacyData().legacies.values())
			if (legacy >= 0)
				numLegacies++;
		
		if (numLegacies == 0) sender.sendMessage(new TextComponentString("You do not have legacies").setStyle(new Style().setColor(TextFormatting.RED)));
		else
		{
			ModGUIs.OpenGui(new GuiLegacyToggle());
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
	{
		return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

	

}