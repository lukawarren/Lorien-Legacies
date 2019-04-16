package lorien.legacies.legacies.worldSave;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class LegacyWorldSaveData extends WorldSavedData {
	
	private static final boolean IS_GLOBAL = true; // Exists across all dimensions (Nether, Overworld, etc)
	private static final String DATA_NAME = LorienLegacies.MODID;

	public LegacyDataHolder legacyData = new LegacyDataHolder();
	
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
		for (LegacyData l : legacyData.data)
			l.value = nbt.getBoolean(l.name);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		for (LegacyData l : legacyData.data)
			nbt.setBoolean(l.name, l.value);
		
		return nbt;
	}
	
	public static LegacyWorldSaveData get(World world)
	{
		MapStorage storage = IS_GLOBAL ? world.getMapStorage() : world.getPerWorldStorage();
		LegacyWorldSaveData instance = (LegacyWorldSaveData) storage.getOrLoadData(LegacyWorldSaveData.class, DATA_NAME);

		if (instance == null)
		{
			instance = new LegacyWorldSaveData();
			storage.setData(DATA_NAME, instance);
		}
		  return instance;
	}
	
	public void setLegacyData(LegacyDataHolder newData, World world)
	{
		
		for (int i = 0; i < newData.data.size(); i++)
		{
			legacyData.data.get(i).name = newData.data.get(i).name;
			legacyData.data.get(i).value = newData.data.get(i).value;
			System.out.println("Saved value with name " + newData.data.get(i).name + " and value " + newData.data.get(i).value);
		}
		
		legacyData = newData;
		markDirty();
		
		//new PacketWorldData(this).sendToAll();
	}
	
	public LegacyDataHolder getLegacyData()
	{
		return legacyData;
	}
	
}
