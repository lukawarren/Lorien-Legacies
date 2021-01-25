package lorienlegacies.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

/*
 * Opening a GUI from a command requires waiting a tick,
 * so this class takes care of a few oddities like that.
 * It also holds the stamina GUI.
 */

public class ModGUIs
{
	
	public static List<Screen> guiOpenQueues = new ArrayList<Screen>();
	public static GuiStamina guiStamina = new GuiStamina();
	
	public static void OnTick()
	{
		// Open all pending GUIs
		for (Screen g : guiOpenQueues)
			Minecraft.getInstance().displayGuiScreen(g);
		
		guiOpenQueues.clear();
	}
	
	public static void OpenGui(Screen e)
	{
		guiOpenQueues.add(e);
	}
}
