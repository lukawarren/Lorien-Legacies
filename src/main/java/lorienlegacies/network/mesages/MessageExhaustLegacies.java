package lorienlegacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MessageExhaustLegacies extends MessageBase<MessageExhaustLegacies>
{

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleClientSide(MessageExhaustLegacies message, EntityPlayer player)
	{
		// De-toggle all legacies and send player message in chat
		Minecraft.getMinecraft().addScheduledTask(() ->
		{
			LorienLegacies.proxy.GetClientLegacyData().DetoggleAllLegacies();
			player.sendMessage(new TextComponentString("Your legacies are exhausted!").setStyle(new Style().setColor(TextFormatting.RED)));
			Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_FIREWORK_BLAST_FAR, 3.0f, 2.0f);
		});
	}

	@Override
	public void handleServerSide(MessageExhaustLegacies message, EntityPlayer player) {}

}
