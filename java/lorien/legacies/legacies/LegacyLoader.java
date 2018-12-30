package lorien.legacies.legacies;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.swing.TextComponent;
import scala.util.Random;

public class LegacyLoader {
	
	public static final int CHANCE_OF_LEGACIES = 10; // 10%
	public static final int AMOUNT_OF_LEGACIES_GIFTED = 3;
	public static final int NUMBER_OF_LEGACIES = 8; // Used for evenly splitting probability in generateLegacyImplimentations()
	
	// Returns either true or false, depending on whether or not player should have legacies
	public static void loadLegacies(LegacyManager playerLegacyManager, boolean forceLegacies)
	{
		// Check if legacies are already assigned to player
		boolean playerIsAlreadyAsigned = false; // TODO: Set to true if exists in save
		
		if (playerIsAlreadyAsigned)
		{
			LorienLegacies.print("Loading legacies for player with UUID " + playerLegacyManager.player.getUniqueID());
			
			// Retrieve from save what legacies they have
			boolean playerPreviouslyHadLegacies = true; // TODO: Load this using capabilites class
			
			if (playerPreviouslyHadLegacies)
			{
				playerLegacyManager.player.sendMessage(new TextComponentString("Your legacies have been loaded! You are blessed with:").setStyle(new Style().setColor(TextFormatting.RED)));
				
				loadLegacyImplimentations(playerLegacyManager);
				
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
		}
	}
	
	// Loads from save
	private static void loadLegacyImplimentations(LegacyManager playerLegacyManager)
	{
		// TODO: Load from save
	}
	
	// Randomly chooses x amount of legacies
	private static void generateLegacyImplimentations(LegacyManager playerLegacyManager, boolean forceLegacies)
	{
		float chanceOfIndividualLegacyBeingChosen = AMOUNT_OF_LEGACIES_GIFTED / NUMBER_OF_LEGACIES;
		
		for (int i = 0; i < AMOUNT_OF_LEGACIES_GIFTED; i++)
		{
			// Choose what legacy the player should get
			float n = getRandomNumberInRange(1, NUMBER_OF_LEGACIES);
			
			// If player already has legacy, choose another number
			if (checkIfLegacyAlreadyAssigned(n, playerLegacyManager, forceLegacies))
			{
				i++;
			}
			else
			{
				if (n == 1)
				{	
					playerLegacyManager.lumenLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Lumen - grants fire resistance and fire powers").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				} 
				else if (n == 2)
				{
					playerLegacyManager.noxenLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Noxen - grants night vision").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				else if (n == 3)
				{
					playerLegacyManager.submariLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Submari - grants water breathing").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				else if (n == 4)
				{
					playerLegacyManager.novisLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Novis - grants invisibility at will").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				else if (n == 5)
				{
					playerLegacyManager.accelixLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Accelix - grants super speed at will").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				else if (n == 6)
				{
					playerLegacyManager.fortemLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Fortem - grants super strength").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				else if (n == 7)
				{
					playerLegacyManager.pondusLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Pondus - grants water walking and water breathing").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}
				else if (n == 8)
				{
					playerLegacyManager.regenerasLegacyEnabled = true;
					playerLegacyManager.player.sendMessage(new TextComponentString("Regeneras - grants regeneration").setStyle(new Style().setColor(TextFormatting.YELLOW)));
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
		if (n == 4 && playerLegacyManager.pondusLegacyEnabled)
			return true;
		if (n == 4 && playerLegacyManager.regenerasLegacyEnabled)
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
