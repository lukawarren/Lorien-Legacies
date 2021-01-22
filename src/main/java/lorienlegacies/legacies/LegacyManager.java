package lorienlegacies.legacies;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import lorienlegacies.config.ConfigLorienLegacies;
import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.generation.LegacyGenerator;
import lorienlegacies.legacies.implementations.Avex;
import lorienlegacies.legacies.implementations.Glacen;
import lorienlegacies.legacies.implementations.Lumen;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageExhaustLegacies;
import lorienlegacies.network.mesages.MessageLegacyData;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LegacyManager
{

	public static final int NUM_LEGACIES = 3;
	
	// Legacies
	Map<String, Legacy> legacies = new LinkedHashMap<String, Legacy>();
	
	// Config (if you can think of a better way to do this, please do it)
	public static final String[] CONFIG_LEGACIES =
	{
		"Lumen",
		"Avex",
		"Glacen"
	};
	
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
		
		// Save everything just in case
		WorldLegacySaveData.get(player.world).markDirty();
		
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
			
			// Use up all stamina before using legacies
			for (Map.Entry<String, Integer> legacy : entry.getValue().legacies.entrySet())
			{
				if (legacy.getValue() > 0 && entry.getValue().IsLegacyToggled(legacy.getKey())) // If enabled and toggled
				{
					// Deplete stamina and add XP
					int stamina = legacies.get(legacy.getKey()).GetStaminaPerTick() * ConfigLorienLegacies.legacyStamina.staminaModifiers.get(legacy.getKey());
					entry.getValue().stamina -= stamina;
				}
			}
			
			// If exhausted, "de-toggle" all legacies and alert player
			if (entry.getValue().stamina <= 0)
			{
				// De-toggle server side
				entry.getValue().DetoggleAllLegacies();

				// Send message to client
				NetworkHandler.sendToPlayer(new MessageExhaustLegacies(), (EntityPlayerMP)player);
				
			}
			
			// For each legacy
			for (Map.Entry<String, Integer> legacy : entry.getValue().legacies.entrySet())
			{
				if (legacy.getValue() > 0 && entry.getValue().IsLegacyToggled(legacy.getKey())) // If enabled and toggled
				{
					// If enough stamina remains, call OnLegacyTick() and add XP
					if (entry.getValue().stamina > 0) 
					{
						entry.getValue().AddLegacyXP(legacy.getKey(), legacies.get(legacy.getKey()).GetStaminaPerTick());
						legacies.get(legacy.getKey()).OnLegacyTick(player);
					}
				}
			}
			
			// Restore stamina and confine to reasonable bounds
			if (entry.getValue().stamina >= ConfigLorienLegacies.legacyStamina.maxStamina) entry.getValue().stamina = ConfigLorienLegacies.legacyStamina.maxStamina;
			if (entry.getValue().stamina <= 0) entry.getValue().stamina = 0;
			entry.getValue().stamina += ConfigLorienLegacies.legacyStamina.staminaRestoredPerTick;
		}
	}
	
}
