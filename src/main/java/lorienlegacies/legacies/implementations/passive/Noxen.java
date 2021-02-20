package lorienlegacies.legacies.implementations.passive;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.PassiveLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Noxen extends PassiveLegacy
{
	// Normally having one variable for all players is wrong, but since this is client-side, there's only the one
	private boolean didApplyNightVision;
	
	public Noxen()
	{
		super("Noxen", Effects.NIGHT_VISION);
		MinecraftForge.EVENT_BUS.register(this);
		
		BOOK_DESCRIPTION = "Like a bat, darkness does not impede you. Your eyes have adjusted to see where others do not.";
	}
	
	@Override
	protected void OnLegacyTick(PlayerEntity player) {}
	
	/*
	 * Called just before the world is rendered
	 */
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void RenderCameraEvent(EntityViewRenderEvent.CameraSetup event)
	{
		// Get player
		if (event.getInfo().getRenderViewEntity() == null || event.getInfo().getRenderViewEntity() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity)event.getInfo().getRenderViewEntity();
		
		// Check toggled
		if (LorienLegacies.proxy.GetClientLegacyData().legacyToggles.get(NAME) == false) return;
		
		// Do we have night vision over the flickering threshold?
		if (player.getActivePotionEffect(effect) != null && player.getActivePotionEffect(effect).getDuration() > 220)
		{ 
			didApplyNightVision = false; 
			return; 
		}
		
		// Get level
		int level = LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(NAME);
		
		player.addPotionEffect(new EffectInstance(effect, 220, level-1, true, true));
		didApplyNightVision = true;
	}
	
	/*
	 * Called after the world is rendered
	 */
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void RenderFinishedEvent(RenderWorldLastEvent event)
	{
		// Get player
		if (Minecraft.getInstance().player == null || Minecraft.getInstance().world == null) return;
		PlayerEntity player = Minecraft.getInstance().player;
		
		// Check toggled
		if (LorienLegacies.proxy.GetClientLegacyData().legacyToggles.get(NAME) == false) return;
		
		// Get rid of the night vision
		if (didApplyNightVision) player.getActivePotionMap().remove(effect);
	}
}
