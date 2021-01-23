package lorienlegacies.config;

import java.util.HashMap;
import java.util.Map;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.LegacyManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = LorienLegacies.MODID, name = LorienLegacies.MODID, type = Type.INSTANCE)
public class ConfigLorienLegacies
{

	@Name("Legacy generation")
	@Comment("Options related to the legacy generation system")
	public static LegacyGeneration legacyGeneration = new LegacyGeneration();
	
	public static class LegacyGeneration
	{
		@Name("Chance of legacies per player")
		@RangeInt(min=0, max=100)
		@Comment("Upon a new player joining a world, he/she has a chance to recieve legacies. This is that chance, out of 100, as a percentage (%).")
		public int legacyChance = 100;
		
		@Name("Minimum legacies if given")
		@RangeInt(min=1, max=LegacyManager.NUM_LEGACIES)
		@Comment("If a player is to be given legacies, this is the minimum they will recieve.")
		public int minimumLegacies = 2;
		
		@Name("Maximum legacies if given")
		@RangeInt(min=1, max=LegacyManager.NUM_LEGACIES)
		@Comment("If a player is to be given legacies, this is the maximum they will recieve.")
		public int maximumLegacies = 3;
	}
	
	@Name("Legacy stamina")
	@Comment("Options related to the legacy stamina system")
	public static LegacyStamina legacyStamina = new LegacyStamina();
	
	public static class LegacyStamina
	{
		@Name("Max stamina")
		@RangeInt(min=1)
		@Comment("Maximum legacy stamina a player can have")
		public int maxStamina = 50;
		
		@Name("Stamina restoration rate")
		@RangeInt(min=0)
		@Comment("The amount of stamina restored per tick")
		public int staminaRestoredPerTick = 1;
		
		@Name("Legacy stamina modifiers")
		@Comment("Multiplied by the amount of stamina used by each legacy")
		public Map<String, Integer> staminaModifiers = new HashMap<>();
		
		@Name("Stamina synchronisation rate")
		@Comment("The amount of ticks to wait before sending stamina data to client")
		public int staminaSyncRate = 1;
		
		LegacyStamina()
		{
			for (String legacy : LegacyManager.CONFIG_LEGACIES) staminaModifiers.put(legacy, 1);
		}
	}
	
	@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
	private static class EventHandler
	{
		// Inject the new values and save to the config file when the config has been changed from the GUI.
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			LorienLegacies.logger.info("Syncing config...");
			if (event.getModID().equals(LorienLegacies.MODID)) ConfigManager.sync(LorienLegacies.MODID, Type.INSTANCE);
		}
	}
	
	public static void SanitiseValues()
	{					
		// Confine values to reasonable bounds
		if (legacyGeneration.minimumLegacies > legacyGeneration.maximumLegacies)
		{
			legacyGeneration.minimumLegacies = legacyGeneration.maximumLegacies;
			ConfigManager.sync(LorienLegacies.MODID, Type.INSTANCE);
		}
	}
}
