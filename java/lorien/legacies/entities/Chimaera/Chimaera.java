package lorien.legacies.entities.Chimaera;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Chimaera extends EntityAgeable {

	public static final String UNLOCALIZED_NAME = "chimaera";
	
	public Chimaera(World worldIn)
	{
		super(worldIn);
		
	}
	
	/*
	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}
	*/

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return null;
	}
	
	
}
