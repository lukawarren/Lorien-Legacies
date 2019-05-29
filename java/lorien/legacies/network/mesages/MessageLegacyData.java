package lorien.legacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.AvexLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.implementations.LumenLegacy;
import lorien.legacies.legacies.implementations.NovisLegacy;
import lorien.legacies.legacies.implementations.NoxenLegacy;
import lorien.legacies.legacies.implementations.PondusLegacy;
import lorien.legacies.legacies.implementations.RegenerasLegacy;
import lorien.legacies.legacies.implementations.SubmariLegacy;
import lorien.legacies.legacies.implementations.Telekinesis;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MessageLegacyData extends MessageBase<MessageLegacyData>
{

public boolean legaciesEnabled;
	
	public boolean lumenLegacyEnabled;
	public boolean noxenLegacyEnabled;
	public boolean submariLegacyEnabled;
	public boolean novisLegacyEnabled;
	public boolean accelixLegacyEnabled;
	public boolean fortemLegacyEnabled;
	public boolean pondusLegacyEnabled;
	public boolean regenerasLegacyEnabled;
	public boolean avexLegacyEnabled;
	
	public MessageLegacyData()
	{
		
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		// Must read and write in same order
		buf.writeBoolean(legaciesEnabled);
		
		buf.writeBoolean(lumenLegacyEnabled);
		buf.writeBoolean(noxenLegacyEnabled);
		buf.writeBoolean(submariLegacyEnabled);
		buf.writeBoolean(novisLegacyEnabled);
		buf.writeBoolean(accelixLegacyEnabled);
		buf.writeBoolean(fortemLegacyEnabled);
		buf.writeBoolean(pondusLegacyEnabled);
		buf.writeBoolean(regenerasLegacyEnabled);
		buf.writeBoolean(avexLegacyEnabled);
		
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		// Must read and write in same order
		legaciesEnabled = buf.readBoolean();
		
		lumenLegacyEnabled = buf.readBoolean();
		noxenLegacyEnabled = buf.readBoolean();
		submariLegacyEnabled = buf.readBoolean();
		novisLegacyEnabled = buf.readBoolean();
		accelixLegacyEnabled = buf.readBoolean();
		fortemLegacyEnabled = buf.readBoolean();
		pondusLegacyEnabled = buf.readBoolean();
		regenerasLegacyEnabled = buf.readBoolean();
		avexLegacyEnabled = buf.readBoolean();
	}

	@Override
	public void handleClientSide(MessageLegacyData message, EntityPlayer player)
	{
		
		Minecraft.getMinecraft().addScheduledTask(() -> {
			
			LorienLegacies.clientLegacyManager = new LegacyManager(Minecraft.getMinecraft().player);
			
			LorienLegacies.clientLegacyManager.legaciesEnabled = message.legaciesEnabled;
			LorienLegacies.clientLegacyManager.lumenLegacyEnabled = message.lumenLegacyEnabled;
			LorienLegacies.clientLegacyManager.noxenLegacyEnabled = message.noxenLegacyEnabled;
			LorienLegacies.clientLegacyManager.submariLegacyEnabled = message.submariLegacyEnabled;
			LorienLegacies.clientLegacyManager.novisLegacyEnabled = message.novisLegacyEnabled;
			LorienLegacies.clientLegacyManager.accelixLegacyEnabled = message.accelixLegacyEnabled;
			LorienLegacies.clientLegacyManager.fortemLegacyEnabled = message.fortemLegacyEnabled;
			LorienLegacies.clientLegacyManager.pondusLegacyEnabled = message.pondusLegacyEnabled;
			LorienLegacies.clientLegacyManager.regenerasLegacyEnabled = message.regenerasLegacyEnabled;
			LorienLegacies.clientLegacyManager.avexLegacyEnabled = message.avexLegacyEnabled;
			
			if (LorienLegacies.clientLegacyManager.legaciesEnabled)
				LegacyLoader.displayBlessedMessgaes(Minecraft.getMinecraft().player);			
		});
		
		
	}

	@Override
	public void handleServerSide(MessageLegacyData message, EntityPlayer player)
	{
		
	}
	
}