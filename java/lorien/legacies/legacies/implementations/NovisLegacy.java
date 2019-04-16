package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;

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
			player.setInvisible(toggled);
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
	}
	
	@Override
	public void blessedMessage(EntityPlayer player)
	{
		player.sendMessage(new TextComponentString(LEGACY_NAME + " - " + DESCRIPTION).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}
	
}
