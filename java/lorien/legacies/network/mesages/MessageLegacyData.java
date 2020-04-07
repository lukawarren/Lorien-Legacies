package lorien.legacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
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
	public boolean glacenLegacyEnabled;
	
	public int lumenLegacyLevel;
	public int noxenLegacyLevel;
	public int submariLegacyLevel;
	public int novisLegacyLevel;
	public int accelixLegacyLevel;
	public int fortemLegacyLevel;
	public int pondusLegacyLevel;
	public int regenerasLegacyLevel;
	public int avexLegacyLevel;
	public int glacenLegacyLevel;
	
	public int lumenLegacyXP;
	public int noxenLegacyXP;
	public int submariLegacyXP;
	public int novisLegacyXP;
	public int accelixLegacyXP;
	public int fortemLegacyXP;
	public int pondusLegacyXP;
	public int regenerasLegacyXP;
	public int avexLegacyXP;
	public int glacenLegacyXP;
	
	public boolean shouldSendMessage;
	
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
		buf.writeBoolean(glacenLegacyEnabled);
		
		buf.writeInt(lumenLegacyLevel);
		buf.writeInt(noxenLegacyLevel);
		buf.writeInt(submariLegacyLevel);
		buf.writeInt(novisLegacyLevel);
		buf.writeInt(accelixLegacyLevel);
		buf.writeInt(fortemLegacyLevel);
		buf.writeInt(pondusLegacyLevel);
		buf.writeInt(regenerasLegacyLevel);
		buf.writeInt(avexLegacyLevel);
		buf.writeInt(glacenLegacyLevel);

		buf.writeInt(lumenLegacyXP);
		buf.writeInt(noxenLegacyXP);
		buf.writeInt(submariLegacyXP);
		buf.writeInt(novisLegacyXP);
		buf.writeInt(accelixLegacyXP);
		buf.writeInt(fortemLegacyXP);
		buf.writeInt(pondusLegacyXP);
		buf.writeInt(regenerasLegacyXP);
		buf.writeInt(avexLegacyXP);
		buf.writeInt(glacenLegacyXP);
		
		buf.writeBoolean(shouldSendMessage);
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
		glacenLegacyEnabled = buf.readBoolean();
		
		lumenLegacyLevel = buf.readInt();
		noxenLegacyLevel = buf.readInt();
		submariLegacyLevel = buf.readInt();
		novisLegacyLevel = buf.readInt();
		accelixLegacyLevel = buf.readInt();
		fortemLegacyLevel = buf.readInt();
		pondusLegacyLevel = buf.readInt();
		regenerasLegacyLevel = buf.readInt();
		avexLegacyLevel = buf.readInt();
		glacenLegacyLevel = buf.readInt();
		
		lumenLegacyXP = buf.readInt();
		noxenLegacyXP = buf.readInt();
		submariLegacyXP = buf.readInt();
		novisLegacyXP = buf.readInt();
		accelixLegacyXP = buf.readInt();
		fortemLegacyXP = buf.readInt();
		pondusLegacyXP = buf.readInt();
		regenerasLegacyXP = buf.readInt();
		avexLegacyXP = buf.readInt();
		glacenLegacyXP = buf.readInt();
		
		shouldSendMessage = buf.readBoolean();
	}
	
	@Override
	public void handleClientSide(MessageLegacyData message, EntityPlayer player)
	{
		
		Minecraft.getMinecraft().addScheduledTask(() -> {
			
			LorienLegacies.clientLegacyManager = new LegacyManager(player);
			
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
			LorienLegacies.clientLegacyManager.glacenLegacyEnabled = message.glacenLegacyEnabled;
			
			LorienLegacies.clientLegacyManager.lumenLegacy.currentLegacyLevel = message.lumenLegacyLevel;
			LorienLegacies.clientLegacyManager.noxenLegacy.currentLegacyLevel = message.noxenLegacyLevel;
			LorienLegacies.clientLegacyManager.submariLegacy.currentLegacyLevel = message.submariLegacyLevel;
			LorienLegacies.clientLegacyManager.novisLegacy.currentLegacyLevel = message.novisLegacyLevel;
			LorienLegacies.clientLegacyManager.accelixLegacy.currentLegacyLevel = message.accelixLegacyLevel;
			LorienLegacies.clientLegacyManager.fortemLegacy.currentLegacyLevel = message.fortemLegacyLevel;
			LorienLegacies.clientLegacyManager.pondusLegacy.currentLegacyLevel = message.pondusLegacyLevel;
			LorienLegacies.clientLegacyManager.regenerasLegacy.currentLegacyLevel = message.regenerasLegacyLevel;
			LorienLegacies.clientLegacyManager.avexLegacy.currentLegacyLevel = message.avexLegacyLevel;
			LorienLegacies.clientLegacyManager.glacenLegacy.currentLegacyLevel = message.glacenLegacyLevel;
			
			LorienLegacies.clientLegacyManager.lumenLegacy.xp = message.lumenLegacyXP;
			LorienLegacies.clientLegacyManager.noxenLegacy.xp = message.noxenLegacyXP;
			LorienLegacies.clientLegacyManager.submariLegacy.xp = message.submariLegacyXP;
			LorienLegacies.clientLegacyManager.novisLegacy.xp = message.novisLegacyXP;
			LorienLegacies.clientLegacyManager.accelixLegacy.xp = message.accelixLegacyXP;
			LorienLegacies.clientLegacyManager.fortemLegacy.xp = message.fortemLegacyXP;
			LorienLegacies.clientLegacyManager.pondusLegacy.xp = message.pondusLegacyXP;
			LorienLegacies.clientLegacyManager.regenerasLegacy.xp = message.regenerasLegacyXP;
			LorienLegacies.clientLegacyManager.avexLegacy.xp = message.avexLegacyXP;
			LorienLegacies.clientLegacyManager.glacenLegacy.xp = message.glacenLegacyXP;
			
			if (LorienLegacies.clientLegacyManager.legaciesEnabled && message.shouldSendMessage)
				LegacyLoader.displayBlessedMessgaes(player);			
		});
		
		
	}

	@Override
	public void handleServerSide(MessageLegacyData message, EntityPlayer player)
	{
		
	}
	
}