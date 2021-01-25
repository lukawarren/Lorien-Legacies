package lorienlegacies.commands;

import java.util.Map;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandLegacies extends LorienCommand
{

	@Override
	protected int OnCommand(CommandSource source)
	{
		// Get player
		Entity entity;
		try  { entity = source.assertIsEntity(); } 
		catch (CommandSyntaxException e)  { LorienLegacies.logger.error("/legacies: {}", e.getStackTrace().toString()); return -1; }
		
		if (entity instanceof PlayerEntity == false) { return -1; }
		
		int numLegacies = 0;
		for (Integer legacy : LorienLegacies.proxy.GetClientLegacyData().legacies.values())
			if (legacy >= 0)
				numLegacies++;
		
		if (numLegacies == 0) { entity.sendMessage(new StringTextComponent("§cYou do not have legacies"), entity.getUniqueID()); return -1; }
		else
		{
			String legacyString = "Your legacies are: ";
			for (Map.Entry<String, Integer> entry : LorienLegacies.proxy.GetClientLegacyData().legacies.entrySet())
				if (entry.getValue() > 0)
					legacyString += entry.getKey() + " ";

			legacyString = legacyString.substring(0, legacyString.length() - 1); // Remove final space
			
			entity.sendMessage(new StringTextComponent(legacyString), entity.getUniqueID());
			return 0;
		}
	}
	
	@Override
	protected String GetName() 
	{
		return "legacies";
	}

	@Override
	protected int GetPermissionLevel()
	{
		return 4;
	}

}