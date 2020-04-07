package lorien.legacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorien.legacies.gui.LegacyGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageLegacyLevelsScreen extends MessageBase<MessageLegacyLevelsScreen>
{

	public static boolean openGUINextTick = false;
	public static boolean initForgeEventBus = false;
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessageLegacyLevelsScreen message, EntityPlayer player) 
	{
		if (!initForgeEventBus)
		{
			initForgeEventBus = true;
			MinecraftForge.EVENT_BUS.register(this.getClass());
		}
		
		openGUINextTick = true;
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		if (openGUINextTick)
		{
			Minecraft.getMinecraft().displayGuiScreen(new LegacyGui());
			openGUINextTick = false;
		}
	}
	
	@Override
	public void handleServerSide(MessageLegacyLevelsScreen message, EntityPlayer player) 
	{
		
	}

}
