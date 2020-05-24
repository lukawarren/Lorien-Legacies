package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import lorien.legacies.legacies.levels.LegacyLevel;
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
	private boolean sprintedLastTick; // Used for stamina
	
	public AvexLegacy()
	{
		LEGACY_NAME = "Avex";
		DESCRIPTION = "grants swift flight";
		
		legacyLevels.add(new LegacyLevel("Your experience grants you greater speed", 5000));
		legacyLevels.add(new LegacyLevel("Flying has less toll on your stamina", 7500));
		legacyLevels.add(new LegacyLevel("You have mastered the art of speed", 10000));
		legacyLevels.add(new LegacyLevel("Extreme endurance grants you greater stamina", 15000));
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
		sprintedLastTick = player.isSprinting();
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
	public int getStaminaPerTick()
	{
		if (!toggled) return 0;
		
		int stamina = 0;
		
		if (currentLegacyLevel == 0) stamina = 15;
		if (currentLegacyLevel == 1) stamina = 15;
		if (currentLegacyLevel == 2) stamina = 10;
		if (currentLegacyLevel == 3) stamina = 10;
		if (currentLegacyLevel >= 4) stamina = 8;
		
		return sprintedLastTick ? stamina * 2 : stamina;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowAvex;
	}
	
}
