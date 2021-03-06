package lorienlegacies.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class GuiStamina extends Screen
{
	private static final ResourceLocation MODDED_ICONS_TEXTURE = new ResourceLocation(LorienLegacies.MODID, "textures/gui/icons.png");
	
	private static final int VANILLA_XP_BAR_WIDTH = 182;
	
	public GuiStamina()
	{
		super(new StringTextComponent(""));
	}
	
	/*
	 * Note: Not Screen#render
	 */
	public void Render(float stamina, RenderGameOverlayEvent event)
	{
		// If we're rendering the XP bar, intercept the event...
		if (event.getType() == ElementType.EXPERIENCE && !Minecraft.getInstance().player.isRidingHorse() && Minecraft.getInstance().playerController.gameIsSurvivalOrAdventure())
		{
			// ...but only if we have legacies
			boolean legacies = false;
			for (Integer i : LorienLegacies.proxy.GetClientLegacyData().legacies.values())
				if (i > 0) legacies = true;
			
			if (!legacies) return;
			
			event.setCanceled(true); // Cancel it

        	// Confine stamina percentage to reasonable bounds
        	float percentage = Math.max(Math.min(stamina / LorienLegacies.proxy.GetClientLegacyData().maxClientStamina, 1.0f), 0.0f);
        	
        	// Calculate scaled width and height
        	int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        	int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        	
        	// Render XP and stamina bars
        	renderExpBar(event.getMatrixStack(), width, height, width/2 - VANILLA_XP_BAR_WIDTH/2, 0, Minecraft.getInstance().player.experience, true, true); // Vanilla
            renderExpBar(event.getMatrixStack(), width, height, width/2, 0, percentage, false, true); // Stamina
            
            Minecraft.getInstance().getTextureManager().bindTexture(GUI_ICONS_LOCATION); // Fix hunger by binding its texture again
		}
	}
	
	/*
	 * Modified version of GuiIngame's vanilla XP bar rendering code
	 * (as of 1.12.2, then wrangled a bit to support the loss of ScaledResolution 
	 * and the new matrix stack stuff)
	 */
	private void renderExpBar(MatrixStack stack, int screenWidth, int screenHeight, int x, int yOffset, float experience, boolean vanilla, boolean halfWidth)
    {
		
		Minecraft.getInstance().getProfiler().startSection("expBar");
        if (vanilla) Minecraft.getInstance().getTextureManager().bindTexture(GUI_ICONS_LOCATION);
        else Minecraft.getInstance().getTextureManager().bindTexture(MODDED_ICONS_TEXTURE);
        int i = Minecraft.getInstance().player.xpBarCap();

        // Draw bar
        if (i > 0 || !vanilla)
        {
            int j = VANILLA_XP_BAR_WIDTH; // Width
            int k = (int)(experience * 183.0F)-1; // Fix off by 1 error
            int l = screenHeight - 32 + 3 - yOffset;
            
            // Scale and render bar background
            if (halfWidth) x *= 2;
            if (halfWidth) stack.scale(0.5f, 1.0f, 1.0f); // Make half width (if need be)
            this.blit(stack, x, l, 0, 64, j, 5); // Draw bar texture
            if (halfWidth) stack.scale(2.0f, 1.0f, 1.0f); // "Un-make" half width (again, if need be)
            
            if (k > 0) // Fill in bar by rendering amount of XP (or stamina)
            {    	
            	if (halfWidth) stack.scale(0.5f, 1.0f, 1.0f);
                this.blit(stack, x, l, 0, 69, k, 5);
                if (halfWidth) stack.scale(2.0f, 1.0f, 1.0f);
            }
        }

        Minecraft.getInstance().getProfiler().endSection();
        
        // Draw XP level text above bar
        if (Minecraft.getInstance().player.experienceLevel > 0)
        {
            Minecraft.getInstance().getProfiler().startSection("expLevel");
            String s = "" + Minecraft.getInstance().player.experienceLevel;
            int i1 = (screenWidth - Minecraft.getInstance().fontRenderer.getStringWidth(s)) / 2;
            int j1 = screenHeight - 31 - 4;
            Minecraft.getInstance().fontRenderer.drawString(stack, s, i1 + 1, j1, 0);
            Minecraft.getInstance().fontRenderer.drawString(stack, s, i1 - 1, j1, 0);
            Minecraft.getInstance().fontRenderer.drawString(stack, s, i1, j1 + 1, 0);
            Minecraft.getInstance().fontRenderer.drawString(stack, s, i1, j1 - 1, 0);
            Minecraft.getInstance().fontRenderer.drawString(stack, s, i1, j1, 8453920);
            Minecraft.getInstance().getProfiler().endSection();
        }
        
    }
}
