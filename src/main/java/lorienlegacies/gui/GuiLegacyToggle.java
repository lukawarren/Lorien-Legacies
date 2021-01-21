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

	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 20;
	private static final int BUTTON_MARGIN = 5;
	
	private static final int NOT_TOGGLED_COLOUR = 0xFFAAAAAA;
	
	private boolean madeThroughKeybind;
	
	public GuiLegacyToggle(boolean madeThroughKeybind)
	{
		super();
		this.madeThroughKeybind = madeThroughKeybind;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		// Create menu of legacies
		int count = 0;
		for (String legacy : LorienLegacies.proxy.GetClientLegacyData().legacies.keySet())
		{
			// Skip if the player doesn't have the legacy
			if (LorienLegacies.proxy.GetClientLegacyData().legacies.get(legacy) == 0) continue; 
			
			// Button
			int x = this.width / 2 - BUTTON_WIDTH / 2;
			int y = 20 + count*(BUTTON_HEIGHT+BUTTON_MARGIN);
			
			GuiButton button = new GuiButton(count, x, y, legacy);
			button.setWidth(BUTTON_WIDTH);
			
			// If not toggled, change colour
			if (!LorienLegacies.proxy.GetClientLegacyData().IsLegacyToggled(legacy))
				button.packedFGColour = NOT_TOGGLED_COLOUR;
			
			this.buttonList.add(button);
			count++;
		}
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		MessageToggleLegacy message = new MessageToggleLegacy();
		message.legacy = button.displayString;
		NetworkHandler.sendToServer(message);
		
		// Toggle on client too
		LorienLegacies.proxy.GetClientLegacyData().ToggleLegacy(button.displayString);
		
		// Toggle button text colour
		if (button.packedFGColour== 0) button.packedFGColour = NOT_TOGGLED_COLOUR;
		else button.packedFGColour = 0;
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		// Draw background
		super.drawDefaultBackground();
		
		// Draw buttons and stuff
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		// Close if alt key not held down and we wern't made by a command
		if (madeThroughKeybind && !Keyboard.isKeyDown(ModKeybinds.keyToggleLegacies.getKeyCode())) Minecraft.getMinecraft().player.closeScreen();
	}
	
	@Override
	public boolean doesGuiPauseGame() { return true; }
	
}
