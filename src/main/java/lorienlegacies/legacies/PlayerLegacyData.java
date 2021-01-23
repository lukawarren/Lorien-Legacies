package lorienlegacies.legacies;

import java.util.LinkedHashMap;

import lorienlegacies.config.ConfigLorienLegacies;

/*
 * Each player has an associated PlayerLegacyData entry
 */
public class PlayerLegacyData
{	
	// Saved data - legacy name and XP
	public LinkedHashMap<String, Integer> legacies = new LinkedHashMap<String, Integer>();
	
	// Non-saved data
	public LinkedHashMap<String, Boolean> legacyToggles = new LinkedHashMap<String, Boolean>();
	public int stamina;
	
	public PlayerLegacyData()
	{
		stamina = ConfigLorienLegacies.legacyStamina.maxStamina;
	}
	
	// For creating empty legacies as in WorldLegacySaveData (keys do not matter as it is merely a temporary class)
	public PlayerLegacyData(int blankLegacies)
	{
		for (int i = 0; i < blankLegacies; ++i) legacies.put("" + i, 0);
		stamina = ConfigLorienLegacies.legacyStamina.maxStamina;
	}
	
	public void RegisterLegacy(String name, Boolean enabled)
	{
		legacies.put(name, enabled ? 1 : 0);
		legacyToggles.put(name, false);
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
		
		stamina = ConfigLorienLegacies.legacyStamina.maxStamina;
	}
	
	public void ToggleLegacy(String legacy)
	{
		if (legacyToggles.get(legacy) == null) return;
		legacyToggles.put(legacy, !legacyToggles.get(legacy));
	}
	
	public boolean IsLegacyToggled(String legacy)
	{
		return legacyToggles.get(legacy);
	}
	
	public void DetoggleAllLegacies()
	{
		for (String legacy : legacyToggles.keySet())
		{
			legacyToggles.put(legacy, false);
		}
	}
	
	public void AddLegacyXP(String legacy, int xp)
	{
		if (legacies.get(legacy) == null) return;
		legacies.put(legacy, legacies.get(legacy)+ xp);
		
		// Stop falling below 1
		if (legacies.get(legacy) < 1) legacies.put(legacy, 1);
	}
	
}
