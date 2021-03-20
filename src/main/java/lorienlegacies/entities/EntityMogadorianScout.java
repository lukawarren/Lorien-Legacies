package lorienlegacies.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.world.World;

public class EntityMogadorianScout extends CreatureEntity 
{
	
	protected EntityMogadorianScout(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
		func_233666_p_().create();
	}
	
	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 0.5d));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
	}
	
	public static AttributeModifierMap getAttributes()
	{
        return MobEntity.func_233666_p_()
        		.createMutableAttribute(Attributes.MAX_HEALTH, 30.0d)
        		.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.d)
        		.create();
    }
	
}
