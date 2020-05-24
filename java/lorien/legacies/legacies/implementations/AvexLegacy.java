package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AvexLegacy extends Legacy {

	private static final float SPEED = 10.0f;
	private static final float FAST_MODIFIER = 2.0f;
	
	private float ticksSinceToggle; // Used so that the ground calculation will not impede lift-off
	
	public AvexLegacy()
	{
		LEGACY_NAME = "Avex";
		DESCRIPTION = "grants swift flight";
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		// Check if toggled
		if (!toggled) return;
		
		// Get player's looking director scaled with speed
		double speedModifier = player.capabilities.getFlySpeed() * SPEED;
		if (player.isSprinting()) speedModifier *= 2; // Java is worse than C so I can't just treat a boolean as an int and use it in the line above :(
		Vec3d scaledLookDirection = new Vec3d(player.getLookVec().x * speedModifier, player.getLookVec().y * speedModifier, player.getLookVec().z * speedModifier);
		
		// Apply velocity to player
		player.setVelocity(scaledLookDirection.x, scaledLookDirection.y, scaledLookDirection.z);
		
		// If the player is touching the ground and they have been flying for more than 1 second (20 ticks), untoggle Avex
		if (player.onGround && ticksSinceToggle > 20) toggle(player);
		
		ticksSinceToggle++;
	}
	
	// Override the toggle function to set ticksSinceToggle
	@Override
	public void toggle(EntityPlayer player)
	{
		if (!player.world.isRemote)
			player.sendMessage(new TextComponentString(LEGACY_NAME + " legacy toggled - set to " + !toggled).setStyle(new Style().setColor(TextFormatting.RED)));
		toggled = !toggled;
		
		ticksSinceToggle = 0;
	}
	
	@Override
	public int getStaminaPerSecond()
	{
		return toggled ? 10 : 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowAvex;
	}
	
}
