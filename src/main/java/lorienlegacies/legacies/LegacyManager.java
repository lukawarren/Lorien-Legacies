package lorienlegacies.legacies;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.implementations.Lumen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LegacyManager
{

	// Data
	Map<String, Legacy> legacies = new HashMap<String, Legacy>();
	Map<UUID, PlayerLegacyData> playerData = new HashMap<UUID, PlayerLegacyData>();
	
	public void RegisterLegacies()
	{
		Lumen lumen = new Lumen();
		legacies.put(lumen.GetName(), lumen);
		
		LorienLegacies.logger.info("Registered {} legacies", legacies.size());
	}
	
	public void RegisterPlayer(EntityPlayer player)
	{
		LorienLegacies.logger.info("Registering player with UUID {}", player.getUniqueID());
		
		// Construct new PlayerLegacyData with all legacies registered
		PlayerLegacyData data = new PlayerLegacyData();
		for (Legacy l : legacies.values()) data.RegisterLegacy(l.GetName(), true);
		
		playerData.put(player.getUniqueID(), data);
	}
	
	public void DisconnectPlayer(EntityPlayer player)
	{
		LorienLegacies.logger.info("Unregistering player with UUID {}", player.getUniqueID());
		
		playerData.remove(player.getUniqueID());
	}
	
	public void OnLegacyTick(World world)
	{
		// For each player
		for (Map.Entry<UUID, PlayerLegacyData> entry : playerData.entrySet())
		{
			EntityPlayer player = world.getPlayerEntityByUUID(entry.getKey());
			
			// For each legacy
			for (Map.Entry<String, Boolean> legacy : entry.getValue().legacies.entrySet())
				if (legacy.getValue()) // If enabled
					legacies.get(legacy.getKey()).OnLegacyTick(player); // OnLegacyTick()
					
		}
	}
	
}
