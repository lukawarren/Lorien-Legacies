package lorien.legacies.config;

import lorien.legacies.core.LorienLegacies;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = LorienLegacies.MODID, name = "lorienlegacies_config", type = Type.INSTANCE)

public class LorienLegaciesConfig
{
	/* - Part of issue #80, this will be re-hauled, no need doing it now if it'll change loads in the near future
	public static LegacyGeneration legacyGeneration = new LegacyGeneration();
	public static class LegacyGeneration
	{
		@Name("Generate Accelix")
		public boolean generateAccelix = true;
		@Name("Generate Avex")
		public boolean generateAvex = true;
		@Name("Generate Fortem")
		public boolean generateFortem = true;
		@Name("Generate Glacen")
		public boolean generateGlacen = true;
		@Name("Generate Lumen")
		public boolean generateLumen = true;
		@Name("Generate Novis")
		public boolean generateNovis = true;
		@Name("Generate Noxen")
		public boolean generateNoxen = true;
		@Name("Generate Pondus")
		public boolean generatePondus = true;
		@Name("Generate Regeneras")
		public boolean generateRegeneras = true;
		@Name("Generate Submari")
		public boolean generateSubmari = true;
		@Name("Generate Telekinesis")
		public boolean generateTekekinesis = true;
	}
	*/
	
	public static LegacyUse legacyUse = new LegacyUse();
	public static class LegacyUse
	{
		@Name("Allow Accelix")
		public boolean allowAccelix = true;
		@Name("Allow Avex")
		public boolean allowAvex = true;
		@Name("Allow Fortem")
		public boolean allowFortem = true;
		@Name("Allow Glacen")
		public boolean allowGlacen = true;
		@Name("Allow Lumen")
		public boolean allowLumen = true;
		@Name("Allow Novis")
		public boolean allowNovis = true;
		@Name("Allow Noxen")
		public boolean allowNoxen = true;
		@Name("Allow Pondus")
		public boolean allowPondus = true;
		@Name("Allow Regeneras")
		public boolean allowRegeneras = true;
		@Name("Allow Submari")
		public boolean allowSubmari = true;
		@Name("Allow Telekinesis")
		public boolean allowTelekinesis = true;
	}
	
	@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
	private static class EventHandler
	{

		// Inject the new values and save to the config file when the config has been changed from the GUI.
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if (event.getModID().equals(LorienLegacies.MODID)) ConfigManager.sync(LorienLegacies.MODID, Type.INSTANCE);
			LorienLegacies.proxy.configHasChanged = true;
		}
	}
	
}
