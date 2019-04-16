package lorien.legacies.legacies;

import org.lwjgl.input.Keyboard;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {
	
	public static KeyBinding lumenFireball;
	
	public static KeyBinding toggleSubmari;
	public static KeyBinding toggleNovis;
	public static KeyBinding toggleAccelix;
	public static KeyBinding toggleFortem;
	public static KeyBinding togglePondus;
	public static KeyBinding activateTelekinesis;
	
	public static void init()
	{
		lumenFireball = new KeyBinding("key.lumenFireball", Keyboard.KEY_RSHIFT, "key.categories." + LorienLegacies.MODID);
		ClientRegistry.registerKeyBinding(lumenFireball);
		
		toggleNovis = new KeyBinding("key.toggleNovis", Keyboard.KEY_I, "key.categories." + LorienLegacies.MODID);
		ClientRegistry.registerKeyBinding(toggleNovis);
		
		toggleAccelix = new KeyBinding("key.toggleAccelix", Keyboard.KEY_SEMICOLON, "key.categories." + LorienLegacies.MODID);
		ClientRegistry.registerKeyBinding(toggleAccelix);
		
		toggleFortem = new KeyBinding("key.toggleFortem", Keyboard.KEY_END, "key.categories." + LorienLegacies.MODID);
		ClientRegistry.registerKeyBinding(toggleFortem);
		
		togglePondus = new KeyBinding("key.togglePondus", Keyboard.KEY_HOME, "key.categories." + LorienLegacies.MODID);
		ClientRegistry.registerKeyBinding(togglePondus);
		
		activateTelekinesis = new KeyBinding("key.activateTelekinesis", Keyboard.KEY_DELETE, "key.categories." + LorienLegacies.MODID);
		ClientRegistry.registerKeyBinding(activateTelekinesis);
	}
	
}
