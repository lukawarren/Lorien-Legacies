package lorien.legacies.commands;

import java.util.ArrayList;
import java.util.List;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.network.NetworkHandler;
import lorien.legacies.network.mesages.MessageLegacyData;
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

public class CommandLegacies implements ICommand
{

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "legacies";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		int index = -1;
		
		for (int i = 0; i < LorienLegacies.legacyManagers.size(); i++)
		{
			if (LorienLegacies.legacyManagers.get(i).player.getUniqueID().equals(sender.getCommandSenderEntity().getUniqueID()))
				index = i;
			
		}
		
		if (index != -1 && LorienLegacies.legacyManagers.get(index).legaciesEnabled)
			LegacyLoader.sendLegaciesToClient(LorienLegacies.legacyManagers.get(index));
		else
			sender.sendMessage(new TextComponentString("You do not have legacies").setStyle(new Style().setColor(TextFormatting.RED)));
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
