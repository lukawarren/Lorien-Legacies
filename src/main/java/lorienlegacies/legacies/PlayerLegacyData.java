package lorienlegacies.legacies;

import java.util.HashMap;
import java.util.Map;

/*
 * Each player has an associated PlayerLegacyData entry
 */
public class PlayerLegacyData
{
	public Map<String, Boolean> legacies = new HashMap<String, Boolean>();
	
	public void RegisterLegacy(String name, Boolean enabled)
	{
		legacies.put(name, enabled);
	}
	
}
