package lorienlegacies.legacies.implementations;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;

public class Lumen extends Legacy 
{
	public Lumen()
	{
		NAME = "Lumen";
		DESCRIPTION = "Grants fire resistance and powers";
	}

	@Override
	protected void OnLegacyTick(EntityPlayer player)
	{
		LorienLegacies.logger.info("Lumen - OnLegacyTick()");
	}

}
