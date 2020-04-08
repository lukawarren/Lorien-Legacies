package lorien.legacies.gui;

import lorien.legacies.legacies.LegacyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class StaminaGui extends GuiScreen // Extending for the drawTexturedModalRect function
{
	
	private final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
	
    public void render(float percentage, int stamina, int maxStamina)
	{
		
    	ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
    	int screenWidth = scaled.getScaledWidth();
    	int screenHeight = scaled.getScaledHeight();
    	
		// Calculate stamina percentage
        if (percentage > 1.0f) percentage = 1.0f;
        if (percentage < 0.0f) percentage = 0.0f;
        
        // Draw stamina bar (actually two bars - one background and one progress bar)
        float padding = 2;
        float width = Minecraft.getMinecraft().fontRenderer.getStringWidth("Stamina: " + maxStamina + " / " + maxStamina); // Expand bar to fill maximum width the text will ever need
        float height = 22; // Same height as creative bar
        float x = screenWidth - width - padding;
        float y = screenHeight - height;
        float widthPosMultipler = 200 / width;
        float heightPosMultiplier = 20 / height;
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.scale(width / 200, height / 20, 1.0f);
        this.drawTexturedModalRect(x * widthPosMultipler, y * heightPosMultiplier, 0, 46 + 0 * 20, 200, 20);
        GlStateManager.scale(200 / width, 20 / height, 1.0f);
        
        // Second bar
        width *= percentage;
        if (width < 0.001f) width = 0.001f; // width must be greater than 0 to avoid breaking the GL state
        widthPosMultipler = 200 / width;
        Minecraft.getMinecraft().getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(0.0F, 0.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.scale(width / 200, height / 20, 1.0f);
        this.drawTexturedModalRect(x * widthPosMultipler, y * heightPosMultiplier, 0, 46 + 2 * 20, 200, 20);
        GlStateManager.scale(200 / width, 20 / height, 1.0f);
        
        // Draw label
        Minecraft.getMinecraft().fontRenderer.drawString("Stamina: " + (stamina) + " / " + maxStamina, (int)x, (int)y - 10, 0x0313fc);
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
	
}
