package lorien.legacies.legacies;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeyInputHandler {
	
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{	
		if (LorienLegacies.instance.clientLegacyManager != null)
			LorienLegacies.instance.clientLegacyManager.onKeyClient();
	}
	
	/*
	// The "proper" way but whatever cause I'm lazy
	@SubscribeEvent (priority = EventPriority.LOWEST)
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception
	{	
		// Find legacy manager for player
		for (LegacyManager l : LorienLegacies.legacyManagers)
		{
			if (l.player.getUniqueID() == Minecraft.getMinecraft().player.getUniqueID())
				l.onKeyPress();
		}
	}
	*/
}
