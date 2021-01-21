package lorienlegacies.keybinds;

import org.lwjgl.input.Keyboard;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.gui.GuiLegacyToggle;
import lorienlegacies.gui.ModGUIs;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
public class ModKeybinds
{
	
	public static KeyBinding keyToggleLegacies;
	
	public static void RegisterKeybinds()
	{
		keyToggleLegacies = new KeyBinding("key.toggleLegacies", Keyboard.KEY_LMENU, "key.lorienlegacies.category");
		ClientRegistry.registerKeyBinding(keyToggleLegacies);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public static void onEvent(KeyInputEvent event)
	{
		if (keyToggleLegacies.isPressed())
		{
			// Toggle legacies GUI
			ModGUIs.OpenGui(new GuiLegacyToggle(true));
		}
	}
	
}
