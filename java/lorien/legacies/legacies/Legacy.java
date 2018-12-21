package lorien.legacies.legacies;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public abstract class Legacy {
	
	public String LEGACY_NAME = "[legacy name not set]";
	
	public boolean toggled = true;
	
	// Called every tick
	public abstract void computeLegacyTick(EntityPlayer player);
	
	public void toggle(EntityPlayer player)
	{
		player.sendMessage(new TextComponentString(LEGACY_NAME + " legacy toggled - set to " + !toggled).setStyle(new Style().setColor(TextFormatting.RED)));
		toggled = !toggled;
	}
}
