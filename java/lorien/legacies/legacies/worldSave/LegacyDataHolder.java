package lorien.legacies.legacies.worldSave;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LegacyDataHolder 
{
	
	public List<LegacyEnabledData> enabledData = new ArrayList<>();
	
	public LegacyEnabledData legacyDataSaved = new LegacyEnabledData("worldDataSaved");
	
	public LegacyEnabledData legaciesEnabled = new LegacyEnabledData("legaciesEnabled");
	
	public LegacyEnabledData lumenLegacyEnabled = new LegacyEnabledData("lumenLegacyEnabled");
	public LegacyEnabledData noxenLegacyEnabled = new LegacyEnabledData("noxenLegacyEnabled");
	public LegacyEnabledData submariLegacyEnabled = new LegacyEnabledData("submariLegacyEnabled");
	public LegacyEnabledData novisLegacyEnabled = new LegacyEnabledData("novisLegacyEnabled");
	public LegacyEnabledData accelixLegacyEnabled = new LegacyEnabledData("accelixLegacyEnabled");
	public LegacyEnabledData fortemLegacyEnabled = new LegacyEnabledData("fortemLegacyEnabled");
	public LegacyEnabledData pondusLegacyEnabled = new LegacyEnabledData("pondusLegacyEnabled");
	public LegacyEnabledData regenerasLegacyEnabled = new LegacyEnabledData("regenerasLegacyEnabled");
	public LegacyEnabledData avexLegacyEnabled = new LegacyEnabledData("avexLegacyEnabled");
	public LegacyEnabledData glacenLegacyEnabled = new LegacyEnabledData("glacenLegacyEnabled");
	
	public List<LegacyLevelData> levelData = new ArrayList<>();
	
	public LegacyLevelData lumenLegacyLevel = new LegacyLevelData("lumenLegacyLevel");
	public LegacyLevelData noxenLegacyLevel = new LegacyLevelData("noxenLegacyLevel");
	public LegacyLevelData submariLegacyLevel = new LegacyLevelData("submariLegacyLevel");
	public LegacyLevelData novisLegacyLevel = new LegacyLevelData("novisLegacyLevel");
	public LegacyLevelData accelixLegacyLevel = new LegacyLevelData("accelixLegacyLevel");
	public LegacyLevelData fortemLegacyLevel = new LegacyLevelData("fortemLegacyLevel");
	public LegacyLevelData pondusLegacyLevel = new LegacyLevelData("pondusLegacyLevel");
	public LegacyLevelData regenerasLegacyLevel = new LegacyLevelData("regenerasLegacyLevel");
	public LegacyLevelData avexLegacyLevel = new LegacyLevelData("avexLegacyLevel");
	public LegacyLevelData glacenLegacyLevel = new LegacyLevelData("glacenLegacyLevel");
	
	public List<LegacyLevelData> xpData = new ArrayList<>();
	
	public LegacyLevelData lumenLegacyXP = new LegacyLevelData("lumenLegacyXP");
	public LegacyLevelData noxenLegacyXP = new LegacyLevelData("noxenLegacyXP");
	public LegacyLevelData submariLegacyXP = new LegacyLevelData("submariLegacyXP");
	public LegacyLevelData novisLegacyXP = new LegacyLevelData("novisLegacyXP");
	public LegacyLevelData accelixLegacyXP = new LegacyLevelData("accelixLegacyXP");
	public LegacyLevelData fortemLegacyXP = new LegacyLevelData("fortemLegacyXP");
	public LegacyLevelData pondusLegacyXP = new LegacyLevelData("pondusLegacyXP");
	public LegacyLevelData regenerasLegacyXP = new LegacyLevelData("regenerasLegacyXP");
	public LegacyLevelData avexLegacyXP = new LegacyLevelData("avexLegacyXP");
	public LegacyLevelData glacenLegacyXP = new LegacyLevelData("glacenLegacyXP");
	
	public LegacyDataHolder()
	{	
		enabledData.add(legacyDataSaved);
		
		enabledData.add(legaciesEnabled);
		
		enabledData.add(lumenLegacyEnabled);
		enabledData.add(noxenLegacyEnabled);
		enabledData.add(submariLegacyEnabled);
		enabledData.add(novisLegacyEnabled);
		enabledData.add(accelixLegacyEnabled);
		enabledData.add(fortemLegacyEnabled);
		enabledData.add(pondusLegacyEnabled);
		enabledData.add(regenerasLegacyEnabled);
		enabledData.add(avexLegacyEnabled);
		enabledData.add(glacenLegacyEnabled);
		
		levelData.add(lumenLegacyLevel);
		levelData.add(noxenLegacyLevel);
		levelData.add(submariLegacyLevel);
		levelData.add(novisLegacyLevel);
		levelData.add(accelixLegacyLevel);
		levelData.add(fortemLegacyLevel);
		levelData.add(pondusLegacyLevel);
		levelData.add(regenerasLegacyLevel);
		levelData.add(avexLegacyLevel);
		levelData.add(glacenLegacyLevel);
		
		xpData.add(lumenLegacyXP);
		xpData.add(noxenLegacyXP);
		xpData.add(submariLegacyXP);
		xpData.add(novisLegacyXP);
		xpData.add(accelixLegacyXP);
		xpData.add(fortemLegacyXP);
		xpData.add(pondusLegacyXP);
		xpData.add(regenerasLegacyXP);
		xpData.add(avexLegacyXP);
		xpData.add(glacenLegacyXP);
	}
	
	public void convertArrayToLegacyEnabledData(int[] array)
	{	
		try { int x = array[0]; } catch (Exception e)
		{
			for (LegacyEnabledData l : enabledData)
				l.value = false;
			return;
		}
		
		for (int i = 0; i < enabledData.size(); i++)
			enabledData.get(i).value = (array[i] == 1) ? true : false;
	}
	
	public int[] convertLegacyEnabledDataToArray()
	{
		int[] array = new int[enabledData.size()];
		
		for (int i = 0; i < enabledData.size(); i++)
			array[i] = (enabledData.get(i).value == true) ? 1 : 0;
		
		return array;
	}
	
	public void convertArrayToLegacyLevelData(int[] array)
	{	
		try { int x = array[0]; } catch (Exception e)
		{
			for (LegacyLevelData l : levelData)
				l.value = 0;
			return;
		}
		
		for (int i = 0; i < levelData.size(); i++)
			levelData.get(i).value = array[i];
	}
	
	public int[] convertLegacyLevelDataToArray()
	{
		int[] array = new int[levelData.size()];
		
		for (int i = 0; i < levelData.size(); i++)
			array[i] = levelData.get(i).value;
		
		return array;
	}
	
	public void convertArrayToLegacyXPData(int[] array)
	{	
		try { int x = array[0]; } catch (Exception e)
		{
			for (LegacyLevelData l : xpData)
				l.value = 0;
			return;
		}
		
		for (int i = 0; i < xpData.size(); i++)
			xpData.get(i).value = array[i];
	}
	
	public int[] convertLegacyXPDataToArray()
	{
		int[] array = new int[xpData.size()];
		
		for (int i = 0; i < xpData.size(); i++)
			array[i] = xpData.get(i).value;
		
		return array;
	}
	
}
