package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AccelixLegacy extends Legacy {

	public AccelixLegacy()
	{
		LEGACY_NAME = "Accelix";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (toggled)
			player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("speed"), 1, 35, true, false));
	}
	
	
}
