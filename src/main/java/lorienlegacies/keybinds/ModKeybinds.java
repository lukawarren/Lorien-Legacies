package lorienlegacies.keybinds;

import org.lwjgl.glfw.GLFW;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.gui.GuiAbility;
import lorienlegacies.gui.GuiLegacyToggle;
import lorienlegacies.gui.ModGUIs;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageToggleLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
public class ModKeybinds
{
	
	public static KeyBinding keyToggleLegacies;
	public static KeyBinding keyLegacyAbilities;
	
	public static KeyBinding keyLastUsedLegacy;
	public static boolean lastLegacyKeyState = false; // For key up
	
	public static void RegisterKeybinds()
	{
		keyToggleLegacies = new KeyBinding("key.toggleLegacies", GLFW.GLFW_KEY_LEFT_ALT, "key.lorienlegacies.category");
		ClientRegistry.registerKeyBinding(keyToggleLegacies);
		
		keyLegacyAbilities = new KeyBinding("key.legacyAbilities", GLFW.GLFW_KEY_Z, "key.lorienlegacies.category");
		ClientRegistry.registerKeyBinding(keyLegacyAbilities);
		
		keyLastUsedLegacy = new KeyBinding("key.lastUsedLegacy", GLFW.GLFW_KEY_GRAVE_ACCENT, "key.lorienlegacies.category");
		ClientRegistry.registerKeyBinding(keyLastUsedLegacy);
	}

	@SubscribeEvent
	public static void ClientTickEvent(ClientTickEvent event)
	{	
		if (Minecraft.getInstance() == null || Minecraft.getInstance().player == null || Minecraft.getInstance().player.world == null)
			return;
		
		if (keyToggleLegacies.isPressed())
		{
			// Toggle legacies GUI
			ModGUIs.OpenGui(new GuiLegacyToggle());
		}
		
		if (keyLegacyAbilities.isPressed())
		{
			// Abilities GUI
			ModGUIs.OpenGui(new GuiAbility());
		}
		
		// Toggling (or untoggling) last used legacy
		String lastUsedLegacy = LorienLegacies.proxy.GetClientLegacyData().lastUsedLegacy;
		if (lastUsedLegacy != null)
		{
			if (lastLegacyKeyState != keyLastUsedLegacy.isKeyDown())
			{
				// Send to server
				MessageToggleLegacy message = new MessageToggleLegacy();
				message.legacy = lastUsedLegacy;
				NetworkHandler.sendToServer(message);
				
				// Toggle on client too
				LorienLegacies.proxy.GetClientLegacyData().ToggleLegacy(lastUsedLegacy);
			}
		}
		else if (lastLegacyKeyState != keyLastUsedLegacy.isKeyDown())
		{
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("§cYou do not have a previously selected legacy"), Minecraft.getInstance().player.getUniqueID());
		}
		
		lastLegacyKeyState = keyLastUsedLegacy.isKeyDown();
	}
}
