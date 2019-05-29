package lorien.legacies.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import lorien.legacies.entities.Chimaera.Chimaera;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMorphChimaera implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	/*
	UUID uuid;
	String newModel;

	public MessageMorphChimaera() {

	}

	public MessageMorphChimaera(String newModel, Chimaera chimaera) {
		this.uuid = chimaera.getUniqueID();
		this.newModel = newModel;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		this.uuid = buffer.readUniqueId();
		this.newModel = buffer.readString(buffer.readableBytes());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeUniqueId(uuid);
		buffer.writeString(newModel);
	}

	public static class Handle implements IMessageHandler<MessageMorphChimaera, IMessage> {

		@Override
		public IMessage onMessage(MessageMorphChimaera message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() ->{
				Chimaera entity = (Chimaera) Minecraft.getMinecraft().world.loadedEntityList.stream().filter(e -> e.getUniqueID().equals(message.uuid)).findAny().get();
				if(entity != null) {
					entity.handleMorph(message.newModel);
				}
			});
			return null;
		}

	}
	*/
}