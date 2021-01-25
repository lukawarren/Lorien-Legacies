package lorienlegacies.network.mesages;

import java.util.Arrays;
import java.util.function.Supplier;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageLegacyData extends MessageBase
{

	public int[] legacies; // See PlayerLegacyData

	public MessageLegacyData(PacketBuffer buf) { super(buf); }
	public MessageLegacyData() {}
	
	@Override
	public void OnDecode(PacketBuffer buf)
	{
		int numLegacies = buf.readInt();
		
		legacies = new int[numLegacies];
		for (int i = 0; i < numLegacies; ++i)
			legacies[i] = buf.readInt();
	}

	@Override
	public void OnEncode(MessageBase packet, PacketBuffer buf)
	{
		buf.writeInt(legacies.length);
		
		for (int i = 0; i < legacies.length; ++i)
			buf.writeInt(legacies[i]);
	}

	@Override
	public void OnMessage(MessageBase packet, Supplier<Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> 
		{
			LorienLegacies.proxy.GetClientLegacyData().FromIntArray(((MessageLegacyData)packet).legacies); // Set data
			
			// Detoggle all legacies in case we just got re-given them
			LorienLegacies.proxy.GetClientLegacyData().DetoggleAllLegacies();
			
			LorienLegacies.logger.info("Recieved legacy data {}", Arrays.toString(LorienLegacies.proxy.GetClientLegacyData().ToIntArray())); // Log data
		});
		
		ctx.setPacketHandled(true);
	}

}
