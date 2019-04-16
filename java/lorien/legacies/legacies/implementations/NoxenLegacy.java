package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class NoxenLegacy extends Legacy {
	
	public NoxenLegacy()
	{
		LEGACY_NAME = "Noxen";
		DESCRIPTION = "grants night vision";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 11, 10, true, false));
	}
	
	@Override
	public void blessedMessage(EntityPlayer player)
	{
		player.sendMessage(new TextComponentString(LEGACY_NAME + " - " + DESCRIPTION).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}
}
