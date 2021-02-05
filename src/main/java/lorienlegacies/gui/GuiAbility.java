package lorienlegacies.gui;

import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.matrix.MatrixStack;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.keybinds.ModKeybinds;
import lorienlegacies.legacies.Legacy.LegacyAbility;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageLegacyAbility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class GuiAbility extends Screen
{	
	private static final int TEXT_COLOUR = 0xffffffff;
	private static final int SELECTED_COLOUR = 0xff0039e6;
	
	private static final int MIN_WHEEL_DISTANCE = 10;
	private static final int DISTANCE_FROM_WHEEL = 100;
	
	private static final int WHEEL_EXPAND_SPEED = 5;
	private int framesSinceStart = 0;
	
	public GuiAbility()
	{
		super(new StringTextComponent(""));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		// Draw background
		super.renderBackground(matrixStack);
		
		// Get number of enabled abilities
		int numAbilities = 0;
		for (Entry<LegacyAbility, String> entry: LorienLegacies.proxy.GetLegacyManager().GetLegacyAbilities().entrySet())
		{
			// If legacy is of succient level
			Integer level = LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(entry.getValue());
			if (level != null && level != 0 && level >= entry.getKey().requiredLevel)
				numAbilities++;
		}
		
		if (numAbilities == 0)
		{
			Minecraft.getInstance().player.sendMessage(new StringTextComponent("§cYou do not have any abilities as of yet"), Minecraft.getInstance().player.getUniqueID());
			Minecraft.getInstance().player.closeScreen();
			return;
		}
		
		// Calculate positions and size
		int wheelX = this.width / 2;
		int wheelY = this.height / 2;
		double radiansPerSegment = 2.0 * Math.PI / (double) numAbilities;  // Each legacy will occupy a segment of the wheel
		
		// Get segment mouse is over by converting to polar coordinate angle and add half of radiansPerSegment to put boundary between options
		double mouseAngle = Math.PI/2 + Math.atan2((double)mouseY - (double)wheelY, (double)mouseX - (double)wheelX) + radiansPerSegment/2;
		if (mouseAngle < 0.0f) mouseAngle += Math.PI*2.0f; // Get rid of negative angles for easier maths
		int mouseSegment = (int) (mouseAngle / radiansPerSegment);
		
		// Distance from wheel
		double distanceFromWheel = Math.sqrt(Math.pow((double)mouseY - (double)wheelY, 2) + Math.pow((double)mouseX - (double)wheelX, 2));
		
		// Expanding effect
		int distanceFromCentre = Math.min(DISTANCE_FROM_WHEEL, framesSinceStart*WHEEL_EXPAND_SPEED);
		framesSinceStart++;
		
		// Draw segments
		int count = 0;
		LegacyAbility selectedAbility = null;
		for (Map.Entry<LegacyAbility, String> entry : LorienLegacies.proxy.GetLegacyManager().GetLegacyAbilities().entrySet())
		{
			// Rotate angle by 90 degrees anticlockwise
			double angle = radiansPerSegment * count - Math.PI/2;
				
			// Get position by going from polar coordinates to cartesian
			int x = (int) (distanceFromCentre * Math.cos(angle));
			int y = (int) (distanceFromCentre * Math.sin(angle));
				
			// Should ability be shown?
			boolean allowed = false;
			Integer level = LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(entry.getValue());
			if (level != null && level != 0 && level >= entry.getKey().requiredLevel)
				allowed = true;
			
			if (allowed) 
			{
				super.drawCenteredString(matrixStack, font, entry.getKey().name, wheelX + x, wheelY + y, mouseSegment == count && distanceFromWheel > MIN_WHEEL_DISTANCE ? SELECTED_COLOUR : TEXT_COLOUR);
				if (mouseSegment == count) selectedAbility = entry.getKey(); // For releasing of key below
				count++;
			}
		}
		
		// Close if alt key not held down
		if (GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), ModKeybinds.keyLegacyAbilities.getKey().getKeyCode()) == 0 && selectedAbility != null)
		{
			// Toggle if distance from wheel is great enough
			if (distanceFromWheel > MIN_WHEEL_DISTANCE)
			{		
				// Send to server
				MessageLegacyAbility message = new MessageLegacyAbility();
				message.ability = selectedAbility.name;
				NetworkHandler.sendToServer(message);
			}

			Minecraft.getInstance().player.closeScreen();
		}
	}
	
	@Override
	public boolean isPauseScreen() { return false; }
	
}
