package lorien.legacies.network.mesages;

import io.netty.buffer.ByteBuf;
import lorien.legacies.config.LorienLegaciesConfig.LegacyUse;
import lorien.legacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MesssageLegacyConfig extends MessageBase<MesssageLegacyConfig>
{

	private static boolean clientHasRecievedMessageOnce = false;
	
	public boolean allowAccelix;
	public boolean allowAvex;
	public boolean allowFortem;
	public boolean allowGlacen;
	public boolean allowLumen;
	public boolean allowNovis;
	public boolean allowNoxen;
	public boolean allowPondus;
	public boolean allowRegeneras;
	public boolean allowSubmari;
	public boolean allowTelekinesis;
	
	public MesssageLegacyConfig() 
	{
		
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(allowAccelix);
		buf.writeBoolean(allowAvex);
		buf.writeBoolean(allowFortem);
		buf.writeBoolean(allowGlacen);
		buf.writeBoolean(allowLumen);
		buf.writeBoolean(allowNovis);
		buf.writeBoolean(allowNoxen);
		buf.writeBoolean(allowPondus);
		buf.writeBoolean(allowRegeneras);
		buf.writeBoolean(allowSubmari);
		buf.writeBoolean(allowTelekinesis);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		allowAccelix = buf.readBoolean();
		allowAvex = buf.readBoolean();
		allowFortem = buf.readBoolean();
		allowGlacen = buf.readBoolean();
		allowLumen = buf.readBoolean();
		allowNovis = buf.readBoolean();
		allowNoxen = buf.readBoolean();
		allowPondus = buf.readBoolean();
		allowRegeneras = buf.readBoolean();
		allowSubmari = buf.readBoolean();
		allowTelekinesis = buf.readBoolean();
	}

	@Override
	public void handleClientSide(MesssageLegacyConfig message, EntityPlayer player)
	{
		Minecraft.getMinecraft().addScheduledTask(() -> {
			
			LorienLegacies.instance.proxy.legacyUseData = new LegacyUse();
			LorienLegacies.instance.proxy.legacyUseData.allowAccelix = message.allowAccelix;
			LorienLegacies.instance.proxy.legacyUseData.allowAvex = message.allowAvex;
			LorienLegacies.instance.proxy.legacyUseData.allowFortem = message.allowFortem;
			LorienLegacies.instance.proxy.legacyUseData.allowGlacen = message.allowGlacen;
			LorienLegacies.instance.proxy.legacyUseData.allowLumen = message.allowLumen;
			LorienLegacies.instance.proxy.legacyUseData.allowNovis = message.allowNovis;
			LorienLegacies.instance.proxy.legacyUseData.allowNoxen = message.allowNoxen;
			LorienLegacies.instance.proxy.legacyUseData.allowPondus = message.allowPondus;
			LorienLegacies.instance.proxy.legacyUseData.allowRegeneras = message.allowRegeneras;
			LorienLegacies.instance.proxy.legacyUseData.allowSubmari = message.allowSubmari;
			LorienLegacies.instance.proxy.legacyUseData.allowTelekinesis = message.allowTelekinesis;
			player.sendMessage(new TextComponentString("Lorien Legacies config " + (clientHasRecievedMessageOnce ? "reloaded" : "loaded")).setStyle(new Style().setColor(TextFormatting.BLUE)));
			clientHasRecievedMessageOnce = true;
		
		});
	}

	@Override
	public void handleServerSide(MesssageLegacyConfig message, EntityPlayer player)
	{
		
	}

}
