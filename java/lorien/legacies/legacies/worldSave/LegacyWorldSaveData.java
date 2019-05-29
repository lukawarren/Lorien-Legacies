package lorien.legacies.legacies.worldSave;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class LegacyWorldSaveData extends WorldSavedData {
	
	private static final boolean IS_GLOBAL = true; // Exists across all dimensions (Nether, Overworld, etc)
	private static final String DATA_NAME = LorienLegacies.MODID;

	public static List<LegacyDataHolder> legacyData = new ArrayList<>();
	public static List<UUID> playerUUIDs = new ArrayList<>();
	
	public static boolean initialLoadingRequired = false;
	
	public LegacyWorldSaveData()
	{
		super(DATA_NAME);
	}
	
	public LegacyWorldSaveData(String s)
	{
		super(s);
		markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		
		if (initialLoadingRequired == true)
		{
			
			int max = nbt.getInteger("maxUUIDs");
			for (int i = 0; i < max; i++)
			{
				playerUUIDs.add(UUID.fromString(nbt.getString(Integer.toString(i))));
				legacyData.add(new LegacyDataHolder());
			}
			
			initialLoadingRequired = false;
			
			readFromNBT(nbt);
			return;
		}
		
		for (int i = 0; i < playerUUIDs.size(); i++)
		{
			int[] data =  nbt.getIntArray(playerUUIDs.get(i).toString());
			
			legacyData.get(i).convertArrayToData(data);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("maxUUIDs", playerUUIDs.size());
		
		for (int i = 0; i < playerUUIDs.size(); i++)
		{
			nbt.setString(Integer.toString(i), playerUUIDs.get(i).toString());
			nbt.setIntArray(playerUUIDs.get(i).toString(), legacyData.get(i).convertDataToArray());
		}
		
		return nbt;
	}
	
	public static LegacyWorldSaveData get(World world)
	{	
		initialLoadingRequired = true;
		MapStorage storage = IS_GLOBAL ? world.getMapStorage() : world.getPerWorldStorage();
		LegacyWorldSaveData instance = (LegacyWorldSaveData) storage.getOrLoadData(LegacyWorldSaveData.class, DATA_NAME);
		
		if (instance == null)
		{
			instance = new LegacyWorldSaveData();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
	
	public void setLegacyData(LegacyDataHolder newData, World world, UUID playerUUID)
	{
		
		for (int i = 0; i < playerUUIDs.size(); i++)
		{		
			if (playerUUIDs.get(i).equals(playerUUID))
			{
				for (int j = 0; j < legacyData.get(i).data.size(); j++)
				{
					legacyData.get(i).data.get(j).name = newData.data.get(j).name;
					legacyData.get(i).data.get(j).value = newData.data.get(j).value;
				}
			}
		}

		markDirty();
	}
	
	public LegacyDataHolder getLegacyDataForPlayer(UUID playerUUID)
	{
		for (int i = 0; i < playerUUIDs.size(); i++)
		{
			if (playerUUIDs.get(i).equals(playerUUID))
			{
				return legacyData.get(i);
			}
		}
		
		return null;
	}
	
	public static void addPlayer(UUID playerUUID)
	{
		playerUUIDs.add(playerUUID);
		legacyData.add(new LegacyDataHolder());
	}
	
}
