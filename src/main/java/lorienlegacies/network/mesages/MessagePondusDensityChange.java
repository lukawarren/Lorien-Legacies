package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import lorienlegacies.gui.GuiPondusDensity;
import lorienlegacies.gui.ModGUIs;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePondusDensityChange extends MessageBase
{
	public boolean directionUp;

	public MessagePondusDensityChange(PacketBuffer buf) { super(buf); }
	public MessagePondusDensityChange() {}
	
	@Override
	public void OnDecode(PacketBuffer buf)
	{
		directionUp = buf.readBoolean();
	}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf)
	{
		buf.writeBoolean(directionUp);
	}

	@Override
	public void OnMessage(MessageBase packet, Supplier<Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> 
		{
			ModGUIs.guiPondus = new GuiPondusDensity(((MessagePondusDensityChange)packet).directionUp);
		});
		
		ctx.setPacketHandled(true);
	}

}
