package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class SubmariLegacy extends Legacy {

	public SubmariLegacy()
	{
		LEGACY_NAME = "Submari";
		DESCRIPTION = "grants water breathing";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (player.isPotionActive(Potion.getPotionFromResourceLocation("water_breathing")) == false)
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("water_breathing"), 1, 255, true, false));
	}
	
	@Override
	public int getStaminaPerSecond()
	{
		return toggled ? 10 : 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowSubmari;
	}

}
