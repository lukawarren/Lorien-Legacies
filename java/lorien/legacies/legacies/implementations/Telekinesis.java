package lorien.legacies.legacies.implementations;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.ibm.icu.impl.ICUService.Key;

import lorien.legacies.legacies.KeyBindings;
import lorien.legacies.legacies.Legacy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Telekinesis extends Legacy
{

	private static final float DISTANCE = 10;
	
	private Entity pointedEntity = null;
	private Entity previousEntity = null;
	
	private static int entityID;
	
	private EntityPlayer player;
	
	public Telekinesis()
	{
		super();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
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
	                if (d0 > 3.0D)
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
	            pointedEntity = null;
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
	                    if (d2 >= 0.0D && pointedEntity instanceof EntityPlayer == false)
	                    {
	                        pointedEntity = entity1;
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
	                            if (d2 == 0.0D && pointedEntity instanceof EntityPlayer == false)
	                            {
	                                pointedEntity = entity1;
	                                vec3d3 = raytraceresult.hitVec;
	                            }
	                        }
	                        else if (pointedEntity instanceof EntityPlayer == false)
	                        {
	                            pointedEntity = entity1;
	                            vec3d3 = raytraceresult.hitVec;
	                            d2 = d3;
	                        }
	                    }
	                }
	            }

	            if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > 3.0D)
	            {
	                pointedEntity = null;
	                Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
	            }

	            if (pointedEntity != null && (d2 < d1 || Minecraft.getMinecraft().objectMouseOver == null))
	            {
	                Minecraft.getMinecraft().objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);

	                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
	                {
	                    Minecraft.getMinecraft().pointedEntity = pointedEntity;
	                }
	            }

	            Minecraft.getMinecraft().mcProfiler.endSection();
	        }
	    }

	    if (pointedEntity == null)
	    	return;
	    
	    deselectPreviousEntity();
	    
	    previousEntity = pointedEntity;

	}
	
	public void deselectPreviousEntity()
	{
		if (previousEntity == null)
			return;
		
		previousEntity.setGlowing(false);
		previousEntity = null;
	}
	
	public void deselectCurrentEntity()
	{
		entityID = 0;
		
		if (pointedEntity == null)
			return;
		
		pointedEntity.setGlowing(false);
		pointedEntity.noClip = false;
		pointedEntity = null;
		
		if (previousEntity == null)
			return;
		
		previousEntity.setGlowing(false);
		previousEntity.noClip = false;
		previousEntity = null;
		
	}

	
	public void computeLegacyTick(EntityPlayer player, boolean server)
	{
		
		if (previousEntity != null)
			previousEntity.setGlowing(false);
		
		this.player = player;

		if (player == null || KeyBindings.activateTelekinesis.isKeyDown() == false)
			return;
		
		if (!server)
		{	
			updatePointedEntity(player);
			
			if (pointedEntity == null)
				return;
			if (pointedEntity instanceof EntityPlayer)
				return;
			
			entityID = pointedEntity.getEntityId();	
		}
		else
		{	
			if (entityID == 0)
				return;
				
			pointedEntity = player.world.getEntityByID(entityID);
			
			if (pointedEntity == null)
				return;
			if (pointedEntity instanceof EntityPlayer)
				return;
			
		}
		
		if (pointedEntity == null)
			return;

		final float distance = 3f;
		Vec3d desiredPosition = new Vec3d(player.getLookVec().x * distance, player.getLookVec().y * distance + 1, player.getLookVec().z * distance).add(player.getPositionVector());
		pointedEntity.setPositionAndUpdate(desiredPosition.x, desiredPosition.y, desiredPosition.z);
		
		if (pointedEntity == null)
			return;
		
		pointedEntity.fallDistance = 0f;
		pointedEntity.motionX = 0f;
		pointedEntity.motionY = 0f;
		pointedEntity.motionZ = 0f;
		
		if (previousEntity != null)
			previousEntity.setGlowing(true);
		
	}
	
	@Override
	public void computeLegacyTick(EntityPlayer player)
	{
		System.err.println("Error - you're calling the wrong method!");
	}
	
}
