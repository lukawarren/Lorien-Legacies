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
	
	public void GenerateRandomLegacies(PlayerLegacyData playerData, boolean forceLegacies)
	{
		// Zero-out all legacies
		for (String key : playerData.legacies.keySet())
			playerData.legacies.put(key, 0);
		
		// Decide if to give player legacies
		boolean playerShouldHaveLegacies = MakeDecisionWithChance(ConfigLorienLegacies.legacyGeneration.legacyChance) || forceLegacies;
		if (playerShouldHaveLegacies == false) return;
		
		// Construct "magic hat" with X entries for each legacy
		List<String> legacyChoices = new ArrayList<String>();
		for (String legacy : playerData.legacies.keySet())
		{
			// Get likelihood multiplier from legacy and config
			int times = LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(legacy).GetGenerationWeighting();
			times *= ConfigLorienLegacies.legacyGeneration.likelihoodMultipliers.get(legacy);
			
			// Place in "magic hat"
			for (int i = 0; i < times; ++i) legacyChoices.add(legacy);
		}
		
		// Shuffle entries
		Collections.shuffle(legacyChoices);
		
		// Pull legacies from "magic hat", avoiding duplicates
		int pointsLeft = ConfigLorienLegacies.legacyGeneration.legacyPoints;
		while (HasSufficientPointsToContinue(pointsLeft, legacyChoices))
		{
			// Pick legacy
			String legacy = legacyChoices.get(0);
			
			// Remove all instances
			legacyChoices.removeIf((e) -> e == legacy);
			
			// Remove points
			int points = LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(legacy).GetGenerationPoints();
			points *= ConfigLorienLegacies.legacyGeneration.costMultipliers.get(legacy);
			pointsLeft -= points;
			
			// Give to player
			playerData.legacies.put(legacy, 1);
		}
		
		// Give telekinesis (which hasn't been in that hat)
		playerData.legacies.put("Telekinesis", 1);
	}
	
	private boolean MakeDecisionWithChance(int percentage)
	{
		int number = random.nextInt(101); // Get a number from 0-100 (inclusive)
		return number <= percentage;
	}
	
	private boolean HasSufficientPointsToContinue(int points, List<String> legacyChoices)
	{
		if (legacyChoices.size() == 0) return false;
		
		int minPointsRequired = Integer.MAX_VALUE;
		for (String l : legacyChoices)
		{
			// Get cost
			int times = LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(l).GetGenerationPoints();
			times *= ConfigLorienLegacies.legacyGeneration.costMultipliers.get(l);
			
			if (times < minPointsRequired) minPointsRequired = times;
		}
		
		if (points < minPointsRequired) return false;
		
		return true;
	}
	
}
