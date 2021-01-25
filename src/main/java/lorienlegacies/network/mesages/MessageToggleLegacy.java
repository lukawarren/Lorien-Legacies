package lorienlegacies.network.mesages;

import java.util.function.Supplier;

import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageToggleLegacy extends MessageBase
{
	public String legacy;

	public MessageToggleLegacy(PacketBuffer buf) { super(buf); }
	public MessageToggleLegacy() {}
	
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
			PlayerEntity player = ctx.getSender();
			
			// If player has legacy
			Integer legacyXP = WorldLegacySaveData.get(player.world).GetPlayerData().get(player.getUniqueID()).legacies.get(((MessageToggleLegacy)packet).legacy);
			if (legacyXP != null && legacyXP != 0)
				WorldLegacySaveData.get(player.world).GetPlayerData().get(player.getUniqueID()).ToggleLegacy(((MessageToggleLegacy)packet).legacy);
		});
		
		ctx.setPacketHandled(true);
	}

}
