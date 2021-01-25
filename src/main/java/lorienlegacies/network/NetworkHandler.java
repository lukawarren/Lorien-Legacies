package lorienlegacies.network;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.network.mesages.MessageBase;
import lorienlegacies.network.mesages.MessageExhaustLegacies;
import lorienlegacies.network.mesages.MessageLegacyData;
import lorienlegacies.network.mesages.MessageStaminaSync;
import lorienlegacies.network.mesages.MessageToggleLegacy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * Handles network packets, delegates messages, etc
 */

public class NetworkHandler
{
	private static final String PROTOCOL_VERSION = "1";
	private static SimpleChannel INSTANCE ;
	
	public static void init()
	{
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(LorienLegacies.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
		
		// Register messages - ID (must be unique), class, then a few functions
		INSTANCE.registerMessage(0, MessageLegacyData.class, 		MessageLegacyData::Encode, 		MessageLegacyData::new, 		MessageLegacyData::Handle);
		INSTANCE.registerMessage(1, MessageToggleLegacy.class,		MessageToggleLegacy::Encode, 	MessageToggleLegacy::new, 		MessageToggleLegacy::Handle);
		INSTANCE.registerMessage(2, MessageExhaustLegacies.class, 	MessageExhaustLegacies::Encode, MessageExhaustLegacies::new, 	MessageExhaustLegacies::Handle);
		INSTANCE.registerMessage(3, MessageStaminaSync.class, 		MessageStaminaSync::Encode, 	MessageStaminaSync::new, 		MessageStaminaSync::Handle);
	}

	public static void sendToServer(MessageBase message)
	{
		INSTANCE.sendToServer(message);
	}
	
	public static void sendToPlayer(MessageBase message, ServerPlayerEntity player)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
	
}
