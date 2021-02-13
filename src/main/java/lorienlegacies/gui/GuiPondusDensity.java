package lorienlegacies.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.implementations.Pondus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class GuiPondusDensity extends Screen
{	
	private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
	private static final ResourceLocation WATER_LOCATION = new ResourceLocation("textures/block/water_still.png");
	private static final ResourceLocation LAVA_LOCATION = new ResourceLocation("textures/block/lava_still.png");
	private static final ResourceLocation DIRT_LOCATION = new ResourceLocation("textures/block/dirt.png");
	private static final ResourceLocation GRASS_LOCATION = new ResourceLocation("textures/block/grass_block_top.png");
	private static final ResourceLocation ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
	
	private static final float TICKS_ACTIVE = 1500;
	
	private int oldSlot;
	private int newSlot;
	private float ticksOpen = 0;
	
	public GuiPondusDensity(boolean directionUp)
	{
		super(new StringTextComponent(""));
		
		// Alert legacy too
		Pondus pondus = (Pondus) LorienLegacies.proxy.GetLegacyManager().GetLegacies().get("Pondus");
		this.oldSlot = pondus.OnClientDensityChange(directionUp);
		this.newSlot = this.oldSlot + (directionUp ? 1 : -1);
	
		// Confine to reasonable bounds
		if (this.newSlot < Pondus.MIN_DENSITY) this.newSlot = Pondus.MIN_DENSITY;
		if (this.newSlot > Pondus.MAX_DENSITY) this.newSlot = Pondus.MAX_DENSITY;
	}
	
	@SuppressWarnings("deprecation")
	public void Render(RenderGameOverlayEvent event)
	{
		MatrixStack matrixStack = event.getMatrixStack();
		
		// Densities
		float oldSlotPercentage = (float) oldSlot / Pondus.MAX_DENSITY;
		float newSlotPercentage = (float) newSlot / Pondus.MAX_DENSITY;
		float percentage = lerp(newSlotPercentage, oldSlotPercentage, ticksOpen / TICKS_ACTIVE);
		
    	int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
		
		// Draw slider
		final int sliderX = 22;
		final int sliderY = height - 205;
		Minecraft.getInstance().getTextureManager().bindTexture(WIDGETS_LOCATION);
		matrixStack.rotate(new Quaternion(new Vector3f(0, 0, 1), +90, true));
		this.blit(matrixStack, sliderY, -sliderX, 0, 46, 200, 20);
		
		// Fill slider
		int sliderHeight = (int)(percentage * 200);
		GlStateManager.color4f(0.1f, 0.8f, 1.0f, 1.0f);
		matrixStack.rotate(new Quaternion(new Vector3f(0, 0, 1), +180, true));
		this.blit(matrixStack, -sliderY-200, sliderX-20, 0, 46, sliderHeight, 20);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		matrixStack.rotate(new Quaternion(new Vector3f(0, 0, 1), -90-180, true));
		
		// Draw density icons
		final int iconsX = sliderX*10 + 32;
		final int iconsY = height*10 - 2040;
		final int iconsHeight = 2000 / 4;
		
		matrixStack.scale(0.1f, 0.1f, 0.1f);
		
		// Dirt
		Minecraft.getInstance().getTextureManager().bindTexture(DIRT_LOCATION);
		this.blit(matrixStack, iconsX, iconsY + iconsHeight*0, 0, 0, 256, 256);
		
		// UV correction for liquids
		matrixStack.scale(1, 20, 1);
		
		// Water
		GlStateManager.color4f(0x3f / 256.0f, 0x76 /  256.0f, 0xe4 / 256.0f, 1.0f);
		Minecraft.getInstance().getTextureManager().bindTexture(WATER_LOCATION);
		this.blit(matrixStack, iconsX, iconsY/20 + iconsHeight/20, 0, 0, 256, 13);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		// Lava
		Minecraft.getInstance().getTextureManager().bindTexture(LAVA_LOCATION);
		this.blit(matrixStack, iconsX, iconsY/20 + iconsHeight/10, 0, 0, 256, 13);
		
		// UV correction for liquids
		matrixStack.scale(1, 1.0f/20.0f, 1);
		
		// Air
		Minecraft.getInstance().getTextureManager().bindTexture(GRASS_LOCATION);
		this.blit(matrixStack, iconsX, iconsY + iconsHeight*3, 0, 0, 256, 256);
		
		// Restore state
		matrixStack.scale(10.0f, 10.0f, 10.0f);
		Minecraft.getInstance().getTextureManager().bindTexture(ICONS_LOCATION);
		
		// Close screen
		if (ticksOpen > TICKS_ACTIVE)
		{
			ModGUIs.ClosePondusOverlay();
		} else ticksOpen++;
	}
	
	private float lerp(float a, float b, float amount)
	{
		return a * amount + b * (1 - amount);
	}
	
	@Override
	public boolean isPauseScreen() { return false; }
	
}
