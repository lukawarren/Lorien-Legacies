package lorien.legacies.legacies.implementations;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.Legacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTP;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;

public class Telekinesis extends Legacy
{
	
	private static final float DISTANCE = 10;
	private static final float LAUNCH_POWER = 0.9f;
	
	public boolean activated = false;
	
	private Entity previousTickEntity; // Keep track of previous entity so we can get rid of the glow effect
	private boolean lockedOntoPointedEntity; // Once the player has chosen an entity, stick with it until we're no longer toggled
	
	public Telekinesis()
	{
		super();
		LEGACY_NAME = "Telekinesis";
		MinecraftForge.EVENT_BUS.register(this);
	}

	
	public void computeLegacyTick(EntityPlayer player)
	{
		if (activated)
		{
			if (previousTickEntity != null && previousTickEntity.isDead) // If the poor sod died, reset everything
			{
				lockedOntoPointedEntity = false;
			}
			
			// Play telekinesis sound effect for the player
			//if (player.world.isRemote && lockedOntoPointedEntity) player.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.1f, 0.1f);
			
			// Get the entity the player is looking at, unless we're locked on, in which case use the previously selected entity
			float partialTicks = (player.world.isRemote) ? Minecraft.getMinecraft().getRenderPartialTicks() : 0;
			Entity pointedEntity = (lockedOntoPointedEntity) ? previousTickEntity : getMouseOver(player, partialTicks);
			if (!lockedOntoPointedEntity && pointedEntity != null) lockedOntoPointedEntity = true;
			if (lockedOntoPointedEntity && pointedEntity == null) lockedOntoPointedEntity = false;
			
			// "De-glow" previous entity and return if pointedEntity is null
			if (previousTickEntity != null && pointedEntity == null) previousTickEntity.setGlowing(false);
			if (pointedEntity == null) return; // Return if null
			if (previousTickEntity != null && previousTickEntity.getUniqueID() != pointedEntity.getUniqueID()) previousTickEntity.setGlowing(false);
			
			// Make the entity glow and lock onto it
			pointedEntity.setGlowing(true);
			lockedOntoPointedEntity = true;
			
			// Get the position where the targeted entity should gravitate towards
			final float distance = 3f;
			Vec3d desiredPosition = new Vec3d(player.getLookVec().x * distance, player.getLookVec().y * distance + 1, player.getLookVec().z * distance).add(player.getPositionVector());
			float pitch = player.cameraPitch;
			float yaw = player.cameraYaw;
			
			// Moving entities is hard, so let's just use the tp command instead
			try {
				teleportEntityToCoordinates(pointedEntity, 
						CommandBase.parseCoordinate(pointedEntity.posX, Double.toString(desiredPosition.x), true), 
						CommandBase.parseCoordinate(pointedEntity.posX, Double.toString(desiredPosition.y), true),
						CommandBase.parseCoordinate(pointedEntity.posX, Double.toString(desiredPosition.z), true),
						CommandBase.parseCoordinate(pointedEntity.rotationYaw, Double.toString(yaw), true),
						CommandBase.parseCoordinate(pointedEntity.rotationPitch, Double.toString(pitch), true));
			} catch (NumberInvalidException e) { e.printStackTrace(); }
			
			previousTickEntity = pointedEntity;
			
		}
		else
		{
			if (previousTickEntity != null)
			{
				previousTickEntity.setGlowing(false);
				previousTickEntity = null;
			}
			lockedOntoPointedEntity = false;
		}
	}
	
	public void launch(EntityPlayer player)
	{
		if (previousTickEntity == null) return;

		// Calculate force
		Vec3d force = new Vec3d(player.getLookVec().x * LAUNCH_POWER, player.getLookVec().y * LAUNCH_POWER, player.getLookVec().z * LAUNCH_POWER);
		previousTickEntity.fallDistance = 1000; // Ensure the entity wall fall to its death
		previousTickEntity.addVelocity(force.x, force.y, force.z); // Fling the entity
		
		// Deselect entity and "un-toggle" legacy
		lockedOntoPointedEntity = false;
		previousTickEntity.setGlowing(false);
		previousTickEntity = null;
		toggled = true;
		toggle(player);
		activated = false;
	}
	
	// Perfectly lifted from CommandTP::teleportEntityToCoordinates()
	private static void teleportEntityToCoordinates(Entity teleportingEntity, CommandBase.CoordinateArg argX, CommandBase.CoordinateArg argY, CommandBase.CoordinateArg argZ, CommandBase.CoordinateArg argYaw, CommandBase.CoordinateArg argPitch)
    {
        if (teleportingEntity instanceof EntityPlayerMP)
        {
            Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);

            if (argX.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X);
            }

            if (argY.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y);
            }

            if (argZ.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Z);
            }

            if (argPitch.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
            }

            if (argYaw.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
            }

            float f = (float)argYaw.getAmount();

            if (!argYaw.isRelative())
            {
                f = MathHelper.wrapDegrees(f);
            }

            float f1 = (float)argPitch.getAmount();

            if (!argPitch.isRelative())
            {
                f1 = MathHelper.wrapDegrees(f1);
            }

            teleportingEntity.dismountRidingEntity();
            ((EntityPlayerMP)teleportingEntity).connection.setPlayerLocation(argX.getAmount(), argY.getAmount(), argZ.getAmount(), f, f1, set);
            teleportingEntity.setRotationYawHead(f);
        }
        else
        {
            float f2 = (float)MathHelper.wrapDegrees(argYaw.getResult());
            float f3 = (float)MathHelper.wrapDegrees(argPitch.getResult());
            f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
            teleportingEntity.setLocationAndAngles(argX.getResult(), argY.getResult(), argZ.getResult(), f2, f3);
            teleportingEntity.setRotationYawHead(f2);
        }

        if (!(teleportingEntity instanceof EntityLivingBase) || !((EntityLivingBase)teleportingEntity).isElytraFlying())
        {
            teleportingEntity.motionY = 0.0D;
            teleportingEntity.onGround = true;
        }
    }
	
	// Lifted from EntityRenderer::getMouseOver()
	public Entity getMouseOver(Entity entity, float partialTicks)
    {

		Entity pointedEntity = null;
		RayTraceResult objectMouseOver;
		
        if (entity != null)
        {
            if (entity.world != null)
            {
                pointedEntity = null;
                double d0 = (double)DISTANCE; // this is the block reach distance
                objectMouseOver = entity.rayTrace(d0, partialTicks);
                Vec3d vec3d = entity.getPositionEyes(partialTicks);
                boolean flag = false;
                int i = 3;
                double d1 = d0;

                d1 = 6.0D;
                d0 = d1;
                    
                if (objectMouseOver != null)
                {
                    d1 = objectMouseOver.hitVec.distanceTo(vec3d);
                }

                Vec3d vec3d1 = entity.getLook(1.0F);
                Vec3d vec3d2 = vec3d.addVector(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
                pointedEntity = null;
                Vec3d vec3d3 = null;
                float f = 1.0F;
                List<Entity> list = entity.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
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
                        if (d2 >= 0.0D)
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
                                if (d2 == 0.0D)
                                {
                                    pointedEntity = entity1;
                                    vec3d3 = raytraceresult.hitVec;
                                }
                            }
                            else
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
                    objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
                }

                if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
                {
                    objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    {
                        //pointedEntity = pointedEntity;
                    }
                }
                
            }
            
        }
        
        
        return pointedEntity;
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

	@Override
	public void blessedMessage(EntityPlayer player)
	{
		if (player.world.isRemote)
			player.sendMessage(new TextComponentString(LEGACY_NAME).setStyle(new Style().setColor(TextFormatting.GOLD)));
	}

	@Override
	public float getStaminaPerTick()
	{
		return 0;
	}
	
	@Override
	public boolean getEnabledInConfig()
	{
		return LorienLegacies.instance.proxy.legacyUseData.allowTelekinesis;
	}
	
}
