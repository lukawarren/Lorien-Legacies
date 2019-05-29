package lorien.legacies.network.mesages.legacyActions;

import lorien.legacies.legacies.LegacyManager;
import net.minecraft.entity.player.EntityPlayer;

public class LegacyActionConverter
{
	
	public static void performLegacyAction(LegacyManager legacyManager, LegacyAction action)
	{
		if (action == null)
			return;
		
		
		EntityPlayer player = legacyManager.player;
		
		if (action == LegacyAction.LumenFireball)
		{
			legacyManager.lumenLegacy.fireball(player);
		}
		
		else if (action == LegacyAction.LumenIgnition)
		{
			legacyManager.lumenLegacy.ignite(player);
		}
		
		else if (action == LegacyAction.Accelix)
		{
			legacyManager.accelixLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.Fortem)
		{
			legacyManager.fortemLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.Novis)
		{
			legacyManager.novisLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.Pondus)
		{
			legacyManager.pondusLegacy.toggle(player);
		}
		
		
	}
	
	public static LegacyAction legacyActionFromInt(int i)
	{
		
		if (i == 0)
			return LegacyAction.LumenFireball;
		else if (i == 1)
			return LegacyAction.LumenIgnition;
		else if (i == 2)
			return LegacyAction.Accelix;
		else if (i == 3)
			return LegacyAction.Fortem;
		else if (i == 4)
			return LegacyAction.Novis;
		else if (i == 5)
			return LegacyAction.Pondus;
		
		return null;
		
	}
	
	public static int intFromLegacyAction(LegacyAction i)
	{
		
		if (i == LegacyAction.LumenFireball)
			return 0;
		else if (i == LegacyAction.LumenIgnition)
			return 1;
		else if (i == LegacyAction.Accelix)
			return 2;
		else if (i == LegacyAction.Fortem)
			return 3;
		else if (i == LegacyAction.Novis)
			return 4;
		else if (i == LegacyAction.Pondus)
			return 5;
		
		return -1;
		
	}
	
}
