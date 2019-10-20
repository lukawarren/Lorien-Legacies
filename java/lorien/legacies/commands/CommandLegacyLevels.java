package lorien.legacies.commands;

import java.util.ArrayList;
import java.util.List;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.gui.LegacyGui;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.network.NetworkHandler;
import lorien.legacies.network.mesages.MessageLegacyData;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandLegacyLevels implements ICommand
{

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "legacyLevels";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "displays Lorien Legacies level GUI";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (!server.worlds[0].isRemote)
			Minecraft.getMinecraft().displayGuiScreen(new LegacyGui());
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
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
