package lorienlegacies.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import lorienlegacies.legacies.PlayerLegacyData;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageLegacyData;
import lorienlegacies.network.mesages.MessageLegacyLevel;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CommandGiveLegacy extends LorienCommand
{
	@Override
	public void Register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(
			Commands.literal(GetName())
			.then(Commands.argument("legacy", StringArgumentType.string())
				.requires(requirement -> requirement.hasPermissionLevel(GetPermissionLevel()))
				.executes(command -> OnCommand(command.getSource(), command.getArgument("legacy", String.class)))
			)
		);
	}
	
	@Override
	protected int OnCommand(CommandSource source, Object arguments)
	{
		Entity entity;
		try  { entity = source.assertIsEntity(); } 
		catch (CommandSyntaxException e)  { LorienLegacies.logger.error("/giveLegacy: {}", e.getStackTrace().toString()); return -1; }
		
		// Parse argument
		String legacyName = ((String)arguments).toLowerCase();
		legacyName = legacyName.subSequence(0, 1).toString().toUpperCase() + legacyName.substring(1, legacyName.length()); // Capitalise first letter
		
		// Does player already have legacy?
		PlayerLegacyData playerData = WorldLegacySaveData.get(source.getServer()).GetPlayerData().get(entity.getUniqueID());
		if (playerData.legacies.containsKey(legacyName) == false || playerData.legacies.get(legacyName) > 0)
		{
			entity.sendMessage(new StringTextComponent("§cEither the legacy is invalid or you already have it"), entity.getUniqueID());
			return -1;
		}
		
		// Give player legacy
		playerData.legacies.put(legacyName, 1);
		
		// Send legacies to client - this will detoggle all legacies too...
		MessageLegacyData message = new MessageLegacyData();
		message.legacies = playerData.ToIntArray();
		NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)source.getEntity());
				
		// ...so send some contrived excuse
		source.getEntity().sendMessage(new StringTextComponent("You now have §9" + legacyName + "§f and rest your legacies temporarily..."), source.getEntity().getUniqueID());
				
		// Send legacy levels to client
		for (Legacy legacyToBeSent : LorienLegacies.proxy.GetLegacyManager().GetLegacies().values())
		{
			MessageLegacyLevel levelMessage = new MessageLegacyLevel();
			levelMessage.legacyName = legacyToBeSent.GetName();
			levelMessage.legacyLevel = legacyToBeSent.GetLegacyLevel((PlayerEntity)source.getEntity());
			NetworkHandler.sendToPlayer(levelMessage, (ServerPlayerEntity)source.getEntity());
		}
		
		return 0;
	}

	@Override
	protected String GetName() { return "giveLegacy"; }

	@Override
	protected int GetPermissionLevel() { return 4; }
	

}