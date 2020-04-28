package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class RegenerasLegacy extends Legacy {
	
	public RegenerasLegacy() 
	{
		LEGACY_NAME = "Regeneras";
		DESCRIPTION = "grants regeneration";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (player.isPotionActive(Potion.getPotionFromResourceLocation("regeneration")) == false)
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 1, 35, true, false));
		
	}
	
	@Override
	public int getStaminaPerSecond()
	{
		return 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowRegeneras;
	}
	
}
