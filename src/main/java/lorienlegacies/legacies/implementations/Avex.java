package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Avex extends Legacy
{

	public Avex()
	{
		NAME = "Avex";
		DESCRIPTION = "Grants swift flight";
		STAMINA_PER_TICK = 1;
		
		MinecraftForge.EVENT_BUS.register(this); // Need to receive events
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{	
		// Get player's looking direction and scale with fly speed
		Vector3d lookDirection = player.getLookVec().scale(player.abilities.getFlySpeed() * 10);
		
		// Apply velocity to player
		player.setMotion(lookDirection.x, lookDirection.y, lookDirection.z);
		player.isAirBorne = true;
		
		// With players, we must notify the client of this change
		((ServerPlayerEntity) player).connection.sendPacket(new SEntityVelocityPacket(player));
	}
	
	@SubscribeEvent
	public void OnLivingAttackEvent(LivingAttackEvent event)
	{
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		// Check the poor sod is a player
		if (event.getEntity() instanceof PlayerEntity == false) return;
		
		// If player does not have Avex, return
		if (GetLegacyLevel((PlayerEntity)event.getEntity()) == 0) return;
		
		// Cancel fall damage
		if (event.getSource().equals(DamageSource.FALL))  event.setCanceled(true);
	}
	
}
