package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FortemLegacy extends Legacy {

	public FortemLegacy()
	{
		LEGACY_NAME = "Fortem";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("strength"), 1, 100, true, false));
	}
	
	
}
