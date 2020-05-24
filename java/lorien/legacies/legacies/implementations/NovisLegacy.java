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

public class NovisLegacy extends Legacy {

	public NovisLegacy()
	{
		LEGACY_NAME = "Novis";
		DESCRIPTION = "grants invisibility at will";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{

		if (toggled)
			player.setInvisible(true);
		else if (toggled == false)
		{
			boolean playerIsAlreadyInvisible = false;
			
			for (PotionEffect e : player.getActivePotionEffects())
			{
				if (e.getEffectName() == "invisibility")
				{
					playerIsAlreadyInvisible = true;
				}
			}
			
			player.setInvisible(playerIsAlreadyInvisible);
		}
		
		//System.out.println("bob: " + toggled);
		
	}
	
	@Override
	public float getStaminaPerTick()
	{
		return toggled ? 10 : 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowNovis;
	}
	
}
