package lorienlegacies.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/*
 * Opening a GUI from a command requires waiting a tick,
 * so this class takes care of a few oddities like that
 */
public class ModGUIs
{
	
	public static List<GuiScreen> guiOpenQueues = new ArrayList<GuiScreen>();
	
	public static void OnTick()
	{
		// Open all pending GUIs
		for (GuiScreen g : guiOpenQueues)
			Minecraft.getMinecraft().displayGuiScreen(g);
		
		guiOpenQueues.clear();
	}
	
	public static void OpenGui(GuiScreen e)
	{
		guiOpenQueues.add(e);
	}
}
