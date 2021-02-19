package lorienlegacies.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.LegacyManager;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = LorienLegacies.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigLorienLegacies
{
	
	public static final float MORE_USER_FRIENDLY_MAX_DOUBLE_VALUE = 100000.0f;
	
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
	public static LegacyXP legacyXP = new LegacyXP();
	
	public static class LegacyGeneration
	{
		public int legacyChance;
		public int legacyPoints;
		public Map<String, Integer> likelihoodMultipliers = new LinkedHashMap<>();
		public Map<String, Integer> costMultipliers = new LinkedHashMap<>();
	}
	
	public static class LegacyStamina
	{
		public float maxStamina;
		public float staminaRestoredPerTick;
		public Map<String, Float> staminaMultipliers = new LinkedHashMap<>();
		public int staminaSyncRate;
	}
	
	public static class LegacyXP
	{
		public Map<String, Integer> xpMultipliers = new LinkedHashMap<>();
	}
	
	// Config builder
	public static class CommonConfig
	{
		// Legacy generation
		IntValue legacyChance;
		IntValue legacyPoints;
		Map<String, IntValue> likelihoodMultipliers = new LinkedHashMap<>();
		Map<String, IntValue> costMultipliers = new LinkedHashMap<>();
		
		// Stamina
		DoubleValue maxStamina;
		DoubleValue staminaRestoredPerTick;
		Map<String, DoubleValue> staminaMultipliers = new LinkedHashMap<>();
		IntValue staminaSyncRate;
		
		// XP
		Map<String, IntValue> xpMultipliers = new LinkedHashMap<>();
		
		public CommonConfig(ForgeConfigSpec.Builder builder)
		{
			/* Legacy generation */
			
			builder.push("Legacy generation");
			
			legacyChance = builder.comment("Upon a new player joining a world, he/she has a chance to recieve legacies. This is that chance, out of 100, as a percentage (%).")
					.translation(LorienLegacies.MODID + ".config." + "legacyChance")
					.defineInRange("legacyChance", 100, 0, 100);
			
			legacyPoints = builder.comment("Amount of legacy points available - more points means more legacies.")
					.translation(LorienLegacies.MODID + ".config." + "legacyPoints")
					.defineInRange("legacyPoints", 5, 0, Integer.MAX_VALUE);
			
			for (String legacy : LegacyManager.CONSTANT_LEGACIES)
			{
				likelihoodMultipliers.put(legacy, builder.comment(legacy + " likelihood multiplier")
					.translation(LorienLegacies.MODID + ".config." + legacy + "LikelihoodMultiplier")
					.defineInRange(legacy + "LikelihoodMultiplier", 1, 0, Integer.MAX_VALUE));
			}
			
			for (String legacy : LegacyManager.CONSTANT_LEGACIES)
			{
				costMultipliers.put(legacy, builder.comment(legacy + " cost multiplier")
					.translation(LorienLegacies.MODID + ".config." + legacy + "CostMultiplier")
					.defineInRange(legacy + "CostMultiplier", 1, 1, Integer.MAX_VALUE));
			}
			
			builder.pop();
			
			/* Stamina */
			
			builder.push("Stamina");
			
			maxStamina = builder.comment("Maximum legacy stamina a player can have")
					.translation(LorienLegacies.MODID + ".config." + "maxStamina")
					.defineInRange("maxStamina", 50, 1, MORE_USER_FRIENDLY_MAX_DOUBLE_VALUE);
			
			staminaRestoredPerTick = builder.comment("The amount of stamina restored per tick")
					.translation(LorienLegacies.MODID + ".config." + "staminaRestoredPerTick")
					.defineInRange("staminaRestoredPerTick", 0.8f, 0, MORE_USER_FRIENDLY_MAX_DOUBLE_VALUE);
			
			for (String legacy : LegacyManager.CONSTANT_LEGACIES)
			{
				staminaMultipliers.put(legacy, builder.comment(legacy + " stamina multiplier")
					.translation(LorienLegacies.MODID + ".config." + legacy + "StaminaMultiplier")
					.defineInRange(legacy + "StaminaMultiplier", 1, 0, MORE_USER_FRIENDLY_MAX_DOUBLE_VALUE));
			}
			
			staminaSyncRate = builder.comment("The amount of ticks to wait every time before sending stamina data to client (so as to reduce lag)")
					.translation(LorienLegacies.MODID + ".config." + "staminaSyncRate")
					.defineInRange("staminaSyncRate", 1, 1, 10);
			
			builder.pop();
			
			/* XP */
			builder.push("XP");
			
			for (String legacy : LegacyManager.CONSTANT_LEGACIES)
			{
				xpMultipliers.put(legacy, builder.comment(legacy + " xp multiplier")
					.translation(LorienLegacies.MODID + ".config." + legacy + "XpMultiplier")
					.defineInRange(legacy + "XpMultiplier", 1, 0, Integer.MAX_VALUE));
			}
			
			builder.pop();
		}
	}
	
	// Baking (because getters and setters are more expensive)
	public static void BakeConfig()
	{
		legacyGeneration.legacyChance = 		CONFIG.legacyChance.get();
		legacyGeneration.legacyPoints = 		CONFIG.legacyPoints.get();
		
		for (Entry<String, IntValue> entry : CONFIG.likelihoodMultipliers.entrySet())
			legacyGeneration.likelihoodMultipliers.put(entry.getKey(), entry.getValue().get());
		
		for (Entry<String, IntValue> entry : CONFIG.costMultipliers.entrySet())
			legacyGeneration.costMultipliers.put(entry.getKey(), entry.getValue().get());
		
		legacyStamina.maxStamina = 				CONFIG.maxStamina.get().floatValue();
		legacyStamina.staminaRestoredPerTick = 	CONFIG.staminaRestoredPerTick.get().floatValue();
		legacyStamina.staminaSyncRate = 		CONFIG.staminaSyncRate.get();
		
		for (Map.Entry<String, DoubleValue> entry : CONFIG.staminaMultipliers.entrySet())
			legacyStamina.staminaMultipliers.put(entry.getKey(), entry.getValue().get().floatValue());
		
		for (Map.Entry<String, IntValue> entry : CONFIG.xpMultipliers.entrySet())
			legacyXP.xpMultipliers.put(entry.getKey(), entry.getValue().get());
		
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
