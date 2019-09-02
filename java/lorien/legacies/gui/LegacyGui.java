package lorien.legacies.gui;

import java.io.IOException;

import org.lwjgl.util.Color;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;

public class LegacyGui extends GuiScreen
{
	
	private static final int YELLOW = 0xE48700;
	
	private GuiLabel levelUpLabel;
	private GuiButton testButton;
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		levelUpLabel = new GuiLabel(fontRenderer, 1, this.width / 2 - 75, 0, 15, 75, YELLOW);
		levelUpLabel.addLine("Your legacies have levelled up!");
		levelUpLabel.addLine("Select a legacy");
		this.labelList.add(levelUpLabel);
		
		
		for (int i = 0; i < 6; i++)
		{
			this.buttonList.add(new GuiButton(i+1, this.width / 20, 60 + i*25, "Legacy " + (i+1)));
		}
		
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
	{
        
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
        this.drawDefaultBackground();
        
        this.drawBackground(0);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawRect((int) (230), 60, this.width - 30, this.height - 30, 0xFFFFFFFF);
        
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
	
}
