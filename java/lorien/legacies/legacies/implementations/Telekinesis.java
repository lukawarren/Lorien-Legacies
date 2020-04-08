package lorien.legacies.legacies.implementations;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import lorien.legacies.legacies.KeyBindings;
import lorien.legacies.legacies.Legacy;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.fixes.EntityId;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;

public class Telekinesis extends Legacy
{
	
	private static final float DISTANCE = 10;

	private HashMap<Integer, Integer> pointedEntities = new HashMap<>();
	private HashMap<Integer, Integer> previousEntities = new HashMap<>();
	private HashMap<Integer, Integer> entityIDs = new HashMap<>();
	
	// Launching variables
	private HashMap<Integer, Boolean> launchRequired = new HashMap<>();
	private HashMap<Integer, Vec3d> force = new HashMap<>();
	
	
	public Telekinesis()
	{
		super();
		LEGACY_NAME = "Telekinesis";
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	// Decides what entity player is selecting
	private void updatePointedEntity(EntityPlayer entity)
	{	
		float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
		
	    if (entity != null)
	    {
	        if (Minecraft.getMinecraft().world != null)
	        {
	            Minecraft.getMinecraft().mcProfiler.startSection("pick");
	            Minecraft.getMinecraft().pointedEntity = null;
	            //double d0 = (double)Minecraft.getMinecraft()..getBlockReachDistance();
	            double d0 = DISTANCE;
	            Minecraft.getMinecraft().objectMouseOver = entity.rayTrace(d0, partialTicks);
	            Vec3d vec3d = entity.getPositionEyes(partialTicks);
	            boolean flag = false;
	            int i = 3;
	            double d1 = d0;

	            if (Minecraft.getMinecraft().playerController.extendedReach())
	            {
	                //d1 = 6.0D;
	                //d0 = d1;
	            }
	            else
	            {
	                if (d0 > DISTANCE)
	                {
	                    flag = true;
	                }
	            }

	            if (Minecraft.getMinecraft().objectMouseOver != null)
	            {
	                d1 = Minecraft.getMinecraft().objectMouseOver.hitVec.distanceTo(vec3d);
	            }

	            Vec3d vec3d1 = entity.getLook(1.0F);
	            Vec3d vec3d2 = vec3d.addVector(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
	            
	            if(pointedEntities.containsKey(entity.getEntityId()))
	            	pointedEntities.put(entity.getEntityId(), null);
	            
	            Vec3d vec3d3 = null;
	            float f = 1.0F;
	            List<Entity> list = Minecraft.getMinecraft().world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
	            {
	                public boolean apply(@Nullable Entity p_apply_1_)
	                {
	                    return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
	                }
	            }));
	            double d2 = d1;

	            for (int j = 0; j < list.size(); ++j)
	            {
	                Entity entity1 = list.get(j);
	                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double)entity1.getCollisionBorderSize());
	                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

	                if (axisalignedbb.contains(vec3d))
	                {
	                    if (d2 >= 0.0D && getEntityBelongingToPlayer(entity) instanceof EntityPlayer == false && getEntityBelongingToPlayer(entity) instanceof EntityPlayerSP == false)
	                    {
	                    	pointedEntities.put(entity.getEntityId(), entity1.getEntityId());
	                        vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
	                        d2 = 0.0D;
	                    }
	                }
	                else if (raytraceresult != null)
	                {
	                    double d3 = vec3d.distanceTo(raytraceresult.hitVec);
	                    if (d3 < d2 || d2 == 0.0D)
	                    {
	                        if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract())
	                        {
	                            if (d2 == 0.0D && getEntityBelongingToPlayer(entity) instanceof EntityPlayer == false && getEntityBelongingToPlayer(entity) instanceof EntityPlayerSP == false)
	                            {
	                            	pointedEntities.put(entity.getEntityId(), entity1.getEntityId());
	                                vec3d3 = raytraceresult.hitVec;
	                            }
	                        }
	                        else if ( getEntityBelongingToPlayer(entity) instanceof EntityPlayer == false && getEntityBelongingToPlayer(entity) instanceof EntityPlayerSP == false)
	                        {
	                        	pointedEntities.put(entity.getEntityId(), entity1.getEntityId());
	                            vec3d3 = raytraceresult.hitVec;
	                            d2 = d3;
	                        }
	                    }
	                }
	            }

	            if ( getEntityBelongingToPlayer(entity) != null && flag && vec3d.distanceTo(vec3d3) > DISTANCE)
	            {
	                pointedEntities.put(entity.getEntityId(), null);
	                Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
	            }

	            if (getEntityBelongingToPlayer(entity) instanceof EntityPlayer == false && getEntityBelongingToPlayer(entity) instanceof EntityPlayerSP == false && (d2 < d1 || Minecraft.getMinecraft().objectMouseOver == null))
	            {
	                Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(getEntityBelongingToPlayer(entity), vec3d3);

	                if (getEntityBelongingToPlayer(entity) instanceof EntityLivingBase || getEntityBelongingToPlayer(entity) instanceof EntityItemFrame)
	                {
	                    Minecraft.getMinecraft().pointedEntity = getEntityBelongingToPlayer(entity);
	                }
	            }

	            Minecraft.getMinecraft().mcProfiler.endSection();
	        }
	    }

	    if (getEntityBelongingToPlayer(entity) instanceof EntityPlayerSP || getEntityBelongingToPlayer(entity) instanceof EntityPlayer)
	    	pointedEntities.put(entity.getEntityId(), null);
	    	
	    if (getEntityBelongingToPlayer(entity) == null)
	    	return;
	    
	    deselectPreviousEntity(entity);
	    
	    previousEntities.put(entity.getEntityId(), getEntityBelongingToPlayer(entity).getEntityId());

	}
	
	public void deselectPreviousEntity(Entity player)
	{
		if (getEntityBelongingToPlayerPreviously(player) == null)
			return;
		
		getEntityBelongingToPlayerPreviously(player).setGlowing(false);
		previousEntities.put(player.getEntityId(), null);
	}
	
	public void deselectCurrentEntity(Entity player)
	{
		entityIDs.put(player.getEntityId(), 0);
		
		if (getEntityBelongingToPlayer(player) == null)
			return;
		
		getEntityBelongingToPlayer(player).setGlowing(false);
		getEntityBelongingToPlayer(player).noClip = false;
		pointedEntities.put(player.getEntityId(), null);
		
		if (getEntityBelongingToPlayerPreviously(player) == null)
			return;
		
		getEntityBelongingToPlayerPreviously(player).setGlowing(false);
		getEntityBelongingToPlayerPreviously(player).noClip = false;
		previousEntities.put(player.getEntityId(), null);
		
	}

	
	public void computeLegacyTick(EntityPlayer player, boolean server)
	{

		
		if (getEntityBelongingToPlayerPreviously(player) != null)
			getEntityBelongingToPlayerPreviously(player).setGlowing(false);
		
		updatePointedEntity(player);
		
		if (getEntityBelongingToPlayer(player) == null)
			return;
		
		// Fix entity no-clipping
		Vec3d oldPosition = getEntityBelongingToPlayer(player).getPositionVector();
		
		final float distance = 3f;
		Vec3d desiredPosition = new Vec3d(player.getLookVec().x * distance, player.getLookVec().y * distance + 1, player.getLookVec().z * distance).add(player.getPositionVector());
		float pitch = player.cameraPitch;
		float yaw = player.cameraYaw;
		
		
		//getEntityBelongingToPlayer(player).setPositionAndUpdate(desiredPosition.x, desiredPosition.y, desiredPosition.z);
		
		getEntityBelongingToPlayerPreviously(player).onKillCommand();
		
		// Fix entity no-clipping
		//if(getEntityBelongingToPlayer(player).isInsideOfMaterial(Material.AIR) == false) // If inside of another block
		//	getEntityBelongingToPlayer(player).setPositionAndUpdate(oldPosition.x, oldPosition.y, oldPosition.z); // Move back to last known working position
		
		if (getEntityBelongingToPlayer(player) == null)
			return;
		
		getEntityBelongingToPlayer(player).fallDistance = 0f;
		getEntityBelongingToPlayer(player).motionX = 0f;
		getEntityBelongingToPlayer(player).motionY = 0f;
		getEntityBelongingToPlayer(player).motionZ = 0f;
		
		getEntityBelongingToPlayer(player).setGlowing(true);

		
	}
	
	private double getEntityColliderSize(Entity entity)
	{
		AxisAlignedBB collider = entity.getEntityBoundingBox();
		
		if (collider == null)
			return 0;
		
		double diffX = collider.maxX - collider.minX;
		double diffY = collider.maxY - collider.minY;
		double diffZ = collider.maxZ - collider.minZ;
		double finalDifference = diffX * diffY * diffZ;
		return finalDifference;
	}
	
	public void launchEntity(EntityPlayer player, boolean server)
	{/*
		if (pointedEntity == null || pointedEntity instanceof EntityPlayer)
			return;
		
		// Client side - decide if there is the need to launch, and if so calculate parameters
		if (server == false)
		{
			launchRequired = KeyBindings.launchTelekinesis.isKeyDown();
			if (launchRequired)
			{
				final float magnitude = 0.3f;
				force = new Vec3d(player.getLookVec().x * magnitude, player.getLookVec().y * magnitude, player.getLookVec().z * magnitude);
				applyLaunchEffect();
			}
		}
		
		// Launch the entity if required
		if (server && launchRequired)
		{
			applyLaunchEffect();
			launchRequired = false;
			deselectCurrentEntity();
			deselectPreviousEntity();
		}
		*/
	}
	
	// Helper function to clean up code - executed on client and server to avoid inconsistencies
	private void applyLaunchEffect()
	{
		//pointedEntity.fallDistance = 1000f;
		//pointedEntity.addVelocity(force.x, force.y, force.z);
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		System.err.println("Error - you're calling the wrong method!");
	}

	@Override
	public void blessedMessage(EntityPlayer player)
	{
		if (player.world.isRemote)
			player.sendMessage(new TextComponentString(LEGACY_NAME).setStyle(new Style().setColor(TextFormatting.GOLD)));
	}
	
	public Entity getEntityBelongingToPlayer(Entity player)
	{			
		if (player == null || player.world == null || pointedEntities.get(player.getEntityId()) == null)
			return null;
		
		try { return  player.world.getEntityByID(pointedEntities.get(player.getEntityId())); } catch (Exception e) { return null; }
	}
	
	public Entity getEntityBelongingToPlayerPreviously(Entity player)
	{
		if (player == null || player.world == null || previousEntities.get(player.getEntityId()) == null)
			return null;
		
		try { return  player.world.getEntityByID(previousEntities.get(player.getEntityId())); } catch (Exception e) { return null; }
	}

	@Override
	public int getStaminaPerSecond()
	{
		return 0;
	}
	
}
