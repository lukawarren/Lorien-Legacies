package lorien.legacies.network.mesages.legacyActions;

import io.netty.buffer.ByteBuf;
import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.network.mesages.MessageBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class MessageLegacyAction extends MessageBase<MessageLegacyAction>
{

	private int legacyActionID;
	
	public MessageLegacyAction()
	{
		
	}
	
	public MessageLegacyAction(int legacyActionID)
	{
		this.legacyActionID = legacyActionID;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		// Must read and write in same order
		buf.writeInt(legacyActionID);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		legacyActionID = buf.readInt();
	}

	@Override
	public void handleClientSide(MessageLegacyAction message, EntityPlayer player)
	{
		
	}

	@Override
	public void handleServerSide(MessageLegacyAction message, EntityPlayer player)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> {
			
			LegacyManager legacyManager = null;
			if (LorienLegacies.instance.legacyManagers.containsKey(player.getUniqueID())) legacyManager = LorienLegacies.instance.legacyManagers.get(player.getUniqueID());
			if (legacyManager == null) return;
			
			LegacyActionConverter.performLegacyAction(legacyManager, LegacyActionConverter.legacyActionFromInt(message.legacyActionID));
		});
	}
	
}
