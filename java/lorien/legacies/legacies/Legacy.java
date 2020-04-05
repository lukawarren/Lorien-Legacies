package lorien.legacies.legacies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lorien.legacies.legacies.levels.LegacyLevel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public abstract class Legacy {
	
	public String LEGACY_NAME = "[legacy name not set]";
	public String DESCRIPTION = "[description not set]";
	
	protected boolean toggled = false;
	
	public List<LegacyLevel> legacyLevels = new ArrayList<LegacyLevel>(Arrays.asList(new LegacyLevel("Base level")));
	public int currentLegacyLevel = 0;
	
	// Called every tick
	public abstract void computeLegacyTick(EntityPlayer player);
	
	public void blessedMessage(EntityPlayer player)
	{
		player.sendMessage(new TextComponentString(LEGACY_NAME + " - " + DESCRIPTION + " - Level " + (currentLegacyLevel+1)).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}
	
	public void toggle(EntityPlayer player)
	{
		if (!player.world.isRemote)
			player.sendMessage(new TextComponentString(LEGACY_NAME + " legacy toggled - set to " + !toggled).setStyle(new Style().setColor(TextFormatting.RED)));
		toggled = !toggled;
	}
	
	public void sendMessageClientside(EntityPlayer player, String message)
	{
		if (player.world.isRemote)
			player.sendMessage(new TextComponentString(message).setStyle(new Style().setColor(TextFormatting.RED)));
	}
	
	public boolean getToggled() { return toggled; }
	
	public boolean hasLevels()
	{
		return legacyLevels.size() > 1;
	}
	
	
}
