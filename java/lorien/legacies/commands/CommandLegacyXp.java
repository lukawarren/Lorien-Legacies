package lorien.legacies.commands;

import java.util.ArrayList;
import java.util.List;

import lorien.legacies.core.LorienLegacies;
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

public class CommandLegacyXp implements ICommand{

	@Override
	public int compareTo(ICommand o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public String getName() {
		return "legacyXp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "add xp to an existing legacy";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		int index = -1;
		
		for (int i = 0; i < LorienLegacies.legacyManagers.size(); i++) {
			if (LorienLegacies.legacyManagers.get(i).player.getUniqueID().equals(sender.getCommandSenderEntity().getUniqueID()))
				index = i;
		}
		
		if (index != -1 && LorienLegacies.legacyManagers.get(index).legaciesEnabled) {
			String name = args[0];
			String addXp = args[1];
			int xp = Integer.parseInt(addXp);
			
			if (LorienLegacies.legacyManagers.get(index).lumenLegacyEnabled) {
				LorienLegacies.legacyManagers.get(index).lumenLegacy.addXPForPlayer(xp, LorienLegacies.legacyManagers.get(index));
				sender.sendMessage(new TextComponentString("Added " + addXp + " xp to lumen").setStyle(new Style().setColor(TextFormatting.GREEN)));
			} else {
				sender.sendMessage(new TextComponentString("Only works for Lumen and you don't have it").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}
			
			
			
			
			
		} else {
			sender.sendMessage(new TextComponentString("You don't have any legacies").setStyle(new Style().setColor(TextFormatting.RED)));
		}
		
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
