package lorien.legacies.legacies;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.AvexLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.implementations.GlacenLegacy;
import lorien.legacies.legacies.implementations.LumenLegacy;
import lorien.legacies.legacies.implementations.NovisLegacy;
import lorien.legacies.legacies.implementations.NoxenLegacy;
import lorien.legacies.legacies.implementations.PondusLegacy;
import lorien.legacies.legacies.implementations.RegenerasLegacy;
import lorien.legacies.legacies.implementations.SubmariLegacy;
import lorien.legacies.legacies.implementations.Telekinesis;
import lorien.legacies.legacies.worldSave.LegacyEnabledData;
import lorien.legacies.legacies.worldSave.LegacyDataHolder;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import lorien.legacies.network.NetworkHandler;
import lorien.legacies.network.mesages.MessageLegacyData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.util.Random;

public class LegacyLoader {
	
	public static final int CHANCE_OF_LEGACIES = 10; // 10%
	public static final int AMOUNT_OF_LEGACIES_GIFTED = 3;
	public static final int NUMBER_OF_LEGACIES = 10; // Used for evenly splitting probability in generateLegacyImplimentations() - don't include Telekinesis!
	
	public static void generateLegacies(LegacyManager playerLegacyManager, boolean forceLegacies)
	{
		
		// Attempt to load world data
		boolean playerIsAlreadyAsigned;
		
		LegacyWorldSaveData saveData = LegacyWorldSaveData.get(playerLegacyManager.player.world); // Load previous data
		LegacyDataHolder legacyData = saveData.getLegacyDataForPlayer(playerLegacyManager.player.getUniqueID()); // Get data for player (if it exists)
		
		if (legacyData == null || legacyData.enabledData == null) // If it doens't exist
		{
			LegacyWorldSaveData.addPlayer(playerLegacyManager.player.getUniqueID());
			playerIsAlreadyAsigned = false;
		}
		else
		{
			playerIsAlreadyAsigned = true;
		}
		
		if (playerIsAlreadyAsigned && !forceLegacies && legacyData.legaciesEnabled.value)
		{	
			LorienLegacies.print("Loading legacies data for player with UUID " + playerLegacyManager.player.getUniqueID());
			
			loadLegaciesFromSave(playerLegacyManager, legacyData);
			
		}
		else
		{
			int n = getRandomNumberInRange(0, 100);
			
			if (forceLegacies)
				n = CHANCE_OF_LEGACIES;
			
			if (n <= CHANCE_OF_LEGACIES) // 10% chance to return true
			{
				playerLegacyManager.legaciesEnabled = true;
			
				// Give player random legacies
				chooseLegacies(playerLegacyManager, forceLegacies);
				
			}
			else
			{
				playerLegacyManager.legaciesEnabled = false;
			}
			
			saveLegaciesToSave(playerLegacyManager, saveData);
		}
		
		sendLegaciesToClient(playerLegacyManager, true);
		
	}
	
	public static void sendLegaciesToClient(LegacyManager playerLegacyManager, boolean shouldShowMessage)
	{
		MessageLegacyData messageLegacyData = new MessageLegacyData();
		messageLegacyData.legaciesEnabled = playerLegacyManager.legaciesEnabled;
		messageLegacyData.lumenLegacyEnabled = playerLegacyManager.lumenLegacyEnabled;
		messageLegacyData.noxenLegacyEnabled = playerLegacyManager.noxenLegacyEnabled;
		messageLegacyData.submariLegacyEnabled = playerLegacyManager.submariLegacyEnabled;
		messageLegacyData.novisLegacyEnabled = playerLegacyManager.novisLegacyEnabled;
		messageLegacyData.accelixLegacyEnabled = playerLegacyManager.accelixLegacyEnabled;
		messageLegacyData.fortemLegacyEnabled = playerLegacyManager.fortemLegacyEnabled;
		messageLegacyData.pondusLegacyEnabled = playerLegacyManager.pondusLegacyEnabled;
		messageLegacyData.regenerasLegacyEnabled = playerLegacyManager.regenerasLegacyEnabled;
		messageLegacyData.avexLegacyEnabled = playerLegacyManager.avexLegacyEnabled;
		messageLegacyData.glacenLegacyEnabled = playerLegacyManager.glacenLegacyEnabled;
		
		messageLegacyData.lumenLegacyLevel = playerLegacyManager.lumenLegacy.currentLegacyLevel;
		messageLegacyData.noxenLegacyLevel = playerLegacyManager.noxenLegacy.currentLegacyLevel;
		messageLegacyData.submariLegacyLevel = playerLegacyManager.submariLegacy.currentLegacyLevel;
		messageLegacyData.novisLegacyLevel = playerLegacyManager.novisLegacy.currentLegacyLevel;
		messageLegacyData.accelixLegacyLevel = playerLegacyManager.accelixLegacy.currentLegacyLevel;
		messageLegacyData.fortemLegacyLevel = playerLegacyManager.fortemLegacy.currentLegacyLevel;
		messageLegacyData.pondusLegacyLevel = playerLegacyManager.pondusLegacy.currentLegacyLevel;
		messageLegacyData.regenerasLegacyLevel = playerLegacyManager.regenerasLegacy.currentLegacyLevel;
		messageLegacyData.avexLegacyLevel = playerLegacyManager.avexLegacy.currentLegacyLevel;
		messageLegacyData.glacenLegacyLevel = playerLegacyManager.glacenLegacy.currentLegacyLevel;
		
		messageLegacyData.lumenLegacyXP = playerLegacyManager.lumenLegacy.xp;
		messageLegacyData.noxenLegacyXP = playerLegacyManager.noxenLegacy.xp;
		messageLegacyData.submariLegacyXP = playerLegacyManager.submariLegacy.xp;
		messageLegacyData.novisLegacyXP = playerLegacyManager.novisLegacy.xp;
		messageLegacyData.accelixLegacyXP = playerLegacyManager.accelixLegacy.xp;
		messageLegacyData.fortemLegacyXP = playerLegacyManager.fortemLegacy.xp;
		messageLegacyData.pondusLegacyXP = playerLegacyManager.pondusLegacy.xp;
		messageLegacyData.regenerasLegacyXP = playerLegacyManager.regenerasLegacy.xp;
		messageLegacyData.avexLegacyXP = playerLegacyManager.avexLegacy.xp;
		messageLegacyData.glacenLegacyXP = playerLegacyManager.glacenLegacy.xp;
		
		messageLegacyData.shouldSendMessage = shouldShowMessage;
		
		NetworkHandler.sendToPlayer(messageLegacyData, (EntityPlayerMP) playerLegacyManager.player);
	}
	
	
	// Loads from save
	private static void loadLegaciesFromSave(LegacyManager playerLegacyManager, LegacyDataHolder legacyData)
	{
		playerLegacyManager.legaciesEnabled = legacyData.legaciesEnabled.value;
		playerLegacyManager.lumenLegacyEnabled = legacyData.lumenLegacyEnabled.value;
		playerLegacyManager.noxenLegacyEnabled = legacyData.noxenLegacyEnabled.value;
		playerLegacyManager.submariLegacyEnabled = legacyData.submariLegacyEnabled.value;
		playerLegacyManager.novisLegacyEnabled = legacyData.novisLegacyEnabled.value;
		playerLegacyManager.accelixLegacyEnabled = legacyData.accelixLegacyEnabled.value;
		playerLegacyManager.fortemLegacyEnabled = legacyData.fortemLegacyEnabled.value;
		playerLegacyManager.pondusLegacyEnabled = legacyData.pondusLegacyEnabled.value;
		playerLegacyManager.regenerasLegacyEnabled = legacyData.regenerasLegacyEnabled.value;
		playerLegacyManager.avexLegacyEnabled = legacyData.avexLegacyEnabled.value;
		playerLegacyManager.glacenLegacyEnabled = legacyData.glacenLegacyEnabled.value;
		
		playerLegacyManager.lumenLegacy.currentLegacyLevel =  legacyData.lumenLegacyLevel.value;
		playerLegacyManager.noxenLegacy.currentLegacyLevel =  legacyData.noxenLegacyLevel.value;
		playerLegacyManager.submariLegacy.currentLegacyLevel =  legacyData.submariLegacyLevel.value;
		playerLegacyManager.novisLegacy.currentLegacyLevel =  legacyData.novisLegacyLevel.value;
		playerLegacyManager.accelixLegacy.currentLegacyLevel =  legacyData.accelixLegacyLevel.value;
		playerLegacyManager.fortemLegacy.currentLegacyLevel =  legacyData.fortemLegacyLevel.value;
		playerLegacyManager.pondusLegacy.currentLegacyLevel =  legacyData.pondusLegacyLevel.value;
		playerLegacyManager.regenerasLegacy.currentLegacyLevel =  legacyData.regenerasLegacyLevel.value;
		playerLegacyManager.avexLegacy.currentLegacyLevel =  legacyData.avexLegacyLevel.value;
		playerLegacyManager.glacenLegacy.currentLegacyLevel =  legacyData.glacenLegacyLevel.value;
		
		playerLegacyManager.lumenLegacy.xp =  legacyData.lumenLegacyXP.value;
		playerLegacyManager.noxenLegacy.xp =  legacyData.noxenLegacyXP.value;
		playerLegacyManager.submariLegacy.xp =  legacyData.submariLegacyXP.value;
		playerLegacyManager.novisLegacy.xp =  legacyData.novisLegacyXP.value;
		playerLegacyManager.accelixLegacy.xp =  legacyData.accelixLegacyXP.value;
		playerLegacyManager.fortemLegacy.xp =  legacyData.fortemLegacyXP.value;
		playerLegacyManager.pondusLegacy.xp =  legacyData.pondusLegacyXP.value;
		playerLegacyManager.regenerasLegacy.xp =  legacyData.regenerasLegacyXP.value;
		playerLegacyManager.avexLegacy.xp =  legacyData.avexLegacyXP.value;
		playerLegacyManager.glacenLegacy.xp =  legacyData.glacenLegacyXP.value;
	}
	
	// Saves legacies to world
	public static void saveLegaciesToSave(LegacyManager playerLegacyManager, LegacyWorldSaveData saveData)
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
		legacyDataHolder.glacenLegacyEnabled.value = playerLegacyManager.glacenLegacyEnabled;
		
		legacyDataHolder.accelixLegacyLevel.value = playerLegacyManager.accelixLegacy.currentLegacyLevel;
		legacyDataHolder.avexLegacyLevel.value = playerLegacyManager.avexLegacy.currentLegacyLevel;
		legacyDataHolder.fortemLegacyLevel.value = playerLegacyManager.fortemLegacy.currentLegacyLevel;
		legacyDataHolder.lumenLegacyLevel.value = playerLegacyManager.lumenLegacy.currentLegacyLevel;
		legacyDataHolder.novisLegacyLevel.value = playerLegacyManager.novisLegacy.currentLegacyLevel;
		legacyDataHolder.noxenLegacyLevel.value = playerLegacyManager.noxenLegacy.currentLegacyLevel;
		legacyDataHolder.pondusLegacyLevel.value = playerLegacyManager.pondusLegacy.currentLegacyLevel;
		legacyDataHolder.regenerasLegacyLevel.value = playerLegacyManager.regenerasLegacy.currentLegacyLevel;
		legacyDataHolder.submariLegacyLevel.value = playerLegacyManager.submariLegacy.currentLegacyLevel;
		legacyDataHolder.glacenLegacyLevel.value = playerLegacyManager.glacenLegacy.currentLegacyLevel;
		
		legacyDataHolder.accelixLegacyXP.value = playerLegacyManager.accelixLegacy.xp;
		legacyDataHolder.avexLegacyXP.value = playerLegacyManager.avexLegacy.xp;
		legacyDataHolder.fortemLegacyXP.value = playerLegacyManager.fortemLegacy.xp;
		legacyDataHolder.lumenLegacyXP.value = playerLegacyManager.lumenLegacy.xp;
		legacyDataHolder.novisLegacyXP.value = playerLegacyManager.novisLegacy.xp;
		legacyDataHolder.noxenLegacyXP.value = playerLegacyManager.noxenLegacy.xp;
		legacyDataHolder.pondusLegacyXP.value = playerLegacyManager.pondusLegacy.xp;
		legacyDataHolder.regenerasLegacyXP.value = playerLegacyManager.regenerasLegacy.xp;
		legacyDataHolder.submariLegacyXP.value = playerLegacyManager.submariLegacy.xp;
		legacyDataHolder.glacenLegacyXP.value = playerLegacyManager.glacenLegacy.xp;
		
		System.out.println(legacyDataHolder.submariLegacyXP.value);
		
		saveData.setLegacyData(legacyDataHolder, playerLegacyManager.player.world, playerLegacyManager.player.getUniqueID());
		
	}
	
	// Randomly chooses x amount of legacies
	private static void chooseLegacies(LegacyManager playerLegacyManager, boolean forceLegacies)
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
			playerLegacyManager.glacenLegacyEnabled = false;
			
			playerLegacyManager.accelixLegacy.currentLegacyLevel = 0;
			playerLegacyManager.avexLegacy.currentLegacyLevel = 0;
			playerLegacyManager.fortemLegacy.currentLegacyLevel = 0;
			playerLegacyManager.lumenLegacy.currentLegacyLevel = 0;
			playerLegacyManager.novisLegacy.currentLegacyLevel = 0;
			playerLegacyManager.noxenLegacy.currentLegacyLevel = 0;
			playerLegacyManager.pondusLegacy.currentLegacyLevel = 0;
			playerLegacyManager.regenerasLegacy.currentLegacyLevel = 0;
			playerLegacyManager.submariLegacy.currentLegacyLevel = 0;
			playerLegacyManager.glacenLegacy.currentLegacyLevel = 0;
			
			playerLegacyManager.accelixLegacy.xp = 0;
			playerLegacyManager.avexLegacy.xp = 0;
			playerLegacyManager.fortemLegacy.xp = 0;
			playerLegacyManager.lumenLegacy.xp = 0;
			playerLegacyManager.novisLegacy.xp = 0;
			playerLegacyManager.noxenLegacy.xp = 0;
			playerLegacyManager.pondusLegacy.xp = 0;
			playerLegacyManager.regenerasLegacy.xp = 0;
			playerLegacyManager.submariLegacy.xp = 0;
			playerLegacyManager.glacenLegacy.xp = 0;
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
				} 
				else if (n == 2)
				{
					playerLegacyManager.noxenLegacyEnabled = true;
				}
				else if (n == 3)
				{
					playerLegacyManager.submariLegacyEnabled = true;
				}
				else if (n == 4)
				{
					playerLegacyManager.novisLegacyEnabled = true;
				}
				else if (n == 5)
				{
					playerLegacyManager.accelixLegacyEnabled = true;
				}
				else if (n == 6)
				{
					playerLegacyManager.fortemLegacyEnabled = true;
				}
				else if (n == 7)
				{
					playerLegacyManager.pondusLegacyEnabled = true;
				}
				else if (n == 8)
				{
					playerLegacyManager.regenerasLegacyEnabled = true;
				}
				else if (n == 9)
				{
					playerLegacyManager.avexLegacyEnabled = true;
				}
				else if (n == 10)
				{
					playerLegacyManager.glacenLegacyEnabled = true;
				}
			}
		}
		
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
		if (n == 10 && playerLegacyManager.glacenLegacyEnabled)
			return true;
		
		return false;

	}
	
	// A little helper function to increase readability
	private static int getRandomNumberInRange(int min, int max)
	{

		/*
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}*/

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static void displayBlessedMessgaes(EntityPlayer player)
	{
		player.sendMessage(new TextComponentString("----------------------------------------").setStyle(new Style().setColor(TextFormatting.BLUE)));
		player.sendMessage(new TextComponentString("You are blessed with legacies! They are:").setStyle(new Style().setColor(TextFormatting.RED)));
		
		if (LorienLegacies.clientLegacyManager.lumenLegacyEnabled)
			LorienLegacies.clientLegacyManager.lumenLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.noxenLegacyEnabled)
			LorienLegacies.clientLegacyManager.noxenLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.submariLegacyEnabled)
			LorienLegacies.clientLegacyManager.submariLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.novisLegacyEnabled)
			LorienLegacies.clientLegacyManager.novisLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.accelixLegacyEnabled)
			LorienLegacies.clientLegacyManager.accelixLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.fortemLegacyEnabled)
			LorienLegacies.clientLegacyManager.fortemLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.pondusLegacyEnabled)
			LorienLegacies.clientLegacyManager.pondusLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.regenerasLegacyEnabled)
			LorienLegacies.clientLegacyManager.regenerasLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.avexLegacyEnabled)
			LorienLegacies.clientLegacyManager.avexLegacy.blessedMessage(player);
		if (LorienLegacies.clientLegacyManager.glacenLegacyEnabled)
			LorienLegacies.clientLegacyManager.glacenLegacy.blessedMessage(player);
		
		LorienLegacies.clientLegacyManager.telekinesis.blessedMessage(player);
		player.sendMessage(new TextComponentString("----------------------------------------").setStyle(new Style().setColor(TextFormatting.BLUE)));
	}
	
}
