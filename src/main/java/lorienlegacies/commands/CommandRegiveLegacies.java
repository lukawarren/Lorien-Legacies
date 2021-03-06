package lorienlegacies.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandRegiveLegacies extends LorienCommand
{
	@Override
	protected int OnCommand(CommandSource source, Object arguments)
	{
		Entity entity;
		try  { entity = source.assertIsEntity(); } 
		catch (CommandSyntaxException e)  { LorienLegacies.logger.error("/regiveLegacies: {}", e.getStackTrace().toString()); return -1; }
		
		if (entity instanceof PlayerEntity)
		{
			LorienLegacies.proxy.GetLegacyManager().RegisterPlayer((PlayerEntity)entity, true);
			entity.sendMessage(new StringTextComponent("You now have new legacies - use /legacies to view them"), entity.getUniqueID());
			
			return 0;
		}
		
		return -1;
	}

	@Override
	protected String GetName() { return "regiveLegacies"; }

	@Override
	protected int GetPermissionLevel() { return 4; }
	

}