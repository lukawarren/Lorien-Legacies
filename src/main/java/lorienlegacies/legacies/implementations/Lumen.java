package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Lumen extends Legacy 
{
	
	public Lumen()
	{
		NAME = "Lumen";
		DESCRIPTION = "Grants fire resistance and powers";
		STAMINA_PER_TICK = 1;
		
		AddLevel("Flammable hands", 1200);
		AddLevel("Some fire and lava resistance", 1800);
		AddLevel("Fully fire and lava proof", 2200);
		AddLevel("Fireballs", 3000);
		AddLevel("Fire wave", 5000);
		
		MinecraftForge.EVENT_BUS.register(this); // Need to receive events
	}

	@SubscribeEvent
	public void OnLivingAttackEvent(LivingAttackEvent event)
	{
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		if (event.getEntity() instanceof PlayerEntity == false) return;
		
		// If player does not have Lumen, return
		if (GetLegacyLevel((PlayerEntity)event.getEntity()) == 0) return;
		
		// If player does not have Lumen toggled, return
		if (IsLegacyToggled((PlayerEntity)event.getEntity()) == false) return;
		
		// Fire resistance
		if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE))  event.setCanceled(true);
		
		// Lava resistance
		if (event.getSource().equals(DamageSource.LAVA)) event.setCanceled(true);
		
		// Magma blocks
		if (event.getSource().equals(DamageSource.HOT_FLOOR)) event.setCanceled(true);
	}
	
	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		
	}

}
