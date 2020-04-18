package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.implementations.GlacenLegacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GlacenBlesser extends Blesser {
	
	private static String UNLOCALIZED_NAME = "glacenblesser";
	
	public GlacenBlesser()
	{
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.blessersTab);
		setMaxStackSize(1);
		setMaxDamage(1);
	}


	@Override
	protected void handleClient(EntityPlayer player)
	{
		LorienLegacies.instance.clientLegacyManager.legaciesEnabled = true;
		LorienLegacies.instance.clientLegacyManager.glacenLegacyEnabled = true;
		new GlacenLegacy().blessedMessage(player);
	}


	@Override
	protected void handleServer(LegacyManager l) 
	{
		l.legaciesEnabled = true;
		l.glacenLegacyEnabled = true;
	}
    
	
}
