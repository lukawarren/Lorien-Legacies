package lorienlegacies.legacies;

import java.util.ArrayList;
import java.util.List;

import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageLegacyLevel;
import lorienlegacies.network.mesages.MessageToggleLegacyClient;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

/*
 * Base class for each legacy.
 */
public abstract class Legacy
{
	protected String NAME;
	protected String DESCRIPTION;
	protected List<LegacyLevel> levels = new ArrayList<LegacyLevel>();
	protected float STAMINA_PER_TICK;
	
	public 		String GetName() 			{ return NAME; }
	public 		String GetDescription() 	{ return DESCRIPTION; }
	protected 	float    GetStaminaPerTick(){ return STAMINA_PER_TICK; }
	
	protected abstract void OnLegacyTick(PlayerEntity player);
	
	protected PlayerLegacyData GetPlayerData(PlayerEntity player)
	{
		return WorldLegacySaveData.get(player.getServer()).GetPlayerData().get(player.getUniqueID());
	}
	
	public boolean IsToggleable()
	{
		return STAMINA_PER_TICK > 0;
	}
	
	protected boolean IsLegacyToggled(PlayerEntity player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data != null) return data.IsLegacyToggled(NAME) || !IsToggleable();
		return false;
	}
	
	protected void Toggle(PlayerEntity player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data != null) data.ToggleLegacy(NAME);
		
		// Send to client
		MessageToggleLegacyClient message = new MessageToggleLegacyClient();
		message.legacy = NAME;
		NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)player);
	}
	
	protected void AddLevel(String description, int requiredXP)
	{
		levels.add(new LegacyLevel(description, requiredXP));
	}

	/*
	 * Returns 0 if legacy is not given,
	 * and 1 for level 1, etc
	 */
	public int GetLegacyLevel(PlayerEntity player)
	{
		PlayerLegacyData data = GetPlayerData(player);
		if (data == null) return 0;
		
		// Starting at level 1, march through each level until XP is exhausted
		int xp = data.legacies.get(NAME).intValue();
		if (xp == 0) return 0;
		
		int level = 1;
		while (level <= levels.size() && xp >= levels.get(level-1).requiredXP)
		{
			xp -= levels.get(level-1).requiredXP;
			level++;
		}
		
		if (level > levels.size()) level--;
		
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
	
	public void OnLevelChange(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		player.sendMessage(new StringTextComponent("§9" + NAME + "§f has levelled up to level " + level + " - " + levels.get(level-1).description.toLowerCase()), player.getUniqueID());
		
		// Send updated levels to client
		MessageLegacyLevel message = new MessageLegacyLevel();
		message.legacyName = NAME;
		message.legacyLevel = GetLegacyLevel(player);
		NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)player);
	}
	
	public List<LegacyLevel> GetLevels() { return levels; }
	
	public class LegacyAbility
	{
		public String name;
		public int requiredLevel;
		
		public LegacyAbility(String name, int requiredLevel)
		{
			this.name = name;
			this.requiredLevel = requiredLevel;
		}
	}
	
	public void OnAbility(String ability, PlayerEntity player) {}
	public float GetAbilityStamina(String ability) { return 0; }
}
