package lorienlegacies.config;

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
	
	@Name("Chance of legacies per player")
	@RangeInt(min=0, max=100)
	@Comment("Upon a new player joining a world, he/she has a chance to recieve legacies. This is that chance, out of 100, as a percentage (%).")
	public static int legacyChance = 100;
	
	@Name("Minimum legacies if given")
	@RangeInt(min=1, max=LegacyManager.NUM_LEGACIES)
	@Comment("If a player is to be given legacies, this is the minimum they will recieve.")
	public static int minimumLegacies = 2;
	
	@Name("Maximum legacies if given")
	@RangeInt(min=1, max=LegacyManager.NUM_LEGACIES)
	@Comment("If a player is to be given legacies, this is the maximum they will recieve.")
	public static int maximumLegacies = 3;
	
	
	@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
	private static class EventHandler
	{
		// Inject the new values and save to the config file when the config has been changed from the GUI.
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if (event.getModID().equals(LorienLegacies.MODID)) ConfigManager.sync(LorienLegacies.MODID, Type.INSTANCE);
		}
	}
	
	public static void SanitiseValues()
	{
		// Confine values to reasonable bounds
		if (minimumLegacies > maximumLegacies)
		{
			minimumLegacies = maximumLegacies;
			ConfigManager.sync(LorienLegacies.MODID, Type.INSTANCE);
		}
	}
}
