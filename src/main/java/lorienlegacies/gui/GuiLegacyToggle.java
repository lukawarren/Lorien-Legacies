package lorienlegacies.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.keybinds.ModKeybinds;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageToggleLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiLegacyToggle extends GuiScreen
{	
	private static final int TOGGLED_COLOUR = 0xff0039e6;
	private static final int NOT_TOGGLED_COLOUR = 0xffbbbbbb;
	private static final int HIGHLIGHTED_TOGGLED_COLOUR = 0xff4d79ff;
	private static final int HIGHLIGHTED_NOT_TOGGLED_COLOUR = 0xffffffff;
	
	private static final int MIN_WHEEL_DISTANCE = 30;
	private static final int DISTANCE_FROM_WHEEL = 100;
	
	private static final int WHEEL_EXPAND_SPEED = 5;
	private int framesSinceStart = 0;
	
	public GuiLegacyToggle()
	{
		super();
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException { }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		// Draw background
		super.drawDefaultBackground();
		
		// Calculate positions and size
		int wheelX = this.width / 2;
		int wheelY = this.height / 2;
		double radiansPerSegment = 2.0 * Math.PI / LorienLegacies.proxy.GetClientLegacyData().legacies.size();  // Each legacy will occupy a segment of the wheel
		
		// Get segment mouse is over by converting to polar coordinate angle
		double mouseAngle = Math.PI/2 + Math.atan2((double)mouseY - (double)wheelY, (double)mouseX - (double)wheelX) + radiansPerSegment/2;		
		int mouseSegment = (int) (mouseAngle / radiansPerSegment);
		
		// Distance from wheel
		double distanceFromWheel = Math.sqrt(Math.pow((double)mouseY - (double)wheelY, 2) + Math.pow((double)mouseX - (double)wheelX, 2));
		
		// Expanding effect
		int distanceFromCentre = Math.min(DISTANCE_FROM_WHEEL, framesSinceStart*WHEEL_EXPAND_SPEED);
		framesSinceStart++;
		
		// Draw segments
		int count = 0;
		String selectedLegacy = "";
		for (String legacy : LorienLegacies.proxy.GetClientLegacyData().legacies.keySet())
		{
			// Rotate angle by 90 degrees anticlockwise
			double angle = radiansPerSegment * count - Math.PI/2;
			
			// Get position by going from polar coordinates to cartesian
			int x = (int) (distanceFromCentre * Math.cos(angle));
			int y = (int) (distanceFromCentre * Math.sin(angle));
			
			// Is legacy currently toggled?
			boolean toggled = LorienLegacies.proxy.GetClientLegacyData().legacyToggles.get(legacy);
			boolean hovered = mouseSegment == count && distanceFromWheel > MIN_WHEEL_DISTANCE;
			
			if (toggled) super.drawCenteredString(fontRenderer, legacy, wheelX + x, wheelY + y, hovered ? HIGHLIGHTED_TOGGLED_COLOUR : TOGGLED_COLOUR );
			else super.drawCenteredString(fontRenderer, legacy, wheelX + x, wheelY + y, hovered ? HIGHLIGHTED_NOT_TOGGLED_COLOUR * 2: NOT_TOGGLED_COLOUR * 2);
			
			if (mouseSegment == count) selectedLegacy = legacy; // For releasing of key below
			
			count++;
		}
		
		// Close if alt key not held down
		if (!Keyboard.isKeyDown(ModKeybinds.keyToggleLegacies.getKeyCode()) && selectedLegacy != "")
		{
			// Toggle if distance from wheel is great enough
			if (distanceFromWheel > MIN_WHEEL_DISTANCE)
			{		
				// Send to server
				MessageToggleLegacy message = new MessageToggleLegacy();
				message.legacy = selectedLegacy;
				NetworkHandler.sendToServer(message);
				
				// Toggle on client too
				LorienLegacies.proxy.GetClientLegacyData().ToggleLegacy(selectedLegacy);
			}

			Minecraft.getMinecraft().player.closeScreen();
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() { return false; }
	
}
