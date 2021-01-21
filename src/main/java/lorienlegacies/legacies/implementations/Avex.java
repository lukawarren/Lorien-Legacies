package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Avex extends Legacy
{

	public Avex()
	{
		NAME = "Avex";
		DESCRIPTION = "Grants swift flight";
		
		MinecraftForge.EVENT_BUS.register(this); // Need to receive events
	}

	@Override
	protected void OnLegacyTick(EntityPlayer player)
	{
		// Get player's looking direction and scale with fly speed
		Vec3d lookDirection = player.getLookVec().scale(player.capabilities.getFlySpeed() * 10);
		
		// Apply velocity to player
		player.motionX = lookDirection.x;
		player.motionY = lookDirection.y;
		player.motionZ = lookDirection.z;
		player.isAirBorne = true;
		
		// With players, we must notify the client of this change
		((EntityPlayerMP) player).connection.sendPacket(new SPacketEntityVelocity(player));
	}
	
	@SubscribeEvent
	public void OnBurnDamage(LivingAttackEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer == false) return;
		
		// If player does not have Avex, return
		if (GetLegacyLevel((EntityPlayer)event.getEntity()) == 0) return;
		
		// Cancel fall damage
		if (event.getSource().equals(DamageSource.FALL))  event.setCanceled(true);
	}
	
}
