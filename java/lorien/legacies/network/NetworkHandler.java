package lorien.legacies.network;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.network.mesages.MessageLegacyData;
import lorien.legacies.network.mesages.MessageLegacyLevelsScreen;
import lorien.legacies.network.mesages.MesssageLegacyConfig;
import lorien.legacies.network.mesages.legacyActions.MessageLegacyAction;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler
{
	
	private static SimpleNetworkWrapper INSTANCE;
	
	public static void init()
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LorienLegacies.MODID);
		
		// Register messages (packets)	
		INSTANCE.registerMessage(MessageLegacyAction.class, MessageLegacyAction.class, 0, Side.SERVER); // Packet class, packet class, ID, which side will receive the packet
		INSTANCE.registerMessage(MessageLegacyData.class, MessageLegacyData.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(MessageLegacyLevelsScreen.class, MessageLegacyLevelsScreen.class, 2, Side.CLIENT);
		INSTANCE.registerMessage(MesssageLegacyConfig.class, MesssageLegacyConfig.class, 3, Side.CLIENT);
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
