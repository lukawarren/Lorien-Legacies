package lorienlegacies.legacies.implementations;

import java.util.Map;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Pondus extends Legacy
{
	
	public Pondus(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Pondus";
		DESCRIPTION = "Grants waterwalking";
		STAMINA_PER_TICK = 1;
		
		AddLevel("Water walking", 1200);
		AddLevel("Lava walking", 1800);
		AddLevel("Air walking", 2200);
		AddLevel("Phase through walls", 3000);
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		//int level = GetLegacyLevel(player);
	}
	
	@SubscribeEvent
	public void OnLivingUpdateEvent(LivingUpdateEvent event)
	{	
		// Get player
		if (event.getEntityLiving() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity)event.getEntityLiving();
		
		// Check we're toggled
		if (event.getEntity().world.isRemote)
		{
			if (LorienLegacies.proxy.GetClientLegacyData().legacyToggles.get(NAME) == false) return;
		}
		else
		{
			if (IsLegacyToggled(player) == false) return;
		}
		
		if (player.isInWater())
		{
			player.setVelocity(player.getMotion().x, 0, player.getMotion().z);
			player.setPosition(player.getPosX(), (int)player.getPosY()+1, player.getPosZ());
		}
			
		// Get block
		BlockPos blockBelowPos = player.getPosition().down();
		BlockState blockBelow = player.world.getBlockState(blockBelowPos);
		
		if (blockBelow.getMaterial() == Material.WATER)
		{
			player.setNoGravity(true);
			
		}
		else
		{
			player.setNoGravity(false);
		}
		
	}
	
}
