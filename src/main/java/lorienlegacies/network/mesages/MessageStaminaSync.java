package lorienlegacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorienlegacies.config.ConfigLorienLegacies;
import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MessageStaminaSync extends MessageBase<MessageStaminaSync>
{
	
	public int stamina;
	public int maxStamina;
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		stamina = buf.readInt();
		maxStamina = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(stamina);
		buf.writeInt(maxStamina);
	}

	@Override
	public void handleClientSide(MessageStaminaSync message, EntityPlayer player)
	{
		Minecraft.getMinecraft().addScheduledTask(() ->
		{
			LorienLegacies.proxy.GetClientLegacyData().stamina = message.stamina;
			ConfigLorienLegacies.legacyStamina.maxStamina = message.maxStamina;
		});
	}

	@Override
	public void handleServerSide(MessageStaminaSync message, EntityPlayer player)
	{
		
	}

}
