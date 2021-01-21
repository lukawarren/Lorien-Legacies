package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.Vec3d;

public class Avex extends Legacy
{

	public Avex()
	{
		NAME = "Avex";
		DESCRIPTION = "Grants swift flight";
	}

	@Override
	protected void OnLegacyTick(EntityPlayer player)
	{
		// Get player's looking direction and scale with fly speed
		Vec3d lookDirection = player.getLookVec().scale(player.capabilities.getFlySpeed() * 5);
		
		// Apply velocity to player
		player.motionX = lookDirection.x;
		player.motionY = lookDirection.y;
		player.motionZ = lookDirection.z;
		player.isAirBorne = true;
		
		// With players, we must notify the client of this change
		((EntityPlayerMP) player).connection.sendPacket(new SPacketEntityVelocity(player));
	}
	
}
