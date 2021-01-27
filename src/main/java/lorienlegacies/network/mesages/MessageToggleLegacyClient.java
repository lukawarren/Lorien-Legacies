package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageToggleLegacyClient extends MessageBase
{
	public String legacy;

	public MessageToggleLegacyClient(PacketBuffer buf) { super(buf); }
	public MessageToggleLegacyClient() {}
	
	@Override
	public void OnDecode(PacketBuffer buf)
	{
		legacy = buf.readString();
	}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf)
	{
		buf.writeString(legacy);
	}
	
	@Override
	public void OnMessage(final MessageBase packet, Supplier<NetworkEvent.Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() ->
		{
			LorienLegacies.proxy.GetClientLegacyData().ToggleLegacy(((MessageToggleLegacyClient)packet).legacy);
		});
		
		ctx.setPacketHandled(true);
	}

}
