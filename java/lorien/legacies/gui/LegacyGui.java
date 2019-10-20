package lorien.legacies.gui;

import java.io.IOException;

import org.lwjgl.util.Color;

import lorien.legacies.legacies.levels.LegacyLevels;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;

public class LegacyGui extends GuiScreen
{
	
	private static final int YELLOW = 0xE48700;
	
	private GuiLabel levelUpLabel;
	private GuiLabel levelUpLabel2;
	private GuiButton testButton;
	
	private int legacyIndex;
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		levelUpLabel = new GuiLabel(fontRenderer, 1, this.width / 2 - 75, 0, 15, 75, 0xFFFFFFFF);
		levelUpLabel.addLine("Your legacies have levelled up!");
		this.labelList.add(levelUpLabel);
		
		levelUpLabel2 = new GuiLabel(fontRenderer, 1, this.width / 2 - 30, 10, 15, 75, 0xFFFFFFFF);
		levelUpLabel2.addLine("Select a legacy:");
		this.labelList.add(levelUpLabel2);
		
		for (int i = 0; i < LegacyLevels.legacies.length; i++)
		{
			this.buttonList.add(new GuiButton(i+1, this.width / 20, 60 + i*25, "Legacy " + LegacyLevels.legacies[i].legacyName));
		}
		
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
	{
		legacyIndex = button.id - 1;
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
        this.drawDefaultBackground();
        
        this.drawBackground(0);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        this.drawRect(this.width - 170, 60, this.width - 30, this.height - 30, 0xFFFFFFFF);
        this.drawCenteredString(this.fontRenderer, LegacyLevels.legacies[legacyIndex].legacyName, ((this.width - 170) + (this.width - 30)) / 2, 70, 0x0313fc);
        
        this.drawString(this.fontRenderer, LegacyLevels.legacies[legacyIndex].levelDescription, this.width - 160, 90, 0x0313fc);
        
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
	
}
