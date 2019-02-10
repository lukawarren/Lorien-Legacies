package lorien.legacies.legacies.implementations;

import org.lwjgl.input.Mouse;

import lorien.legacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;

public class AvexLegacy extends Legacy {

	private static final float SCROLL_SENSITIVITY = 0.3f;
	private static final float MIN_SPEED = 0.2f;
	private static final float MAX_SPEED = 3f;
	
	private float speed = 1f;
	
	@Override
	public void computeLegacyTick(EntityPlayer player) {
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

}
