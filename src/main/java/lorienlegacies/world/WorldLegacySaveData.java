package lorienlegacies.world;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.PlayerLegacyData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

/*
 * Responsible for saving and loading of player legacy data,
 * for use with the entire mod
 */

public class WorldLegacySaveData extends WorldSavedData
{
	private static final boolean IS_GLOBAL = true; // Exists across all dimensions (Nether, Overworld, etc)
	private static final String DATA_NAME = LorienLegacies.MODID;
	
	private World world;
	private Map<UUID, PlayerLegacyData> playerData = new HashMap<UUID, PlayerLegacyData>();
	
	public WorldLegacySaveData(World world)
	{
		super(DATA_NAME);
		this.world = world;
	}
	
	public WorldLegacySaveData(String s)
	{
		super(s);
		markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		if (world == null) return; // If we haven't been properly created, break
		
		playerData.clear();
		
		// For each player, read legacy data into playerData
		for (int i = 0; i < world.playerEntities.size(); ++i)
		{
			UUID uuid = world.playerEntities.get(i).getUniqueID();
			int[] data = nbt.getIntArray(uuid.toString());
			playerData.get(uuid).FromIntArray(data);
			LorienLegacies.logger.info("Loaded legacy data for player with UUID {} --- {}", uuid, Arrays.toString(data));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		// For each player, serialise data and write to save
		for (Map.Entry<UUID, PlayerLegacyData> entry : playerData.entrySet())
		{
			int[] data = entry.getValue().ToIntArray();
			nbt.setIntArray(entry.getKey().toString(), data);
			LorienLegacies.logger.info("Saved legacy data for player with UUID {} --- {}", entry.getKey().toString(), Arrays.toString(data));
		}
			
		return nbt;
	}

	public static WorldLegacySaveData get(World world)
	{
		// Get instance (if applicable)
		MapStorage storage = IS_GLOBAL ? world.getMapStorage() : world.getPerWorldStorage();
		WorldLegacySaveData instance = (WorldLegacySaveData) storage.getOrLoadData(WorldLegacySaveData.class, DATA_NAME);
		
		// If none exists, make new one
		if (instance == null)
		{
			instance = new WorldLegacySaveData(world);
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
	
	public void SetPlayerData(UUID uuid, PlayerLegacyData data)
	{
		playerData.put(uuid, data);
		markDirty();
	}
	
	public void RemovePlayerFromDataToBeSaved(UUID uuid)
	{
		playerData.remove(uuid);
	}
	
	public Map<UUID, PlayerLegacyData> GetPlayerData()
	{
		return playerData;
	}
	
	public void SetWorld(World world)
	{
		this.world = world;
	}
}
