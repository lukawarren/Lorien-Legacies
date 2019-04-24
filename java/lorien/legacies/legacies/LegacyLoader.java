package lorien.legacies.legacies;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.AvexLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.implementations.LumenLegacy;
import lorien.legacies.legacies.implementations.NovisLegacy;
import lorien.legacies.legacies.implementations.NoxenLegacy;
import lorien.legacies.legacies.implementations.PondusLegacy;
import lorien.legacies.legacies.implementations.RegenerasLegacy;
import lorien.legacies.legacies.implementations.SubmariLegacy;
import lorien.legacies.legacies.implementations.Telekinesis;
import lorien.legacies.legacies.worldSave.LegacyData;
import lorien.legacies.legacies.worldSave.LegacyDataHolder;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.util.Random;

public class LegacyLoader {
	
	public static final int CHANCE_OF_LEGACIES = 10; // 10%
	public static final int AMOUNT_OF_LEGACIES_GIFTED = 3;
	public static final int NUMBER_OF_LEGACIES = 9; // Used for evenly splitting probability in generateLegacyImplimentations() - don't include Telekinesis!
	
	// Returns either true or false, depending on whether or not player should have legacies
	public static void loadLegacies(LegacyManager playerLegacyManager, boolean forceLegacies)
	{		
		// Attempt to load world data
		LegacyWorldSaveData saveData = LegacyWorldSaveData.get(playerLegacyManager.player.world);
		LegacyDataHolder legacyData = saveData.getLegacyData();
		
		// Check if legacies are already assigned to player
		boolean playerIsAlreadyAsigned = legacyData.legacyDataSaved.value;
		
		if (playerIsAlreadyAsigned && !forceLegacies)
		{
			LorienLegacies.print("Loading legacies data for player with UUID " + playerLegacyManager.player.getUniqueID());
			
			for (LegacyData l : legacyData.data)
				System.out.println("Value with name " + l.name + " is set to " + l.value);
			
			
			
			if (legacyData.legaciesEnabled.value)
			{
				playerLegacyManager.player.sendMessage(new TextComponentString("Your legacies have been loaded! You are blessed with:").setStyle(new Style().setColor(TextFormatting.RED)));			
				loadLegacyImplimentations(playerLegacyManager, legacyData);			
			}
			
		}
		else
		{
			int n = getRandomNumberInRange(0, 100);
			
			if (forceLegacies)
				n = CHANCE_OF_LEGACIES;
			
			if (n <= CHANCE_OF_LEGACIES) // 10% chance to return true
			{
				playerLegacyManager.legaciesEnabled = true;
				playerLegacyManager.player.sendMessage(new TextComponentString("You have been blessed with legacies! They are:").setStyle(new Style().setColor(TextFormatting.RED)));
			
				// Give player a random legacy
				generateLegacyImplimentations(playerLegacyManager, forceLegacies);
				
			}
			else
			{
				playerLegacyManager.legaciesEnabled = false;
				//LorienLegacies.print("Player with UUID " + playerLegacyManager.player.getUniqueID() + " ___ has not been blessed with legacies."); // Should actually set playerIsAlreadyAsigned to be "false" in save
			}
			
			saveLegacyImplimentations(playerLegacyManager, saveData);
		}
	}
	
	// Loads from save
	private static void loadLegacyImplimentations(LegacyManager playerLegacyManager, LegacyDataHolder legacyData)
	{
		playerLegacyManager.legaciesEnabled = true;
		
		if (legacyData.lumenLegacyEnabled.value)
		{	
			playerLegacyManager.lumenLegacyEnabled = true;
			new LumenLegacy().blessedMessage(playerLegacyManager.player);
		} 
		if (legacyData.noxenLegacyEnabled.value)
		{
			playerLegacyManager.noxenLegacyEnabled = true;
			new NoxenLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.submariLegacyEnabled.value)
		{
			playerLegacyManager.submariLegacyEnabled = true;
			new SubmariLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.novisLegacyEnabled.value)
		{
			playerLegacyManager.novisLegacyEnabled = true;
			new NovisLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.accelixLegacyEnabled.value)
		{
			playerLegacyManager.accelixLegacyEnabled = true;
			new AccelixLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.fortemLegacyEnabled.value)
		{
			playerLegacyManager.fortemLegacyEnabled = true;
			new FortemLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.pondusLegacyEnabled.value)
		{
			playerLegacyManager.pondusLegacyEnabled = true;
			new PondusLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.regenerasLegacyEnabled.value)
		{
			playerLegacyManager.regenerasLegacyEnabled = true;
			new RegenerasLegacy().blessedMessage(playerLegacyManager.player);
		}
		if (legacyData.avexLegacyEnabled.value)
		{
			playerLegacyManager.avexLegacyEnabled = true;
			new AvexLegacy().blessedMessage(playerLegacyManager.player);
		}
		new Telekinesis().blessedMessage(playerLegacyManager.player);
	}
	
	// Saves legacies to world
	public static void saveLegacyImplimentations(LegacyManager playerLegacyManager, LegacyWorldSaveData saveData)
	{
		LegacyDataHolder legacyDataHolder = new LegacyDataHolder();
		legacyDataHolder.legacyDataSaved.value = true;
		
		legacyDataHolder.legaciesEnabled.value = playerLegacyManager.legaciesEnabled;
		
		legacyDataHolder.accelixLegacyEnabled.value = playerLegacyManager.accelixLegacyEnabled;
		legacyDataHolder.avexLegacyEnabled.value = playerLegacyManager.avexLegacyEnabled;
		legacyDataHolder.fortemLegacyEnabled.value = playerLegacyManager.fortemLegacyEnabled;
		legacyDataHolder.lumenLegacyEnabled.value = playerLegacyManager.lumenLegacyEnabled;
		legacyDataHolder.novisLegacyEnabled.value = playerLegacyManager.novisLegacyEnabled;
		legacyDataHolder.noxenLegacyEnabled.value = playerLegacyManager.noxenLegacyEnabled;
		legacyDataHolder.pondusLegacyEnabled.value = playerLegacyManager.pondusLegacyEnabled;
		legacyDataHolder.regenerasLegacyEnabled.value = playerLegacyManager.regenerasLegacyEnabled;
		legacyDataHolder.submariLegacyEnabled.value = playerLegacyManager.submariLegacyEnabled;
		
		saveData.setLegacyData(legacyDataHolder, playerLegacyManager.player.world);
	}
	
	// Randomly chooses x amount of legacies
	private static void generateLegacyImplimentations(LegacyManager playerLegacyManager, boolean forceLegacies)
	{
		if (forceLegacies)
		{
			// get rid of all legacies first
			playerLegacyManager.accelixLegacyEnabled = false;
			playerLegacyManager.avexLegacyEnabled = false;
			playerLegacyManager.fortemLegacyEnabled = false;
			playerLegacyManager.lumenLegacyEnabled = false;
			playerLegacyManager.novisLegacyEnabled = false;
			playerLegacyManager.noxenLegacyEnabled = false;
			playerLegacyManager.pondusLegacyEnabled = false;
			playerLegacyManager.regenerasLegacyEnabled = false;
			playerLegacyManager.submariLegacyEnabled = false;
		}
		
		float chanceOfIndividualLegacyBeingChosen = AMOUNT_OF_LEGACIES_GIFTED / NUMBER_OF_LEGACIES;
		
		int toGift = AMOUNT_OF_LEGACIES_GIFTED;
		for (int i = 0; i < AMOUNT_OF_LEGACIES_GIFTED; i++)
		{
			// Choose what legacy the player should get
			float n = getRandomNumberInRange(1, NUMBER_OF_LEGACIES);
			
			// If player already has legacy, choose another number
			if (checkIfLegacyAlreadyAssigned(n, playerLegacyManager, false))
			{
				toGift++;
			}
			else
			{
				if (n == 1)
				{	
					playerLegacyManager.lumenLegacyEnabled = true;
					new LumenLegacy().blessedMessage(playerLegacyManager.player);
				} 
				else if (n == 2)
				{
					playerLegacyManager.noxenLegacyEnabled = true;
					new NoxenLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 3)
				{
					playerLegacyManager.submariLegacyEnabled = true;
					new SubmariLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 4)
				{
					playerLegacyManager.novisLegacyEnabled = true;
					new NovisLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 5)
				{
					playerLegacyManager.accelixLegacyEnabled = true;
					new AccelixLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 6)
				{
					playerLegacyManager.fortemLegacyEnabled = true;
					new FortemLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 7)
				{
					playerLegacyManager.pondusLegacyEnabled = true;
					new PondusLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 8)
				{
					playerLegacyManager.regenerasLegacyEnabled = true;
					new RegenerasLegacy().blessedMessage(playerLegacyManager.player);
				}
				else if (n == 9)
				{
					playerLegacyManager.avexLegacyEnabled = true;
					new AvexLegacy().blessedMessage(playerLegacyManager.player);
				}
			}
		}
		new Telekinesis().blessedMessage(playerLegacyManager.player);
	}
	
	private static boolean checkIfLegacyAlreadyAssigned(float n, LegacyManager playerLegacyManager, boolean forceLegacies)
	{
		if (forceLegacies)
			return false;
		
		if (n == 1 && playerLegacyManager.lumenLegacyEnabled)
			return true;
		if (n == 2 && playerLegacyManager.noxenLegacyEnabled)
			return true;
		if (n == 3 && playerLegacyManager.submariLegacyEnabled)
			return true;
		if (n == 4 && playerLegacyManager.novisLegacyEnabled)
			return true;
		if (n == 5 && playerLegacyManager.accelixLegacyEnabled)
			return true;
		if (n == 6 && playerLegacyManager.fortemLegacyEnabled)
			return true;
		if (n == 7 && playerLegacyManager.pondusLegacyEnabled)
			return true;
		if (n == 8 && playerLegacyManager.regenerasLegacyEnabled)
			return true;
		if (n == 9 && playerLegacyManager.avexLegacyEnabled)
			return true;
		
		return false;
	}
	
	// A little helper function to increase readability
	private static int getRandomNumberInRange(int min, int max) {

		/*
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}*/

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
}
