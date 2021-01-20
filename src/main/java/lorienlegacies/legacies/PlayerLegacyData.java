package lorienlegacies.legacies;

import java.util.LinkedHashMap;

/*
 * Each player has an associated PlayerLegacyData entry
 */
public class PlayerLegacyData
{
	public LinkedHashMap<String, Integer> legacies = new LinkedHashMap<String, Integer>();
	
	public void RegisterLegacy(String name, Boolean enabled)
	{
		legacies.put(name, enabled ? 1 : 0);
	}
	
	public int[] ToIntArray()
	{
		int[] array = new int[legacies.size()];
		int count = 0;
		for (Integer value : legacies.values())
		{
			array[count] = value;
			count++;
		}
		
		return array;
	}
	
	public void FromIntArray(int[] array)
	{
		// Assuming hashmap is already populated, fill with correct values
		for (int i = 0; i < legacies.size(); ++i)
		{
			// Get nth key
			String key = (String) (legacies.keySet().toArray()[i]);
			
			legacies.replace(key, array[i]);
		}
	}
	
}
