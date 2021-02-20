package lorienlegacies.network.mesages;

import java.util.Map;
import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy.LegacyAbility;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageLegacyAbility extends MessageBase
{

	public String ability;

	public MessageLegacyAbility(PacketBuffer buf) { super(buf); }
	public MessageLegacyAbility() {}
	
	@Override
	public void OnDecode(PacketBuffer buf)
	{
		ability = buf.readString(256);
	}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf)
	{
		buf.writeString(ability);
	}

	@Override
	public void OnMessage(MessageBase packet, Supplier<Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> 
		{
			Map<LegacyAbility, String> abilities  = LorienLegacies.proxy.GetLegacyManager().GetLegacyAbilities();

			// Find ability and notify LegacyManager
			for (Map.Entry<LegacyAbility, String> entry : abilities.entrySet())
			{				
				if (entry.getKey().name.equals(((MessageLegacyAbility)packet).ability) && 
					LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(entry.getValue()).GetLegacyLevel(ctx.getSender()) >= entry.getKey().requiredLevel)
				{
					LorienLegacies.proxy.GetLegacyManager().OnAbility(entry.getKey(), entry.getValue(), ctx.getSender());
					
					return;
				}
			}
			
		});
		
		ctx.setPacketHandled(true);
	}

}
