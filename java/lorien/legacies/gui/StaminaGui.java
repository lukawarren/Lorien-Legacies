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
	
	public float percentage = 0.0f;
	
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
	
    public void render(int stamina, int maxStamina, RenderGameOverlayEvent event)
	{
	
    	// If we're rendering the XP bar, intercept the event
    	if (event.getType() == ElementType.EXPERIENCE && !Minecraft.getMinecraft().player.isRidingHorse() && Minecraft.getMinecraft().playerController.gameIsSurvivalOrAdventure())
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

            renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2 - 182/2, yOffset, Minecraft.getMinecraft().player.experience, true, true); // Vanilla
            GlStateManager.color(0.5f, 0.5f, 0.6f);
            renderExpBar(new ScaledResolution(Minecraft.getMinecraft()), screenWidth/2, yOffset, percentage, false, true); // Stamina
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS); // Fix hunger by binding its texture again
    		
    	}
    }
	
}
