package lorienlegacies.commands;

import java.util.ArrayList;
import java.util.List;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandGiveLegacies implements ICommand
{

	@Override
	public int compareTo(ICommand o)
	{
		 return this.getName().compareTo(o.getName());
	}

	@Override
	public String getName()
	{
		return "giveLegacies";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "giveLegacies <player>";
	}

	@Override
	public List<String> getAliases()
	{
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (sender.getCommandSenderEntity() instanceof EntityPlayer)
		{
			LorienLegacies.proxy.GetLegacyManager().RegisterPlayer((EntityPlayer)sender.getCommandSenderEntity(), true);
			sender.sendMessage(new TextComponentString("You now have new legacies - use /legacies to view them"));
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender.canUseCommand(4, getName());
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