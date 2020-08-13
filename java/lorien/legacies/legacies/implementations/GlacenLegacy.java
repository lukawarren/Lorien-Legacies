package lorien.legacies.legacies.implementations;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GlacenLegacy extends Legacy {
	
	private static final float DISTANCE = 10f;
	private static final boolean RAY_GOES_THROUGH_ICE = true;
	
	public GlacenLegacy()
	{
		LEGACY_NAME = "Glacen";
		DESCRIPTION = "grants ice powers";
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		
		
	}
	
	public void freezeWaterIfNeeded(EntityPlayer player)
	{		
		// Get block player is looking at
		RayTraceResult rayResult = rayTrace(player, DISTANCE, Minecraft.getMinecraft().getRenderPartialTicks(), RAY_GOES_THROUGH_ICE);
			
		if (rayResult == null || rayResult.getBlockPos() == null || player.world.getBlockState(rayResult.getBlockPos()) == null)
			return;
				
		// Set the block to ice
		if (player.world.getBlockState(rayResult.getBlockPos()).getMaterial() == Material.WATER)
		{
			player.world.setBlockState(rayResult.getBlockPos(), Block.getStateById(Block.getIdFromBlock(Blocks.ICE)));
		
			// Play sound audible to everyone by making a new entity at the position of player and making it play the sound
			EntitySnowball entity = new EntitySnowball(player.world);
			entity.posX = player.posX;
			entity.posY = player.posY;
			entity.posZ = player.posZ;
			entity.playSound(SoundEvents.ENTITY_BOAT_PADDLE_WATER, 1f, 2f);
			entity.onKillCommand();
		}
	}
	
	// Custom, slightly modified version of ray-tracing function that detects flowing water
	@SuppressWarnings("unused") // Eclipse gives me an annoying warning; I care little for it
	private RayTraceResult rayTrace(EntityPlayer player, double blockReachDistance, float partialTicks, boolean rayGoesThroughIce)
	{
		Vec3d vec31 = player.getPositionEyes(partialTicks);
        Vec3d vec3d1 = player.getLook(partialTicks);
        Vec3d vec32 = vec31.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        
        final boolean stopOnLiquid = false;
        final boolean ignoreBlockWithoutBoundingBox = false;
        final boolean returnLastUncollidableBlock = false;
        
        if (!Double.isNaN(vec31.x) && !Double.isNaN(vec31.y) && !Double.isNaN(vec31.z))
        {
            if (!Double.isNaN(vec32.x) && !Double.isNaN(vec32.y) && !Double.isNaN(vec32.z))
            {
                int i = MathHelper.floor(vec32.x);
                int j = MathHelper.floor(vec32.y);
                int k = MathHelper.floor(vec32.z);
                int l = MathHelper.floor(vec31.x);
                int i1 = MathHelper.floor(vec31.y);
                int j1 = MathHelper.floor(vec31.z);
                BlockPos blockpos = new BlockPos(l, i1, j1);
                IBlockState iblockstate = player.world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

                if ((!ignoreBlockWithoutBoundingBox || iblockstate.getCollisionBoundingBox(player.world, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, stopOnLiquid))
                {
                    RayTraceResult raytraceresult = iblockstate.collisionRayTrace(player.world, blockpos, vec31, vec32);

                    if (raytraceresult != null)
                    {
                        return raytraceresult;
                    }
                }

                RayTraceResult raytraceresult2 = null;
                int k1 = 200;

                while (k1-- >= 0)
                {
                    if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z))
                    {
                        return null;
                    }

                    if (l == i && i1 == j && j1 == k)
                    {
                        return returnLastUncollidableBlock ? raytraceresult2 : null;
                    }

                    boolean flag2 = true;
                    boolean flag = true;
                    boolean flag1 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (i > l)
                    {
                        d0 = (double)l + 1.0D;
                    }
                    else if (i < l)
                    {
                        d0 = (double)l + 0.0D;
                    }
                    else
                    {
                        flag2 = false;
                    }

                    if (j > i1)
                    {
                        d1 = (double)i1 + 1.0D;
                    }
                    else if (j < i1)
                    {
                        d1 = (double)i1 + 0.0D;
                    }
                    else
                    {
                        flag = false;
                    }

                    if (k > j1)
                    {
                        d2 = (double)j1 + 1.0D;
                    }
                    else if (k < j1)
                    {
                        d2 = (double)j1 + 0.0D;
                    }
                    else
                    {
                        flag1 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = vec32.x - vec31.x;
                    double d7 = vec32.y - vec31.y;
                    double d8 = vec32.z - vec31.z;

                    if (flag2)
                    {
                        d3 = (d0 - vec31.x) / d6;
                    }

                    if (flag)
                    {
                        d4 = (d1 - vec31.y) / d7;
                    }

                    if (flag1)
                    {
                        d5 = (d2 - vec31.z) / d8;
                    }

                    if (d3 == -0.0D)
                    {
                        d3 = -1.0E-4D;
                    }

                    if (d4 == -0.0D)
                    {
                        d4 = -1.0E-4D;
                    }

                    if (d5 == -0.0D)
                    {
                        d5 = -1.0E-4D;
                    }

                    EnumFacing enumfacing;

                    if (d3 < d4 && d3 < d5)
                    {
                        enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
                        vec31 = new Vec3d(d0, vec31.y + d7 * d3, vec31.z + d8 * d3);
                    }
                    else if (d4 < d5)
                    {
                        enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
                        vec31 = new Vec3d(vec31.x + d6 * d4, d1, vec31.z + d8 * d4);
                    }
                    else
                    {
                        enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        vec31 = new Vec3d(vec31.x + d6 * d5, vec31.y + d7 * d5, d2);
                    }

                    l = MathHelper.floor(vec31.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
                    i1 = MathHelper.floor(vec31.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
                    j1 = MathHelper.floor(vec31.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
                    blockpos = new BlockPos(l, i1, j1);
                    IBlockState iblockstate1 = player.world.getBlockState(blockpos);
                    Block block1 = iblockstate1.getBlock();

                    if (!ignoreBlockWithoutBoundingBox || iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(player.world, blockpos) != Block.NULL_AABB)
                    {
                    	
                    	boolean rayIceCondition = rayGoesThroughIce ? (iblockstate1.getMaterial() != Material.ICE) : true;
                    	
                        if ((block1.canCollideCheck(iblockstate1, stopOnLiquid) || iblockstate1.getMaterial() == Material.WATER) && rayIceCondition)
                        {
                            RayTraceResult raytraceresult1 = iblockstate1.collisionRayTrace(player.world, blockpos, vec31, vec32);
                            
                            if (rayGoesThroughIce == false)
                            {
                            	if (iblockstate1.getMaterial() == Material.ICE)
                            		return null;
                            }
                            
                            if (raytraceresult1 != null)
                            {
                                return raytraceresult1;
                            }
                        }
                        else
                        {
                            raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
                        }
                    }
                }

                return returnLastUncollidableBlock ? raytraceresult2 : null;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public float getStaminaPerTick()
	{
		return 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowGlacen;
	}

	
}
