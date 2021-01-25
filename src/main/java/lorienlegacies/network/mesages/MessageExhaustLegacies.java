package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageExhaustLegacies extends MessageBase
{

	public MessageExhaustLegacies(PacketBuffer buf) { super(buf); }
	public MessageExhaustLegacies() {}
	
	@Override
	public void OnMessage(final MessageBase packet, Supplier<NetworkEvent.Context> context)
	{
		// De-toggle all legacies and send player message in chat
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() ->
		{
			LorienLegacies.proxy.GetClientLegacyData().DetoggleAllLegacies();
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("§cYour legacies are exhausted!"), Minecraft.getInstance().player.getUniqueID());
			Minecraft.getInstance().player.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 3.0f, 2.0f);
		});
		
		ctx.setPacketHandled(true);
	}

	@Override
	public void OnDecode(PacketBuffer buf) {}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf) {}

}
