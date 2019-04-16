package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Mouse;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class AvexLegacy extends Legacy {

	private static final float SCROLL_SENSITIVITY = 0.03f;
	private static final float MIN_SPEED = 0.01f;
	private static final float MAX_SPEED = 1f;
	
	private float speed = 0.25f;
	
	public AvexLegacy()
	{
		LEGACY_NAME = "Avex";
		DESCRIPTION = "grants swift flight";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		
		// Returns 0 if not scrolling, -1 is scrolling down, and 1 if scrolling up
		int i = Integer.signum(Mouse.getEventDWheel());
		
		// Add to the speed
		speed += (float)i * SCROLL_SENSITIVITY;
		
		// Clamp between MIN_SPEED and MAX_SPEED
		if (speed < MIN_SPEED)
			speed = MIN_SPEED;
		else if (speed > MAX_SPEED)
			speed = MAX_SPEED;
		
		// Change player's flight speed accordingly
		if (!player.world.isRemote)
		{
			player.capabilities.allowFlying = true;
			player.capabilities.setFlySpeed(speed);
			player.sendPlayerAbilities();	
		}
	
	}
	
	@Override
	public void blessedMessage(EntityPlayer player)
	{
		player.sendMessage(new TextComponentString(LEGACY_NAME + " - " + DESCRIPTION).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}
	
}
