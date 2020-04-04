package lorien.legacies.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.levels.LegacyLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.GuiScrollingList;

public class LegacyGui extends GuiScreen
{
	
	private Scrollbar scrollbar;
	
	private class Scrollbar
	{

		private final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
		
		public float x;
		public float y;
		
		private float lastMouseYSinceClick;
		private float lastButtonYSinceClick;
		
		public Scrollbar()
		{
			this.x = 20;
			this.y = 60;
		}
		
		public float draw(LegacyGui gui, float mouseX, float mouseY, float heightOfList, boolean mouseClicked)
		{		
			// Detect if mouse over button
			boolean hovered = mouseX >= x-width && mouseX <= x && mouseY >= y && mouseY <= y+height;
			
			// Move scrollbar when dragged
			if (mouseClicked && mouseX <= x)
				y = mouseY;
			
			// Work out scrollbar width, height, x and y
			float width = 15;
			float height = 200;
			
			boolean scrollbarNeeded = gui.height < 60 + heightOfList; // Why 60+? See the button code, there's an offset of 60
			if (scrollbarNeeded)
			{
				float percentageVisible = gui.height / (60 + heightOfList);
				height = percentageVisible * gui.height - 60;
			}
			else
			{
				height = gui.height - 60 - 25/2;
				y = 60;
				return 0.0f;
			}
			
			// Keep y within reasonable bounds
			if (y < 60) y = 60;
			if (y + height > gui.height - 1) y = gui.height - height - 1;
			
			// Work out deviation from standard button sizes for scale
			float widthPosMultipler = 20 / width;
			float heightPosMultiplier = 200 / height;
			
			// Draw scrollbar
	        mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        int i = hovered ? 2 : 1;
	        GlStateManager.enableBlend();
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	        GlStateManager.rotate(90.0f, 0, 0, 1);
	        GlStateManager.scale(height / 200, width / 20, 1.0f); // X and Y have to be swapped since we rotated it
	        gui.drawTexturedModalRect(y*heightPosMultiplier, -x*widthPosMultipler, 0, 46 + i * 20, 200, 20); // X and Y have to be swapped since we rotated it
	        
	        // Calculate scroll amount - min 60, max gui.height - height + 30
	        float percentage = (y - 60) / ((gui.height - height - 1) - 60);
	        float differenceBetweenWindowHeightAndScrollviewHeight = heightOfList - (gui.height - 60);
	        return -percentage * differenceBetweenWindowHeightAndScrollviewHeight;
		}
		
	}
	
	private int legacyIndex;
	
	public LegacyManager legacyManager;
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		// Get all legacies and add GUI stuff for each one
		legacyManager = LorienLegacies.legacyManagers.get(0);
		for (int i = 0; i < legacyManager.legacyList.size(); i++)
		{
			Legacy legacy = (Legacy) legacyManager.legacyList.get(i);
			this.buttonList.add(new GuiButton(i+1, this.width / 20, 60 + i*25, legacy.LEGACY_NAME));
		}
		
		scrollbar = new Scrollbar();
		
		Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
	{
		legacyIndex = button.id - 1;
    }

	
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		Legacy legacy = (Legacy) legacyManager.legacyList.get(legacyIndex);
		
		// UI Background
        this.drawWorldBackground(0);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        // Title bit
     	this.drawCenteredString(this.fontRenderer, "Your legacies have levelled up!", this.width / 2, 10, 0xFFFFFFFF);
     	this.drawCenteredString(this.fontRenderer, "Please select a legacy:", this.width / 2, 20, 0xFFFFFFFF);
        
     	// White background
        this.drawGradientRect(this.width - 170, 60, this.width - 30, this.height - 30, 0xFFFFFFFF, 0xFF111111);
        
        // Legacy title
        fontRenderer.drawString(legacy.LEGACY_NAME, ((this.width - 170) + (this.width - 30)) / 2 - fontRenderer.getStringWidth(legacy.LEGACY_NAME) / 2, 70, 0x0313fc);
        
        // Draw rest of legacy window
        if (legacy.hasLevels())
        {
        	for (int i = 0; i < legacy.legacyLevels.size(); i++)
        	{
        		int colour = (legacy.currentLegacyLevel == i) ? 0xB400 : 0x0313fc; // Rule Britania! We're sticking to UK spelling if it kills me!
        		fontRenderer.drawSplitString("Level " + (i+1) + ": " + legacy.legacyLevels.get(i).description, this.width - 160, 90 + i*25, 100, colour);
        	}
        }
        else
        	fontRenderer.drawSplitString("This legacy cannot be levelled up", this.width - 160, 90, 100, 0x0313fc);
      
        // Draw scrollbar and do scrollbar logic
        float scrollAmount = scrollbar.draw(this, mouseX, mouseY, legacyManager.legacyList.size() * 25, Mouse.isButtonDown(0));
        for (int i = 0; i < buttonList.size(); i++)
        {
        	buttonList.get(i).y = (int) (60 + i*25 + scrollAmount);
        	if (buttonList.get(i).y < 60) buttonList.get(i).visible = false;
        	else buttonList.get(i).visible = true;
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
	
}
