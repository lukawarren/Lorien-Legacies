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
import lorienlegacies.network.mesages.MessageStaminaSync;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LegacyManager
{

	public static final int NUM_LEGACIES = 3;
	
	// Legacies
	Map<String, Legacy> legacies = new LinkedHashMap<String, Legacy>();
	
	/*
	 * Certain code, like in the config and effects packages, rely on
	 * a constant set of legacies present before RegisterLegacies() has been
	 * called, hence the multiple definition here. The point of RegisterLegacies()
	 * is to make disabling legacies easier, but they should still be present
	 * in any config, and we can't exactly remove effects mid-save.
	 */
	public static final String[] CONSTANT_LEGACIES =
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
	
	public void RegisterPlayer(PlayerEntity player, boolean forceLegacies)
	{		
		LorienLegacies.logger.info("Registering player with UUID {}", player.getUniqueID());
		
		// Construct new PlayerLegacyData with all legacies registered
		PlayerLegacyData data = new PlayerLegacyData();
		for (Legacy l : legacies.values()) data.RegisterLegacy(l.GetName(), true);
		
		// If player has already had legacies generated and we are not to give them new ones
		if (WorldLegacySaveData.get(player.getServer()).IsPlayerAlreadySaved(player.getUniqueID()) && !forceLegacies)
		{
			data.FromIntArray(WorldLegacySaveData.get(player.getServer()).GetPlayerData().get(player.getUniqueID()).ToIntArray());
		}
		// Else generate legacies
		else 
		{
			LorienLegacies.logger.info("Generating legacies...");
			
			// If forcing legacies, we must change the seed every time
			int seedOffset = 0;
			if (forceLegacies) seedOffset = (int) (new Date().getTime() / 1000);
			
			new LegacyGenerator(((ServerWorld)player.world).getSeed() + player.getUniqueID().getLeastSignificantBits() + seedOffset).GenerateRandomLegacies(data, forceLegacies);
		}
		
		// Add to save data
		WorldLegacySaveData.get(player.getServer()).SetPlayerData(player.getUniqueID(), data);
		
		// Force toggling off
		WorldLegacySaveData.get(player.getServer()).GetPlayerData().get(player.getUniqueID()).DetoggleAllLegacies();
		
		// Send legacies to client
		MessageLegacyData message = new MessageLegacyData();
		message.legacies = data.ToIntArray();
		NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)player);
	}
	
	public void RegisterClientData(PlayerLegacyData data)
	{
		// Populate hashmap for later use
		for (String legacy : legacies.keySet()) data.RegisterLegacy(legacy, true);
	}
	
	public void DisconnectPlayer(PlayerEntity player)
	{
		LorienLegacies.logger.info("Unregistering player with UUID {}", player.getUniqueID());
		
		// Save everything just in case
		WorldLegacySaveData.get(player.getServer()).markDirty();
	}
	
	public void OnLegacyTick(World world)
	{
		// For each player
		for (Map.Entry<UUID, PlayerLegacyData> entry : WorldLegacySaveData.get(world.getServer()).GetPlayerData().entrySet())
		{
			PlayerEntity player = world.getPlayerByUuid(entry.getKey());
			if (player == null) continue; // Avoid players not actually logged on
			
			// Save current levels and use up all stamina before using legacies
			for (Map.Entry<String, Integer> legacy : entry.getValue().legacies.entrySet())
			{
				if (legacy.getValue() > 0 && entry.getValue().IsLegacyToggled(legacy.getKey())) // If enabled and toggled
				{
					// Deplete stamina and add XP...
					int stamina = legacies.get(legacy.getKey()).GetStaminaPerTick() * ConfigLorienLegacies.legacyStamina.staminaMultipliers.get(legacy.getKey());
					
					// ...but not in creative...
					if (player.isCreative() == false) entry.getValue().stamina -= stamina;
					
					//... and save current level
					entry.getValue().legacyPrevLvl.put(legacy.getKey(), legacies.get(legacy.getKey()).GetLegacyLevel(player));
				}
			}
			
			// If exhausted, "de-toggle" all legacies and alert player
			if (entry.getValue().stamina <= 0)
			{
				// De-toggle server side
				entry.getValue().DetoggleAllLegacies();

				// Send message to client
				NetworkHandler.sendToPlayer(new MessageExhaustLegacies(), (ServerPlayerEntity)player);
				
			}
			
			// For each legacy
			for (Map.Entry<String, Integer> legacy : entry.getValue().legacies.entrySet())
			{
				if (legacy.getValue() > 0 && entry.getValue().IsLegacyToggled(legacy.getKey())) // If enabled and toggled
				{
					// If enough stamina remains, call OnLegacyTick() and add XP
					if (entry.getValue().stamina > 0) 
					{
						entry.getValue().AddLegacyXP(legacy.getKey(), legacies.get(legacy.getKey()).GetStaminaPerTick() * ConfigLorienLegacies.legacyXP.xpMultipliers.get(legacy.getKey()));
						legacies.get(legacy.getKey()).OnLegacyTick(player);
						
						// If level has increased, notify legacy
						if (entry.getValue().legacyPrevLvl.get(legacy.getKey()) != legacies.get(legacy.getKey()).GetLegacyLevel(player))
							legacies.get(legacy.getKey()).OnLevelChange(player);
					}
				}
			}
			
			// Restore stamina and confine to reasonable bounds
			if (entry.getValue().stamina >= ConfigLorienLegacies.legacyStamina.maxStamina) entry.getValue().stamina = ConfigLorienLegacies.legacyStamina.maxStamina;
			if (entry.getValue().stamina <= 0) entry.getValue().stamina = 0;
			entry.getValue().stamina += ConfigLorienLegacies.legacyStamina.staminaRestoredPerTick;
			
			// Send stamina to player - possibly every nth tick to reduce lag
			if (world.getWorldInfo().getGameTime() % ConfigLorienLegacies.legacyStamina.staminaSyncRate == 0)
			{
				MessageStaminaSync message = new MessageStaminaSync();
				message.maxStamina = ConfigLorienLegacies.legacyStamina.maxStamina;
				message.stamina = entry.getValue().stamina;
				NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)player);
			}
		}
	}
	
	public Map<String, Legacy> GetLegacies()
	{
		return legacies;
	}
	
}
