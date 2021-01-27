package lorienlegacies.legacies.implementations;

import lorienlegacies.legacies.Legacy;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;

public class Glacen extends Legacy
{
	private static final float DISTANCE = 10f;
	
	public Glacen()
	{
		NAME = "Glacen";
		DESCRIPTION = "Grants ice powers";
		STAMINA_PER_TICK = 1;
		
		AddLevel("Frost touch", 1200);
		AddLevel("Ice powers", 1800);
		AddLevel("Turn rain into snow", 2200);
		AddLevel("Mob freeze", 3000);
		AddLevel("Freeze wave", 5000);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		// Get block player is looking at
		Vector3d startVec = player.getEyePosition(1.0f).add(player.getLookVec().mul(2.0f, 2.0f, 2.0f)); // Add 1 block minimum distance
		Vector3d endVec = startVec.add(player.getLookVec().scale(DISTANCE));
		BlockRayTraceResult rayResult = player.world.rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		
		if (rayResult == null || rayResult.getPos() == null || player.world.getBlockState(rayResult.getPos()) == null) return;
		
		// Set the block to ice if it's water
		if (player.world.getBlockState(rayResult.getPos()).getMaterial() == Material.WATER)
		{
			player.world.setBlockState(rayResult.getPos(), Blocks.ICE.getDefaultState());
				
			// Play sound audible to everyone by making a new entity at the position of player and making it play the sound
			SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ());
			entity.playSound(SoundEvents.ENTITY_BOAT_PADDLE_WATER, 1f, 2f);
			entity.onKillCommand();
		}
	}
	
}
