package lorien.legacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorien.legacies.gui.LegacyGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageLegacyLevelsScreen extends MessageBase<MessageLegacyLevelsScreen>
{

	@Override
	public void fromBytes(ByteBuf buf)
	{
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		
	}

	@SideOnly(Side.CLIENT) // I know it's bad crashes, but I don't want the mod to crash. Do you?
	@Override
	public void handleClientSide(MessageLegacyLevelsScreen message, EntityPlayer player) 
	{
		Minecraft.getMinecraft().displayGuiScreen(new LegacyGui());
	}

	@Override
	public void handleServerSide(MessageLegacyLevelsScreen message, EntityPlayer player) 
	{
		
	}

}
