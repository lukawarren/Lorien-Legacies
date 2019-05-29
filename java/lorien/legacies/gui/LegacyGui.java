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
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		System.out.println(TextFormatting.YELLOW.hashCode());
		levelUpLabel = new GuiLabel(fontRenderer, 1, this.width / 2 - 75, 0, 15, 75, YELLOW);
		levelUpLabel.addLine("Your legacies have levelled up!");
		this.labelList.add(levelUpLabel);		
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
	{
        
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
        this.drawDefaultBackground();
        
        
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
	
}
