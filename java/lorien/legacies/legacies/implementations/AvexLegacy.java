package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import lorien.legacies.legacies.KeyBindings;
import lorien.legacies.legacies.Legacy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class AvexLegacy extends Legacy {

	private static final float SCROLL_SENSITIVITY = 0.045f;
	private static final float MIN_SPEED = 0.01f;
	public static final float DEFAULT_SPEED = 0.05f;
	private static final float MAX_SPEED = 0.3f;
	
	private float speed;
	
	// Disabling scroll wheel's effects
	private int lastScrolledItem;
	
	public AvexLegacy()
	{
		LEGACY_NAME = "Avex";
		DESCRIPTION = "grants swift flight";
		speed = DEFAULT_SPEED;
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		
		if (KeyBindings.scrollWithAvex.isKeyDown())
		{
			player.inventory.currentItem = lastScrolledItem;
		}
		else
		{
			lastScrolledItem = player.inventory.currentItem;
			return;
		}
		
		// Returns 0 if not scrolling, -1 is scrolling down, and 1 if scrolling up
		int i = Integer.signum(Mouse.getEventDWheel());
		
		// Add to the speed
		speed += (float)i * SCROLL_SENSITIVITY * 0.1f;;
		
		// Clamp values
		if (speed < MIN_SPEED)
			speed = MIN_SPEED;
		if (speed > MAX_SPEED)
			speed = MAX_SPEED;
		
		// Change player's flight speed accordingly
		if (!player.world.isRemote)
		{
			System.out.println(customSigmoid(speed));
			player.capabilities.allowFlying = true;
			player.capabilities.setFlySpeed(customSigmoid(speed));
			player.sendPlayerAbilities();	
		}
		
		
		
	}
	
	private float customSigmoid(float x)
	{
		float sigmoid = (float) (1/( 0.1f + Math.pow(Math.E,(-1.0f*x)))) - 0.907f;
		if (sigmoid < MIN_SPEED)
			sigmoid = MIN_SPEED;
		if (sigmoid > MAX_SPEED)
			sigmoid = MAX_SPEED;
		return sigmoid;
	}
	
}
