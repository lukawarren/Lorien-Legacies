package lorien.legacies.commands;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModCommands
{
	// See LorienLegacies.java
	public static void register(FMLPreInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new CommandLegacies());
		MinecraftForge.EVENT_BUS.register(new CommandLegacyLevels());
		MinecraftForge.EVENT_BUS.register(new CommandLegacyXp());
	}
	
}
