package lorien.legacies.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LegacyGui extends GuiScreen
{
	
	private final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
	private final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/demo_background.png");
	
	private int legacyIndex;
	
	public LegacyManager legacyManager;
	
	public List levelLegacies = new ArrayList<Legacy>();
	
	float panelStart;
	float panelWidth;
	float panelHeight;
	float panelCentre;
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		panelStart = this.width / 20;
		panelWidth = this.width / 20 * 19;
		panelHeight = this.height / 20 * 19;
		panelCentre = (panelStart + panelWidth) / 2;
		
		// Change size based on GUI size in settings (yes, there's an easier way to do this, but hey)
		int guiScale = Minecraft.getMinecraft().gameSettings.guiScale; // 1 small, 2 normal, 3 large, 0 auto
		// Actually, you know what?..... I can't be bothered :p
		/*
		 if (guiScale == 1)
			{
				panelStart *= 3;
				panelWidth /= 3;
				//panelHeight /= 3;
				
			}
		 */
					
		// Get all legacies and add GUI stuff for each one
		levelLegacies.clear();
		legacyManager = LorienLegacies.instance.clientLegacyManager;
		for (int i = 0; i < legacyManager.legacyList.size(); i++)
		{
			Legacy legacy = (Legacy) legacyManager.legacyList.get(i);
			if ((boolean) legacyManager.legacyEnabledList.get(i) && legacy.getEnabledInConfig())
				levelLegacies.add(legacy);
		}
		
		// Add the two buttons
		GuiButton buttonLeft = new GuiButton(1, (int)panelCentre - (int)panelStart * 3, (int)panelStart + 15, 20, 20, "<");
		buttonList.add(buttonLeft);
		GuiButton buttonRight = new GuiButton(2, (int)panelCentre + (int)panelStart * 3 - 20, (int)panelStart + 15, 20, 20, ">");
		buttonList.add(buttonRight);
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		if (button.id == 1) legacyIndex--;
		else legacyIndex++;
		if (legacyIndex < 0) legacyIndex = 0;
		if (legacyIndex > levelLegacies.size()-1) legacyIndex = levelLegacies.size()-1;
    }

	
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		Legacy legacy = (Legacy) levelLegacies.get(legacyIndex);
		
		
		// UI Background
        this.drawWorldBackground(0);
        
        // Background panel
        float x = panelStart;
        float y = panelStart;
     	float width = panelWidth - x;
     	float height = panelHeight - x;
     	float widthPosMultipler = 247 / width;
		float heightPosMultiplier = 165 / height;
        mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.scale(width / 247, height / 165, 1.0f);
        this.drawTexturedModalRect(x * widthPosMultipler, y * heightPosMultiplier, 0, 0, 256, 256);
        GlStateManager.scale(247 / width, 165 / height, 1.0f);
        
        // Draw buttons and stuff
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        // Title bit
     	this.drawCenteredString(this.fontRenderer, "Lorien Legacies levels", (int)panelCentre, 10, 0xFFFFFFFF);
			
        // Legacy title
        fontRenderer.drawString(legacy.LEGACY_NAME, (int)panelCentre - fontRenderer.getStringWidth(legacy.LEGACY_NAME) / 2, (int)panelStart + 25, 0x0313fc);
        
        // Draw rest of legacy window
        if (legacy.hasLevels())
        {
        	for (int i = 0; i < legacy.legacyLevels.size(); i++)
        	{
        		int colour = (legacy.currentLegacyLevel == i) ? 0xB400 : 0x0313fc; // Rule Britania! We're sticking to UK spelling if it kills me!
        		fontRenderer.drawString("Level " + (i+1) + ": " + legacy.legacyLevels.get(i).description, (int)panelCentre - fontRenderer.getStringWidth("Level " + (i+1) + ": " + legacy.legacyLevels.get(i).description) / 2, 85 + i*25, colour);
        	}
        }
        else
        	fontRenderer.drawString("This legacy cannot be levelled up", (int)panelCentre - fontRenderer.getStringWidth("This legacy cannot be levelled up") / 2, 110, 0x0313fc);
      
        if (legacy.hasLevels())
        {
        	// Calculate XP needed for next legacy level
            int xpNeeded = legacy.legacyLevels.get(legacy.currentLegacyLevel).xpRequired;
            float progress = (float)legacy.xp / (float)xpNeeded;
            if (progress > 1.0f) progress = 1.0f;
            
            // Draw XP progress bar (actually two bars - one background and one progress bar)
            x = panelStart + 30;
            y = panelHeight - 30 - 2.5f;
            width = (panelWidth - 30) - x;
            height = 20;
            widthPosMultipler = 200 / width;
            heightPosMultiplier = 20 / height;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.scale(width / 200, height / 20, 1.0f);
            this.drawTexturedModalRect(x * widthPosMultipler, y * heightPosMultiplier, 0, 46 + 0 * 20, 200, 20);
            GlStateManager.scale(200 / width, 20 / height, 1.0f);
            
            // Second bar
            x += 0.7f;
            width = ((this.width - 30) - x - 5) * progress;
            if (width < 0.001f) width = 0.001f; // INFURIATING BUG ALERT - WIDTH MUST BE GREATER THAN ZERO OR IT BREAKS THE SCALE BECAUSE THE RECIPRICAL OF ZERO IS STILL ZERO WTF THIS TOOK HOURS
            widthPosMultipler = 200 / width;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(0.0F, 0.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.scale(width / 200, height / 20, 1.0f);
            this.drawTexturedModalRect(x * widthPosMultipler, y * heightPosMultiplier, 0, 46 + 1 * 20, 200, 20);
            GlStateManager.scale(200 / width, 20 / height, 1.0f);
            
            // Draw XP label
            fontRenderer.drawString("XP: " + (legacy.xp) + " / " + legacy.legacyLevels.get(legacy.currentLegacyLevel).xpRequired, (int)x, (int)y - 10, 0x0313fc);
        }
                
    }
	
    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
	
}
