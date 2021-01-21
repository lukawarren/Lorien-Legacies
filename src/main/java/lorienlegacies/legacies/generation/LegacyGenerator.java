package lorienlegacies.legacies.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lorienlegacies.config.ConfigLorienLegacies;
import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.PlayerLegacyData;

/*
 * Generates a list of random legacies for a player
 */
public class LegacyGenerator
{

	Random random = new Random();
	
	public LegacyGenerator(long seed)
	{
		random.setSeed(seed);
	}
	
	public void GenerateRandomLegacies(PlayerLegacyData playerData)
	{
		// Zero-out all legacies
		for (String key : playerData.legacies.keySet())
			playerData.legacies.put(key, 0);
		
		// Decide if to give player legacies
		boolean playerShouldHaveLegacies = MakeDecisionWithChance(ConfigLorienLegacies.legacyChance);
		int numLegacies = GetNumberBetween(ConfigLorienLegacies.minimumLegacies, ConfigLorienLegacies.maximumLegacies);
		
		if (playerShouldHaveLegacies == false || numLegacies == 0) return;
		
		// Chose 3 random legacies by shuffling keys...
		List<String> shuffledLegacies = new ArrayList<String>(playerData.legacies.keySet());
		Collections.shuffle(shuffledLegacies);
		
		// ...then getting the first n
		List<String> obtainedLegacies = shuffledLegacies.subList(0, numLegacies);
		LorienLegacies.logger.info("Legacies generated: {}", obtainedLegacies.toString());
		
		// Use these legacies as keys in the hashmap to "enable" them
		for (String legacy : obtainedLegacies)
			playerData.legacies.put(legacy, 1);
	}
	
	private boolean MakeDecisionWithChance(int percentage)
	{
		int number = random.nextInt(101); // Get a number from 0-100 (inclusive)
		return number <= percentage;
	}
	
	private int GetNumberBetween(int min, int max)
	{
		return random.nextInt(max + 1) + min;
	}
	
}
