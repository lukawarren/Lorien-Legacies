package lorienlegacies.gui;

import java.util.ArrayList;
import java.util.List;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/*
 * Opening a GUI from a command requires waiting a tick,
 * so this class takes care of a few oddities like that.
 * It also holds the stamina GUI and the Pondus density
 * bar, as these are overlays and are handled a bit
 * differently.
 */

public class ModGUIs
{
	
	public List<Screen> guiOpenQueues = new ArrayList<Screen>();
	public GuiStamina guiStamina = new GuiStamina();
	public GuiPondusDensity guiPondus = null;
	
	public ModGUIs()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void OnTick()
	{
		// Open all pending GUIs
		for (Screen g : guiOpenQueues)
			Minecraft.getInstance().displayGuiScreen(g);
		
		guiOpenQueues.clear();
	}
	
	public void OpenGUI(Screen screen)
	{
		guiOpenQueues.add(screen);
	}
	
	@SubscribeEvent()
    public void OnRenderOverlays(RenderGameOverlayEvent event)
    {
    	guiStamina.Render(LorienLegacies.proxy.GetClientLegacyData().stamina, event);
    	if (guiPondus != null) guiPondus.Render(event);
    }
	
	public void ClosePondusOverlay()
	{
		guiPondus = null;
	}
}
