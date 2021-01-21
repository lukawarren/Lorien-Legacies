package lorienlegacies.network;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.network.mesages.MessageLegacyData;
import lorienlegacies.network.mesages.MessageToggleLegacy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handles network packets, delegates messages, etc
 */

public class NetworkHandler
{
	
	private static SimpleNetworkWrapper INSTANCE;
	
	public static void init()
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LorienLegacies.MODID);
		
		// Register messages - Packet class, packet class, ID (must be unique), and the side which recieves the message
		INSTANCE.registerMessage(MessageLegacyData.class, MessageLegacyData.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageToggleLegacy.class, MessageToggleLegacy.class, 1, Side.SERVER);
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
