package lorienlegacies.world;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.PlayerLegacyData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

/*
 * Responsible for saving and loading of player legacy data,
 * for use with the entire mod
 */

public class WorldLegacySaveData extends WorldSavedData implements Supplier<WorldLegacySaveData>
{
	private static final String DATA_NAME = LorienLegacies.MODID;
	
	private Map<UUID, PlayerLegacyData> playerData = new HashMap<UUID, PlayerLegacyData>();
	
	public WorldLegacySaveData()
	{
		super(DATA_NAME);
	}
	
	public WorldLegacySaveData(String s)
	{
		super(s);
		markDirty();
	}
	
	@Override
	public void read(CompoundNBT nbt)
	{
		playerData.clear();
		
		// Read from array of UUIDs (if they exist yet!) - where each UUID is 16 bytes long
		byte[] uuids = nbt.getByteArray("legacyUUIDs");
		if (uuids == null) return;
		
		// For each present UUID, read legacy data into playerData
		for (int i = 0; i < uuids.length / 16; ++i)
		{
			// Build UUID from bytes
			byte[] bytes = new byte[16];
			for (int b = 0; b < 16; ++b) bytes[b] = uuids[i*16 + b];
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			long high = bb.getLong();
		    long low = bb.getLong();
			UUID uuid = new UUID(high, low);
			
			int[] data = nbt.getIntArray(uuid.toString());
			
			// playerData entry will be overwritten with registered legacies later, so for now it is okay to leave it blank
			playerData.put(uuid, new PlayerLegacyData(data.length));
			playerData.get(uuid).FromIntArray(data);
			LorienLegacies.logger.info("Loaded legacy data for player with UUID {} --- {}", uuid, Arrays.toString(playerData.get(uuid).ToIntArray()));
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		// Make Byte list of UUIDS
		List<Byte> uuidBytes = new ArrayList<Byte>();
		for (UUID uuid : playerData.keySet())
		{
			// Build byte array from UUID
			ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		    bb.putLong(uuid.getMostSignificantBits());
		    bb.putLong(uuid.getLeastSignificantBits());
		    
		    // Append each byte to array
		    for (byte b : bb.array())
		    	uuidBytes.add(b);
		}
		
		// Convert Byte list to byte[] and save
		byte[] bytes = new byte[16 * playerData.size()]; // Each UUID is 16 bytes
		for (int i = 0; i < bytes.length; ++i) bytes[i] = uuidBytes.get(i).byteValue();
		nbt.putByteArray("legacyUUIDs", bytes);
		
		// For each player, serialise data and write to save
		for (Map.Entry<UUID, PlayerLegacyData> entry : playerData.entrySet())
		{
			int[] data = entry.getValue().ToIntArray();
			nbt.putIntArray(entry.getKey().toString(), data);
		}
			
		return nbt;
	}

	public static WorldLegacySaveData get(MinecraftServer server)
	{
		// Use the overworld for saving data as it is always present
		World world = server.getWorld(World.OVERWORLD);
		
		// Get instance (if applicable)...
		DimensionSavedDataManager storage = ((ServerWorld)world).getSavedData();
		WorldLegacySaveData saver = (WorldLegacySaveData) storage.getOrCreate(() -> new WorldLegacySaveData(), LorienLegacies.MODID);

		// ...or create a new one
		if (saver == null) 
		{
			saver = new WorldLegacySaveData();
			storage.set(saver);
		}
		return saver;
	}
	
	@Override
	public WorldLegacySaveData get()
	{
		return this;
	}
	
	public void SetPlayerData(UUID uuid, PlayerLegacyData data)
	{
		playerData.put(uuid, data);
		markDirty();
	}
	
	public Map<UUID, PlayerLegacyData> GetPlayerData()
	{
		return playerData;
	}

	public boolean IsPlayerAlreadySaved(UUID uuid)
	{
		return playerData.containsKey(uuid);
	}

}
