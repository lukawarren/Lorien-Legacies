package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageLegacyLevel extends MessageBase
{

	public String legacyName;
	public int legacyLevel;

	public MessageLegacyLevel(PacketBuffer buf) { super(buf); }
	public MessageLegacyLevel() {}
	
	@Override
	public void OnDecode(PacketBuffer buf)
	{
		legacyName = buf.readString();
		legacyLevel = buf.readInt();
	}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf)
	{
		buf.writeString(legacyName);
		buf.writeInt(legacyLevel);
	}

	@Override
	public void OnMessage(MessageBase packet, Supplier<Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> 
		{
			LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.put(legacyName, legacyLevel);
		});
		
		ctx.setPacketHandled(true);
	}

}
