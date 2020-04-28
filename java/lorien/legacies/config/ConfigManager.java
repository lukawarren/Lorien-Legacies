package lorien.legacies.config;

import java.util.List;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.network.NetworkHandler;
import lorien.legacies.network.mesages.MesssageLegacyConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ConfigManager
{
	
	public ConfigManager()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{				
		if (event.side.isClient()) return;

		if (LorienLegacies.instance.proxy.configHasChanged) updateConfigForAllClients(event.world.playerEntities);
		LorienLegacies.instance.proxy.configHasChanged = false;
	}
	
	public void updateConfigForAllClients(List<EntityPlayer> playerEntities)
	{
		for (EntityPlayer p : playerEntities) updateConfigForClient(p);
	}
	
	public void updateConfigForClient(EntityPlayer p)
	{
		MesssageLegacyConfig configMessage = new MesssageLegacyConfig();
		configMessage.allowAccelix = LorienLegaciesConfig.legacyUse.allowAccelix;
		configMessage.allowAvex = LorienLegaciesConfig.legacyUse.allowAvex;
		configMessage.allowFortem = LorienLegaciesConfig.legacyUse.allowFortem;
		configMessage.allowGlacen = LorienLegaciesConfig.legacyUse.allowGlacen;
		configMessage.allowLumen = LorienLegaciesConfig.legacyUse.allowLumen;
		configMessage.allowNovis = LorienLegaciesConfig.legacyUse.allowNovis;
		configMessage.allowNoxen = LorienLegaciesConfig.legacyUse.allowNoxen;
		configMessage.allowPondus = LorienLegaciesConfig.legacyUse.allowPondus;
		configMessage.allowRegeneras = LorienLegaciesConfig.legacyUse.allowRegeneras;
		configMessage.allowSubmari = LorienLegaciesConfig.legacyUse.allowSubmari;
		configMessage.allowTelekinesis = LorienLegaciesConfig.legacyUse.allowTelekinesis;
		NetworkHandler.sendToPlayer(configMessage, (EntityPlayerMP) p);
	}
	
}
