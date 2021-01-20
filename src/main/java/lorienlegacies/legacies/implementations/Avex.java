package lorienlegacies.legacies.implementations;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import net.minecraft.entity.player.EntityPlayer;

public class Avex extends Legacy
{

	public Avex()
	{
		NAME = "Avex";
		DESCRIPTION = "Grants swift flight";
	}

	@Override
	protected void OnLegacyTick(EntityPlayer player)
	{
		LorienLegacies.logger.info("Avex - OnLegacyTick()");
	}
	
}
