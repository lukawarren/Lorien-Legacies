package lorien.legacies.legacies.worldSave;

import java.util.ArrayList;
import java.util.List;

public class LegacyDataHolder 
{
	
	public List<LegacyData> data = new ArrayList<>();
	
	public LegacyData legacyDataSaved = new LegacyData("worldDataSaved");
	
	public LegacyData legaciesEnabled = new LegacyData("legaciesEnabled");
	
	public LegacyData lumenLegacyEnabled = new LegacyData("lumenLegacyEnabled");
	public LegacyData noxenLegacyEnabled = new LegacyData("noxenLegacyEnabled");
	public LegacyData submariLegacyEnabled = new LegacyData("submariLegacyEnabled");
	public LegacyData novisLegacyEnabled = new LegacyData("novisLegacyEnabled");
	public LegacyData accelixLegacyEnabled = new LegacyData("accelixLegacyEnabled");
	public LegacyData fortemLegacyEnabled = new LegacyData("fortemLegacyEnabled");
	public LegacyData pondusLegacyEnabled = new LegacyData("pondusLegacyEnabled");
	public LegacyData regenerasLegacyEnabled = new LegacyData("regenerasLegacyEnabled");
	public LegacyData avexLegacyEnabled = new LegacyData("avexLegacyEnabled");
	
	public LegacyDataHolder()
	{
		data.add(legacyDataSaved);
		
		data.add(legaciesEnabled);
		
		data.add(lumenLegacyEnabled);
		data.add(noxenLegacyEnabled);
		data.add(submariLegacyEnabled);
		data.add(novisLegacyEnabled);
		data.add(accelixLegacyEnabled);
		data.add(fortemLegacyEnabled);
		data.add(pondusLegacyEnabled);
		data.add(regenerasLegacyEnabled);
		data.add(avexLegacyEnabled);
	}
	
}
