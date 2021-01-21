package lorienlegacies.legacies;

import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.EntityPlayer;

/*
 * Base class for each legacy.
 */
public abstract class Legacy
{
	protected String NAME;
	protected String DESCRIPTION;
	
	protected String GetName() 			{ return NAME; }
	protected String GetDescription() 	{ return DESCRIPTION; }
	
	protected abstract void OnLegacyTick(EntityPlayer player);
	
	protected PlayerLegacyData GetPlayerData(EntityPlayer player)
	{
		return WorldLegacySaveData.get(player.world).GetPlayerData().get(player.getUniqueID());
	}
	
	protected int GetLegacyLevel(EntityPlayer player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data != null) return data.legacies.get(NAME).intValue();
		else return 0;
	}
	
	protected boolean IsLegacyToggled(EntityPlayer player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data != null) return data.IsLegacyToggled(NAME);
		return false;
	}
}
