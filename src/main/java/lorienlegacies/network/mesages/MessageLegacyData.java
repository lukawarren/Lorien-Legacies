package lorienlegacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import scala.actors.threadpool.Arrays;

public class MessageLegacyData extends MessageBase<MessageLegacyData>
{
	
	public int[] legacies; // See PlayerLegacyData
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		int numLegacies = buf.readInt();
		
		legacies = new int[numLegacies];
		for (int i = 0; i < numLegacies; ++i)
			legacies[i] = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(legacies.length);
		
		for (int i = 0; i < legacies.length; ++i)
			buf.writeInt(legacies[i]);
	}

	@Override
	public void handleClientSide(MessageLegacyData message, EntityPlayer player)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> {
			LorienLegacies.proxy.GetClientLegacyData().FromIntArray(message.legacies); // Set data
			LorienLegacies.logger.info("Recieved legacy data {}", Arrays.toString(LorienLegacies.proxy.GetClientLegacyData().ToIntArray())); // Log data
		});
	}

	@Override
	public void handleServerSide(MessageLegacyData message, EntityPlayer player)
	{
		
	}

}
