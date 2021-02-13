package lorienlegacies.commands;

import java.util.Map;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.PlayerLegacyData;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class CommandMaxLegacyLevels extends LorienCommand
{
	@Override
	protected int OnCommand(CommandSource source, Object arguments)
	{
		// Get player
		Entity entity;
		try  { entity = source.assertIsEntity(); } 
		catch (CommandSyntaxException e)  { LorienLegacies.logger.error("/maxLegacyLevels: {}", e.getStackTrace().toString()); return -1; }
		if (entity instanceof PlayerEntity == false) { return -1; }
		PlayerEntity player = (PlayerEntity) entity;
		
		// Give lots of XP
		PlayerLegacyData data = WorldLegacySaveData.get(entity.getServer()).GetPlayerData().get(player.getUniqueID());
		for (Map.Entry<String, Integer> entry : data.legacies.entrySet())
		{
			if (entry.getValue().intValue() > 0) // If enabled
				data.AddLegacyXP(entry.getKey(), LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(entry.getKey()).GetTotalXpRequirements());
			
			// Detect changes in level so as to sync with client
			if (data.legacyPrevLvl.get(entry.getKey()) != LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(entry.getKey()).GetLegacyLevel(player))
				LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(entry.getKey()).OnLevelChange(player);
		}
		
		return 0;
	}
	
	@Override
	protected String GetName()  { return "maxLegacyLevels"; }

	@Override
	protected int GetPermissionLevel() { return 0; }
	
}