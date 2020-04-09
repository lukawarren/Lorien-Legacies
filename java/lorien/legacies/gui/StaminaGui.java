package lorien.legacies.gui;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class StaminaGui extends GuiScreen // Extending for the drawTexturedModalRect function
{
	
	private final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
	private ResourceLocation CUSTOM_ICON_TEXTURES = new ResourceLocation(LorienLegacies.MODID + ":textures/gui/customicons.png");;
	
	public void renderExpBar(ScaledResolution scaledRes, int x, int yOffset, float experience, boolean vanilla, boolean halfWidth) // Lifted from GuiIngame
    {
        Minecraft.getMinecraft().mcProfiler.startSection("expBar");
        Minecraft.getMinecraft().getTextureManager().bindTexture(vanilla ? Gui.ICONS : CUSTOM_ICON_TEXTURES);
        int i = Minecraft.getMinecraft().player.xpBarCap();

        if (i > 0 || !vanilla)
        {
            int j = 182;
            int k = (int)(experience * 183.0F)-1; // Fix off by 1 error
            int l = scaledRes.getScaledHeight() - 32 + 3 - yOffset;
            
            if (halfWidth) x *= 2;
            if (halfWidth) GlStateManager.scale(0.5, 1.0, 1.0);
            this.drawTexturedModalRect(x, l, 0, 64, j, 5);
            if (halfWidth) GlStateManager.scale(2.0, 1.0, 1.0);
            
            if (k > 0)
            {
            	if (halfWidth) GlStateManager.scale(0.5, 1.0, 1.0);
                this.drawTexturedModalRect(x, l, 0, 69, k, 5);
                if (halfWidth) GlStateManager.scale(2.0, 1.0, 1.0);
            }
        }

        Minecraft.getMinecraft().mcProfiler.endSection();

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
	
    public void render(float percentage, int stamina, int maxStamina, RenderGameOverlayEvent event)
	{
<<<<<<< Updated upstream
		
    	ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
    	int screenWidth = scaled.getScaledWidth();
    	int screenHeight = scaled.getScaledHeight();
    	
		// Calculate stamina percentage
        if (percentage > 1.0f) percentage = 1.0f;
        if (percentage < 0.0f) percentage = 0.0f;
        
        // Draw stamina bar (actually two bars - one background and one progress bar)
        float padding = 2;
        float width = Minecraft.getMinecraft().fontRenderer.getStringWidth("Stamina: " + maxStamina + " / " + maxStamina); // Expand bar to fill maximum width the text will ever need
        float height = 20;
        float x = screenWidth - width - padding;
        float y = screenHeight - height - padding;
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
=======
	
    	// If we're rendering the XP bar, intercept the event
    	if (event.getType() == ElementType.EXPERIENCE && !Minecraft.getMinecraft().player.isRidingHorse())
    	{
    		event.setCanceled(true);
        	
        	ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        	int screenWidth = scaled.getScaledWidth();
        	int screenHeight = scaled.getScaledHeight();
        	
        	int x = 100;
        	int yOffset = 0; // Pixels to add from normal XP bar position
        	
    		// Calculate stamina percentage
            if (percentage > 1.0f) percentage = 1.0f;
            if (percentage < 0.0f) percentage = 0.0f;
            
            GlStateManager.disableBlend();
            
            if (Minecraft.getMinecraft().playerController.gameIsSurvivalOrAdventure())
            {
            	renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2 - 182/2, yOffset, Minecraft.getMinecraft().player.experience, true, true); // Vanilla
            	GlStateManager.color(0.5f, 0.5f, 0.6f);
            	renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2, yOffset, percentage, false, true); // Stamina
            } 
            else if (!Minecraft.getMinecraft().playerController.isSpectator())
            {
            	GlStateManager.color(0.5f, 0.5f, 0.6f);
            	renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2 - 182/2, yOffset, percentage, false, false); // Stamina
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS); // Fix hunger by binding its texture again
    		
    	}
>>>>>>> Stashed changes
    }
	
}
