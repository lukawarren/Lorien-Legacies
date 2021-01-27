package lorienlegacies.commands;

import java.util.Map;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandLegacies extends LorienCommand
{

	@Override
	protected int OnCommand(CommandSource source, String argument)
	{
		// Get player
		Entity entity;
		try  { entity = source.assertIsEntity(); } 
		catch (CommandSyntaxException e)  { LorienLegacies.logger.error("/legacies: {}", e.getStackTrace().toString()); return -1; }
		
		if (entity instanceof PlayerEntity == false) { return -1; }
		
		int numLegacies = 0;
		for (Integer legacy : WorldLegacySaveData.get(source.getServer()).GetPlayerData().get(source.getEntity().getUniqueID()).legacies.values())
			if (legacy >= 0)
				numLegacies++;
		
		if (numLegacies == 0) { entity.sendMessage(new StringTextComponent("§cYou do not have legacies"), entity.getUniqueID()); return -1; }
		else
		{
			entity.sendMessage(new StringTextComponent("Your legacies are:"), source.getEntity().getUniqueID());
			for (Map.Entry<String, Legacy> entry : LorienLegacies.proxy.GetLegacyManager().GetLegacies().entrySet())
			{
				entity.sendMessage(new StringTextComponent("§9" + entry.getValue().GetName() + "§f - " + entry.getValue().GetDescription()), entity.getUniqueID());
			}		
			return 0;
		}
	}
	
	@Override
	protected String GetName() { return "legacies"; }

	@Override
	protected int GetPermissionLevel()
	{
		return 0;
	}

}