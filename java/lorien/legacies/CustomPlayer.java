package lorien.legacies;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;

public class CustomPlayer extends EntityPlayerSP {

	public CustomPlayer() {
		super(Minecraft.getMinecraft(), Minecraft.getMinecraft().player.world, Minecraft.getMinecraft().player.connection, Minecraft.getMinecraft().player.getStatFileWriter(), Minecraft.getMinecraft().player.getRecipeBook());
	}
	
	@Override
	public boolean isOnLadder()
	{
		return true;
	}
	
	@Override
	public boolean isSpectator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCreative() {
		// TODO Auto-generated method stub
		return true;
	}

}
