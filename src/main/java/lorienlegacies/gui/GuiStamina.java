package lorienlegacies.gui;

import lorienlegacies.config.ConfigLorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GuiStamina extends GuiScreen
{
	private static final int VANILLA_XP_BAR_WIDTH = 182;
	
	public void render(float stamina, RenderGameOverlayEvent event)
	{
		// If we're rendering the XP bar, intercept the event
		if (event.getType() == ElementType.EXPERIENCE && !Minecraft.getMinecraft().player.isRidingHorse() && Minecraft.getMinecraft().playerController.gameIsSurvivalOrAdventure())
		{
			event.setCanceled(true); // Cancel it
			
			// Calculate size of screen
			ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        	int screenWidth = scaled.getScaledWidth();
        	
        	// Confine stamina percentage to reasonable bounds
        	float percentage = Math.max(Math.min(stamina / ConfigLorienLegacies.legacyStamina.maxStamina, 1.0f), 0.0f);
        	
        	// Render XP and stamina bars
        	renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2 - VANILLA_XP_BAR_WIDTH/2, 0, Minecraft.getMinecraft().player.experience, true, true); // Vanilla
            renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2, 0, percentage, false, true); // Stamina
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS); // Fix hunger by binding its texture again
		}
	}
	
	/*
	 * Modified version of GuiIngame's vanilla XP bar rendering code
	 */
	private void renderExpBar(ScaledResolution scaledRes, int x, int yOffset, float experience, boolean vanilla, boolean halfWidth)
    {
		Minecraft.getMinecraft().mcProfiler.startSection("expBar");
        Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
        int i = Minecraft.getMinecraft().player.xpBarCap();
        
        GlStateManager.color(1.0f, 1.0f, 1.0f); // Fix colours
        
        // Draw bar
        if (i > 0 || !vanilla)
        {
            int j = VANILLA_XP_BAR_WIDTH; // Width
            int k = (int)(experience * 183.0F)-1; // Fix off by 1 error
            int l = scaledRes.getScaledHeight() - 32 + 3 - yOffset;
            
            // Scale and render bar background
            if (halfWidth) x *= 2;
            if (halfWidth) GlStateManager.scale(0.5, 1.0, 1.0); // Make half width (if need be)
            this.drawTexturedModalRect(x, l, 0, 64, j, 5); // Draw bar texture
            if (halfWidth) GlStateManager.scale(2.0, 1.0, 1.0); // "Un-make" half width (again, if need be)
            
            if (k > 0) // Fill in bar by rendering amount of XP (or stamina)
            {
            	if (!vanilla) GlStateManager.color(0.3f, 0.3f, 1.0f);
            	
            	if (halfWidth) GlStateManager.scale(0.5, 1.0, 1.0);
                this.drawTexturedModalRect(x, l, 0, 69, k, 5);
                if (halfWidth) GlStateManager.scale(2.0, 1.0, 1.0);
                
                if (!vanilla) GlStateManager.color(1.0f, 1.0f, 1.0f);
            }
        }

        Minecraft.getMinecraft().mcProfiler.endSection();
        
        // Draw XP level text above bar
        if (Minecraft.getMinecraft().player.experienceLevel > 0)
        {
            Minecraft.getMinecraft().mcProfiler.startSection("expLevel");
            String s = "" + Minecraft.getMinecraft().player.experienceLevel;
            int i1 = (scaledRes.getScaledWidth() - Minecraft.getMinecraft().fontRenderer.getStringWidth(s)) / 2;
            int j1 = scaledRes.getScaledHeight() - 31 - 4;
            Minecraft.getMinecraft().fontRenderer.drawString(s, i1 + 1, j1, 0);
            Minecraft.getMinecraft().fontRenderer.drawString(s, i1 - 1, j1, 0);
            Minecraft.getMinecraft().fontRenderer.drawString(s, i1, j1 + 1, 0);
            Minecraft.getMinecraft().fontRenderer.drawString(s, i1, j1 - 1, 0);
            Minecraft.getMinecraft().fontRenderer.drawString(s, i1, j1, 8453920);
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
    }
}
