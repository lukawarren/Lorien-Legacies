package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SubmariLegacy extends Legacy {

	public SubmariLegacy()
	{
		LEGACY_NAME = "Submari";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("water_breathing"), 1, 255, true, false));
	}
	
	
}
