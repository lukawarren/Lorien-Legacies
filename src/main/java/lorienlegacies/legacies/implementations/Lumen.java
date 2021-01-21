package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Lumen extends Legacy 
{
	
	public Lumen()
	{
		NAME = "Lumen";
		DESCRIPTION = "Grants fire resistance and powers";
		
		MinecraftForge.EVENT_BUS.register(this); // Need to receive events
	}

	@SubscribeEvent
	public void OnBurnDamage(LivingAttackEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer == false) return;
		
		// If player does not have Lumen, return
		if (GetLegacyLevel((EntityPlayer)event.getEntity()) == 0) return;
		
		// If player does not have Lumen toggled, return
		if (IsLegacyToggled((EntityPlayer)event.getEntity()) == false) return;
		
		// Fire resistance
		if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE))  event.setCanceled(true);
		
		// Lava resistance
		if (event.getSource().equals(DamageSource.LAVA)) event.setCanceled(true);
	}
	
	@Override
	protected void OnLegacyTick(EntityPlayer player)
	{
		
	}

}
