package lorienlegacies.network;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * Handles network packets, delegates messages, etc
 */

public class NetworkHandler
{
	
	private static SimpleNetworkWrapper INSTANCE;
	
	public static void init()
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LorienLegacies.MODID);
		
		// TODO: Register messages (packets)	
	}

	public static void sendToServer(IMessage message)
	{
		INSTANCE.sendToServer(message);
	}
	
	public static void sendToPlayer(IMessage message, EntityPlayerMP player)
	{
		INSTANCE.sendTo(message, player);
	}
	
}
