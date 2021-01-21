package lorienlegacies.legacies;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.generation.LegacyGenerator;
import lorienlegacies.legacies.implementations.*;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageLegacyData;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LegacyManager
{

	public static final int NUM_LEGACIES = 3;
	
	// Legacies
	Map<String, Legacy> legacies = new HashMap<String, Legacy>();
	
	public void RegisterLegacies()
	{
		Lumen lumen = new Lumen();
		legacies.put(lumen.GetName(), lumen);
		
		Avex avex = new Avex();
		legacies.put(avex.GetName(), avex);
		
		Glacen glacen = new Glacen();
		legacies.put(glacen.GetName(), glacen);
		
		LorienLegacies.logger.info("Registered {} legacies", legacies.size());
	}
	
	public void RegisterPlayer(EntityPlayer player, boolean forceLegacies)
	{		
		LorienLegacies.logger.info("Registering player with UUID {}", player.getUniqueID());
		
		// Construct new PlayerLegacyData with all legacies registered
		PlayerLegacyData data = new PlayerLegacyData();
		for (Legacy l : legacies.values()) data.RegisterLegacy(l.GetName(), true);
		
		// If player has already had legacies generated and we are not to give them new ones
		if (WorldLegacySaveData.get(player.world).IsPlayerAlreadySaved(player.getUniqueID()) && !forceLegacies)
		{
			data.FromIntArray(WorldLegacySaveData.get(player.world).GetPlayerData().get(player.getUniqueID()).ToIntArray());
		}
		// Else generate legacies
		else 
		{
			LorienLegacies.logger.info("Generating legacies...");
			
			// If forcing legacies, we must change the seed every time
			int seedOffset = 0;
			if (forceLegacies) seedOffset = (int) (new Date().getTime() / 1000);
			
			new LegacyGenerator(player.world.getSeed() + player.getUniqueID().getLeastSignificantBits() + seedOffset).GenerateRandomLegacies(data, forceLegacies);
		}
		
		// Add to save data
		WorldLegacySaveData.get(player.world).SetPlayerData(player.getUniqueID(), data);
		
		// Send legacies to client
		MessageLegacyData message = new MessageLegacyData();
		message.legacies = data.ToIntArray();
		NetworkHandler.sendToPlayer(message, (EntityPlayerMP)player);
	}
	
	public void RegisterClientData(PlayerLegacyData data)
	{
		// Populate hashmap for later use
		for (String legacy : legacies.keySet()) data.RegisterLegacy(legacy, true);
	}
	
	public void DisconnectPlayer(EntityPlayer player)
	{
		LorienLegacies.logger.info("Unregistering player with UUID {}", player.getUniqueID());
		
		// We can assume that no further edits will be made to the player's save data, so remove from WorldLegacySaveData
		WorldLegacySaveData.get(player.world).RemovePlayerFromDataToBeSaved(player.getUniqueID());
	}
	
	public void OnLegacyTick(World world)
	{
		// For each player
		for (Map.Entry<UUID, PlayerLegacyData> entry : WorldLegacySaveData.get(world).GetPlayerData().entrySet())
		{
			EntityPlayer player = world.getPlayerEntityByUUID(entry.getKey());
			if (player == null) continue; // Avoid players not actually logged on
			
			// For each legacy
			for (Map.Entry<String, Integer> legacy : entry.getValue().legacies.entrySet())
				if (legacy.getValue() > 0 && entry.getValue().IsLegacyToggled(legacy.getKey())) // If enabled and toggled
					legacies.get(legacy.getKey()).OnLegacyTick(player); // call OnLegacyTick()
					
		}
	}
	
}
