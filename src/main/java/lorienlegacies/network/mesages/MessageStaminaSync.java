package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageStaminaSync extends MessageBase
{
	
	public float stamina;
	public float maxStamina;
	
	public MessageStaminaSync(PacketBuffer buf) { super(buf); }
	public MessageStaminaSync() {}
	
	@Override
	public void OnDecode(PacketBuffer buf)
	{
		stamina = buf.readFloat();
		maxStamina = buf.readFloat();
	}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf)
	{
		buf.writeFloat(stamina);
		buf.writeFloat(maxStamina);
	}

	@Override
	public void OnMessage(MessageBase packet, Supplier<Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() ->
		{
			LorienLegacies.proxy.GetClientLegacyData().stamina = ((MessageStaminaSync)packet).stamina;
			LorienLegacies.proxy.GetClientLegacyData().maxClientStamina = ((MessageStaminaSync)packet).maxStamina;
		});
		
		ctx.setPacketHandled(true);
	}
}
