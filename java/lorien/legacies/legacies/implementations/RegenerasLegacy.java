package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class RegenerasLegacy extends Legacy {

	@Override
	public void computeLegacyTick(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 1, 35, true, false));
		
	}

}
