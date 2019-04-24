package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class AccelixLegacy extends Legacy
{

	public AccelixLegacy()
	{
		LEGACY_NAME = "Accelix";
		DESCRIPTION = "grants super speed at will";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (toggled)
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("speed"), 1, 35, true, false));
	}
	

	
}
