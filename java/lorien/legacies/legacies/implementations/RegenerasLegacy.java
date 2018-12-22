package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class RegenerasLegacy extends Legacy {
	
	public RegenerasLegacy()
	{
		LEGACY_NAME = "Regeneras";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 11, 10, true, false));
	}
	
	
}
