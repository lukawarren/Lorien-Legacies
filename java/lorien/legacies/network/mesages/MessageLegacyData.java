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
		// There is a bug whereby the server will send this message to the client before the client has loaded in themselves,
		// but only in survival singleplayer for some reason. By waiting a second, this problem will *probably* go away.
		// I know it's bad practice but the error is quite hard to reproduce and this was the most painless way to fix it.
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		
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
			
			if (LorienLegacies.instance.clientLegacyManager == null) LorienLegacies.instance.clientLegacyManager = new LegacyManager(player);
			
			LorienLegacies.instance.clientLegacyManager.legaciesEnabled = message.legaciesEnabled;
			LorienLegacies.instance.clientLegacyManager.lumenLegacyEnabled = message.lumenLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.noxenLegacyEnabled = message.noxenLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.submariLegacyEnabled = message.submariLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.novisLegacyEnabled = message.novisLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.accelixLegacyEnabled = message.accelixLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.fortemLegacyEnabled = message.fortemLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.pondusLegacyEnabled = message.pondusLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.regenerasLegacyEnabled = message.regenerasLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.avexLegacyEnabled = message.avexLegacyEnabled;
			LorienLegacies.instance.clientLegacyManager.glacenLegacyEnabled = message.glacenLegacyEnabled;
			
			LorienLegacies.instance.clientLegacyManager.lumenLegacy.currentLegacyLevel = message.lumenLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.noxenLegacy.currentLegacyLevel = message.noxenLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.submariLegacy.currentLegacyLevel = message.submariLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.novisLegacy.currentLegacyLevel = message.novisLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.accelixLegacy.currentLegacyLevel = message.accelixLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.fortemLegacy.currentLegacyLevel = message.fortemLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.pondusLegacy.currentLegacyLevel = message.pondusLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.regenerasLegacy.currentLegacyLevel = message.regenerasLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.avexLegacy.currentLegacyLevel = message.avexLegacyLevel;
			LorienLegacies.instance.clientLegacyManager.glacenLegacy.currentLegacyLevel = message.glacenLegacyLevel;
			
			LorienLegacies.instance.clientLegacyManager.lumenLegacy.xp = message.lumenLegacyXP;
			LorienLegacies.instance.clientLegacyManager.noxenLegacy.xp = message.noxenLegacyXP;
			LorienLegacies.instance.clientLegacyManager.submariLegacy.xp = message.submariLegacyXP;
			LorienLegacies.instance.clientLegacyManager.novisLegacy.xp = message.novisLegacyXP;
			LorienLegacies.instance.clientLegacyManager.accelixLegacy.xp = message.accelixLegacyXP;
			LorienLegacies.instance.clientLegacyManager.fortemLegacy.xp = message.fortemLegacyXP;
			LorienLegacies.instance.clientLegacyManager.pondusLegacy.xp = message.pondusLegacyXP;
			LorienLegacies.instance.clientLegacyManager.regenerasLegacy.xp = message.regenerasLegacyXP;
			LorienLegacies.instance.clientLegacyManager.avexLegacy.xp = message.avexLegacyXP;
			LorienLegacies.instance.clientLegacyManager.glacenLegacy.xp = message.glacenLegacyXP;
			
			if (LorienLegacies.instance.clientLegacyManager.legaciesEnabled && message.shouldSendMessage)
				LegacyLoader.displayBlessedMessgaes(player);			
		});
		
		
	}

	@Override
	public void handleServerSide(MessageLegacyData message, EntityPlayer player)
	{
		
	}
	
}