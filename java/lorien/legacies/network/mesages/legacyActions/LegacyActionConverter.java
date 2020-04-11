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
		
		if (action == LegacyAction.LumenFireball && legacyManager.lumenLegacyEnabled)
		{
			legacyManager.lumenLegacy.fireball(player);
		}
		
		else if (action == LegacyAction.LumenIgnition && legacyManager.lumenLegacyEnabled)
		{
			legacyManager.lumenLegacy.ignite(player);
		}
		
		else if (action == LegacyAction.Accelix && legacyManager.accelixLegacyEnabled)
		{
			legacyManager.accelixLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.Fortem && legacyManager.fortemLegacyEnabled)
		{
			legacyManager.fortemLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.Novis && legacyManager.novisLegacyEnabled)
		{
			legacyManager.novisLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.Pondus && legacyManager.pondusLegacyEnabled)
		{
			legacyManager.pondusLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.GlacenFreeze && legacyManager.glacenLegacyEnabled)
		{
			legacyManager.glacenLegacy.freezeWaterIfNeeded(player);
		}
		
		else if (action == LegacyAction.Avex && legacyManager.avexLegacyEnabled)
		{
			legacyManager.avexLegacy.toggle(player);
		}
		
		else if (action == LegacyAction.AvexHover && legacyManager.avexLegacyEnabled)
		{
			legacyManager.avexLegacy.hover(player);
		}
		
		else if (action == LegacyAction.Telekinesis && legacyManager.legaciesEnabled)
		{
			legacyManager.telekinesis.activated = !legacyManager.telekinesis.activated;
			legacyManager.telekinesis.toggle(player);
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
		else if (i == 6)
			return LegacyAction.GlacenFreeze;
		else if (i == 7)
			return LegacyAction.Avex;
		else if (i == 8)
			return LegacyAction.AvexHover;
		else if (i == 9)
			return LegacyAction.Telekinesis;
		
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
		else if (i == LegacyAction.GlacenFreeze)
			return 6;
		else if (i == LegacyAction.Avex)
			return 7;
		else if (i == LegacyAction.AvexHover)
			return 8;
		else if (i == LegacyAction.Telekinesis)
			return 9;
		
		return -1;
		
	}
	
}
