package lorienlegacies.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.LegacyManager;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = LorienLegacies.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigLorienLegacies
{
	
	public static final CommonConfig CONFIG;
	public static final ForgeConfigSpec COMMON_SPEC;
	
	/*
	 * Get static objects to build config
	 */
	static
	{
		final Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		COMMON_SPEC = pair.getRight();
		CONFIG = pair.getLeft();
	}
	
	/*
	 * Config values seperated by
	 * category (remnant from 1.12.2)
	 */
	
	public static LegacyGeneration legacyGeneration = new LegacyGeneration();
	public static LegacyStamina legacyStamina = new LegacyStamina();
	
	public static class LegacyGeneration
	{
		public int legacyChance;
		public int minimumLegacies;
		public int maximumLegacies;
	}
	
	public static class LegacyStamina
	{
		public int maxStamina ;
		public int staminaRestoredPerTick;
		public Map<String, Integer> staminaMultipliers = new LinkedHashMap<>();
		public int staminaSyncRate;
	}
	
	// Config builder
	public static class CommonConfig
	{
		// Legacy generation
		IntValue legacyChance;
		IntValue minimumLegacies;
		IntValue maximumLegacies;
		
		// Stamina
		IntValue maxStamina;
		IntValue staminaRestoredPerTick;
		Map<String, IntValue> staminaMultipliers = new LinkedHashMap<>();
		IntValue staminaSyncRate;
		
		public CommonConfig(ForgeConfigSpec.Builder builder)
		{
			/* Legacy generation */
			
			builder.push("Legacy generation");
			
			legacyChance = builder.comment("Upon a new player joining a world, he/she has a chance to recieve legacies. This is that chance, out of 100, as a percentage (%).")
					.translation(LorienLegacies.MODID + ".config." + "legacyChance")
					.defineInRange("legacyChance", 100, 0, 100);
			
			minimumLegacies = builder.comment("Minimum legacies if given")
					.translation(LorienLegacies.MODID + ".config." + "minimumLegacies")
					.defineInRange("minimumLegacies", 2, 1, LegacyManager.NUM_LEGACIES);
			
			maximumLegacies = builder.comment("Maximum legacies if given")
					.translation(LorienLegacies.MODID + ".config." + "maximumLegacies")
					.defineInRange("maximumLegacies", 3, 1, LegacyManager.NUM_LEGACIES);
			
			builder.pop();
			
			/* Stamina */
			
			builder.push("Stamina");
			
			maxStamina = builder.comment("Maximum legacy stamina a player can have")
					.translation(LorienLegacies.MODID + ".config." + "maxStamina")
					.defineInRange("maxStamina", 50, 1, Integer.MAX_VALUE);
			
			staminaRestoredPerTick = builder.comment("The amount of stamina restored per tick")
					.translation(LorienLegacies.MODID + ".config." + "staminaRestoredPerTick")
					.defineInRange("staminaRestoredPerTick", 1, 0, Integer.MAX_VALUE);
			
			for (String legacy : LegacyManager.CONSTANT_LEGACIES)
			{
				staminaMultipliers.put(legacy, builder.comment(legacy + " stamina multiplier")
					.translation(LorienLegacies.MODID + ".config." + legacy + "StaminaMultiplier")
					.defineInRange(legacy + "StaminaMultiplier", 1, 0, Integer.MAX_VALUE));
			}
			
			staminaSyncRate = builder.comment("The amount of ticks to wait every time before sending stamina data to client (so as to reduce lag)")
					.translation(LorienLegacies.MODID + ".config." + "staminaSyncRate")
					.defineInRange("staminaSyncRate", 1, 1, 10);
			
			builder.pop();
		}
	}
	
	// Baking (because getters and setters are more expensive)
	public static void BakeConfig()
	{
		legacyGeneration.legacyChance = 		CONFIG.legacyChance.get();
		legacyGeneration.minimumLegacies = 		CONFIG.minimumLegacies.get();
		legacyGeneration.maximumLegacies = 		CONFIG.maximumLegacies.get();
		
		legacyStamina.maxStamina = 				CONFIG.maxStamina.get();
		legacyStamina.staminaRestoredPerTick = 	CONFIG.staminaRestoredPerTick.get();
		legacyStamina.staminaSyncRate = 		CONFIG.staminaSyncRate.get();
		
		for (Map.Entry<String, IntValue> entry : CONFIG.staminaMultipliers.entrySet())
			legacyStamina.staminaMultipliers.put(entry.getKey(), entry.getValue().get());
		
	}
	
	@SubscribeEvent
	public static void OnModConfigEvent(final ModConfig.ModConfigEvent configEvent)
	{
		if (configEvent.getConfig().getSpec() == COMMON_SPEC)
		{
			BakeConfig();
		}
	}
	
}
