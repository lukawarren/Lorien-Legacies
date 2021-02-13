package lorienlegacies.gui;

import java.util.ArrayList;
import java.util.List;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/*
 * Opening a GUI from a command requires waiting a tick,
 * so this class takes care of a few oddities like that.
 * It also holds the stamina GUI and the Pondus density
 * bar, as these are overlays and are handled a bit
 * differently.
 */

@EventBusSubscriber(Dist.CLIENT)
public class ModGUIs
{
	
	public static List<Screen> guiOpenQueues = new ArrayList<Screen>();
	public static GuiStamina guiStamina = new GuiStamina();
	public static GuiPondusDensity guiPondus = null;
	
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
	
	@SubscribeEvent
    public static void OnRenderOverlays(RenderGameOverlayEvent event)
    {
    	guiStamina.Render(LorienLegacies.proxy.GetClientLegacyData().stamina, event);
    	if (guiPondus != null) guiPondus.Render(event);
    }
	
	public static void ClosePondusOverlay()
	{
		guiPondus = null;
	}
}
