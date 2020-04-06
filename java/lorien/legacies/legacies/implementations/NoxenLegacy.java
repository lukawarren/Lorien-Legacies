package lorien.legacies.legacies.implementations;

import lorien.legacies.legacies.Legacy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class NoxenLegacy extends Legacy {
	
	// Used for auto-on night vision
	private boolean lightEnabled;
	
	public NoxenLegacy()
	{
		LEGACY_NAME = "Noxen";
		DESCRIPTION = "grants night vision";
		lightEnabled = false;
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
		// Judge by the light level or the time of day to determine if Noxen is activated
		if (isNightTime(player.world) || player.world.getLight((new BlockPos(player.posX, player.posY, player.posZ)), true) < 15) {
			if (player.isPotionActive(Potion.getPotionFromResourceLocation("night_vision")) == false)
				player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"), 556000000, 10, true, false));
			
		} else if (player.isPotionActive(Potion.getPotionFromResourceLocation("night_vision")) == true){
			player.removePotionEffect(Potion.getPotionFromResourceLocation("night_vision"));
		}
	}
	
	// If it is night out, return true
	public boolean isNightTime(World world) {
		long time = world.getWorldTime();
		
		if (time > 12500) // This is sunset
			return true;
		else 
			return false;
	}
	
}
