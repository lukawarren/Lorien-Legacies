package lorienlegacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageToggleLegacy extends MessageBase<MessageToggleLegacy>
{
	public String legacy;

	@Override
	public void fromBytes(ByteBuf buf)
	{
		legacy = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, legacy);
	}

	@Override
	public void handleClientSide(MessageToggleLegacy message, EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(MessageToggleLegacy message, EntityPlayer player)
	{
		// If player has legacy
		Integer legacyXP = WorldLegacySaveData.get(player.world).GetPlayerData().get(player.getUniqueID()).legacies.get(message.legacy);
		if (legacyXP != null && legacyXP != 0)
			WorldLegacySaveData.get(player.world).GetPlayerData().get(player.getUniqueID()).ToggleLegacy(message.legacy);
	}

}
