package lorienlegacies.legacies;

import net.minecraft.entity.player.EntityPlayer;

/*
 * Base class for each legacy.
 */
public abstract class Legacy
{
	protected String NAME;
	protected String DESCRIPTION;
	
	protected String GetName() 			{ return NAME; }
	protected String GetDescription() 	{ return DESCRIPTION; }
	
	protected abstract void OnLegacyTick(EntityPlayer player);
}