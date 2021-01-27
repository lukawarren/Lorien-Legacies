package lorienlegacies.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import lorienlegacies.legacies.Legacy.LegacyLevel;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandLegacyLevels extends LorienCommand
{

	@Override
	protected int OnCommand(CommandSource source, String argument)
	{
		// Get player
		Entity entity;
		try  { entity = source.assertIsEntity(); } 
		catch (CommandSyntaxException e)  { LorienLegacies.logger.error("/legacyLevel: {}", e.getStackTrace().toString()); return -1; }
		
		if (entity instanceof PlayerEntity == false) { return -1; }
		
		String legacyName = argument.toLowerCase();
		legacyName = legacyName.subSequence(0, 1).toString().toUpperCase() + legacyName.substring(1, legacyName.length()); // Capitalise first letter
		Legacy legacy = LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(legacyName);
		if (legacy == null) { entity.sendMessage(new StringTextComponent("§cInvalid legacy \"" + legacyName + "\""), entity.getUniqueID()); return -1; }
		
		int legacyLevel = legacy.GetLegacyLevel((PlayerEntity)source.getEntity());
		String levelText = legacyLevel > 0 ? "level " + legacyLevel : "not given"; 
		entity.sendMessage(new StringTextComponent("§9" + legacy.GetName() + "§f - currently " + levelText + ":"), entity.getUniqueID());
		
		int count = 1;
		for (LegacyLevel level : legacy.GetLevels())
		{
			entity.sendMessage(new StringTextComponent("Level " + count + ": " + level.description + " (requires " + level.requiredXP + " xp)"), entity.getUniqueID());
			count++;
		}
		
		return 0;
	}
	
	@Override
	protected String GetName()  { return "legacyLevel"; }

	@Override
	protected int GetPermissionLevel() { return 0; }

	@Override
	protected boolean HasArgument() { return true; }
	
	@Override
	protected String GetArgument() { return "legacy"; }
	
}