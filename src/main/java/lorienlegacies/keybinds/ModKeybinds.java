package lorienlegacies.keybinds;

import org.lwjgl.glfw.GLFW;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.gui.GuiLegacyToggle;
import lorienlegacies.gui.ModGUIs;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
public class ModKeybinds
{
	
	public static KeyBinding keyToggleLegacies;
	
	public static void RegisterKeybinds()
	{
		keyToggleLegacies = new KeyBinding("key.toggleLegacies", GLFW.GLFW_KEY_LEFT_ALT, "key.lorienlegacies.category");
		ClientRegistry.registerKeyBinding(keyToggleLegacies);
	}

	@SubscribeEvent
	public static void onEvent(KeyInputEvent event)
	{
		if (keyToggleLegacies.isPressed())
		{
			// Toggle legacies GUI
			ModGUIs.OpenGui(new GuiLegacyToggle());
		}
	}
	
}
