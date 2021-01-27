package lorienlegacies.legacies;

import java.util.ArrayList;
import java.util.List;

import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.PlayerEntity;

/*
 * Base class for each legacy.
 */
public abstract class Legacy
{
	protected String NAME;
	protected String DESCRIPTION;
	protected List<LegacyLevel> levels = new ArrayList<LegacyLevel>();
	protected int STAMINA_PER_TICK;
	
	public 		String GetName() 			{ return NAME; }
	public 		String GetDescription() 	{ return DESCRIPTION; }
	protected 	int    GetStaminaPerTick() 	{ return STAMINA_PER_TICK; }
	
	protected abstract void OnLegacyTick(PlayerEntity player);
	
	protected PlayerLegacyData GetPlayerData(PlayerEntity player)
	{
		return WorldLegacySaveData.get(player.getServer()).GetPlayerData().get(player.getUniqueID());
	}
	
	protected boolean IsLegacyToggled(PlayerEntity player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data != null) return data.IsLegacyToggled(NAME);
		return false;
	}
	
	protected void Toggle(PlayerEntity player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data != null) data.ToggleLegacy(NAME);
	}
	
	protected void AddLevel(String description, int requiredXP)
	{
		levels.add(new LegacyLevel(description, requiredXP));
	}

	/*
	 * Returns 0 if legacy is not given,
	 * and 1 for level 1, etc
	 */
	protected int GetLegacyLevel(PlayerEntity player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data == null) return 0;
		
		// Starting at level 1, march through each level until XP is exhausted
		int xp = data.legacies.get(NAME).intValue();
		int level = 1;
		while (level <= levels.size() && xp >= levels.get(level-1).requiredXP)
		{
			level++;
			xp -= levels.get(level-1).requiredXP;
		}
		
		return level;
	}
	
	public class LegacyLevel
	{
		public String description;
		public int requiredXP;
		
		public LegacyLevel(String description, int requiredXP)
		{
			this.description = description;
			this.requiredXP = requiredXP;
		}
	}
	
	public List<LegacyLevel> GetLevels() { return levels; }
	
}
