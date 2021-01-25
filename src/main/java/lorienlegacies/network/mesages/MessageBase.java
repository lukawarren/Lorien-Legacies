package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/*
 * Base class for network messages, to be extended
 */
public abstract class MessageBase
{
	
	public MessageBase(PacketBuffer buf)
	{
		OnDecode(buf);
	}
	
	public MessageBase() {}
	
    public abstract void OnDecode(PacketBuffer buf);
    public abstract void OnEncode(MessageBase packet, PacketBuffer buf);
    
    public static void Encode(MessageBase packet, PacketBuffer buf)
    {
    	packet.OnEncode(packet, buf);
    }
	
    public abstract void OnMessage(final MessageBase packet, Supplier<NetworkEvent.Context> context);
    public static void Handle(final MessageBase packet, Supplier<NetworkEvent.Context> context)
    {
    	packet.OnMessage(packet, context);
    }
    
}
