package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class NovisLegacy extends Legacy {

	public NovisLegacy()
	{
		LEGACY_NAME = "Novis";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		if (toggled)
			player.setInvisible(toggled);
		else if (toggled == false)
		{
			boolean breakLoop = false;
			for (PotionEffect e : player.getActivePotionEffects())
			{
				if (e.getEffectName() == "invisibility")
				{
					player.setInvisible(true);
					breakLoop = true;
				}
			}
			
			if (breakLoop == false)
				player.setInvisible(false);
		}
	}
	
	
}
