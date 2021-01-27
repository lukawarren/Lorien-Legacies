package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Avex extends Legacy
{

	private static final int RANGE = 5;
	private static final int FLY_SPEED = 3;
	
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
		// Get nearest obstruction by casting 6 rays, one for each direction
		BlockRayTraceResult results[] = new BlockRayTraceResult[6];
		results[0] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, 0, RANGE), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[1] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, 0, -RANGE), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[2] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(-RANGE, 0, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[3] =  player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(RANGE, 0, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[4] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, -RANGE, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		results[5] = player.world.rayTraceBlocks(new RayTraceContext(player.getPositionVec(), player.getPositionVec().add(0, RANGE, 0), 
				RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		
		// Get nearest ray result
		Vector3i playerPositionAsInt = new Vector3i(player.getPosX(), player.getPosY(), player.getPosZ());
		double nearestSquaredDistance = Double.MAX_VALUE;
		for (int i = 0; i < results.length; ++i)
		{
			if (results[i] != null && results[i].getPos() != null)
			{
				double distance = results[i].getPos().distanceSq(playerPositionAsInt);
				if (distance < nearestSquaredDistance) nearestSquaredDistance = distance;
			}
		}
		
		nearestSquaredDistance = Math.max(Math.sqrt(nearestSquaredDistance), 1.0);
		
		// Get player's looking direction and scale with fly speed
		Vector3d lookDirection = player.getLookVec().scale(player.abilities.getFlySpeed() * FLY_SPEED * nearestSquaredDistance);
		
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
		
		// If player does not have Avex toggled, return
		if (IsLegacyToggled((PlayerEntity)event.getEntity()) == false) return;
		
		// Cancel fall damage
		if (event.getSource().equals(DamageSource.FALL))  event.setCanceled(true);	
	
		// De-toggle legacy
		Toggle((PlayerEntity)event.getEntity());
	}
	
}
