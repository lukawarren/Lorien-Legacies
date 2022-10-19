package lorienlegacies.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;

public class EntityMogadorianScout extends CreatureEntity 
{
	//EVENTUALLY ADD this.entityDropItem(Items.EGG); FOR ASH
	protected EntityMogadorianScout(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
		func_233666_p_().create();
	}
	
	@Override
	protected void registerGoals()
	{

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new RandomWalkingGoal(this, .7d));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));

	}

	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}
	
	public static AttributeModifierMap getAttributes()
	{
        return MobEntity.func_233666_p_()
        		.createMutableAttribute(Attributes.MAX_HEALTH, 30.0d)
        		.createMutableAttribute(Attributes.MOVEMENT_SPEED, .25D)
        		.create();
    }
}
