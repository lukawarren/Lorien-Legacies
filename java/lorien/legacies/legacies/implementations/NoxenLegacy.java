package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class NoxenLegacy extends Legacy {
	
	public NoxenLegacy()
	{
		LEGACY_NAME = "Noxen";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 11, 10, true, false));
	}
	
	
}
