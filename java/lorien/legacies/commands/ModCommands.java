package lorien.legacies.commands;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModCommands
{
	
	public static void register(FMLPreInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new CommandLegacies());
	}
	
}
