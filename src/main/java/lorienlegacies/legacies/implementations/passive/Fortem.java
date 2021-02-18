package lorienlegacies.legacies.implementations.passive;

import lorienlegacies.legacies.PassiveLegacy;
import net.minecraft.potion.Effects;

public class Fortem extends PassiveLegacy
{
	public Fortem()
	{
		super("Fortem", Effects.STRENGTH);
		
		BOOK_DESCRIPTION = "Fortem grants you remarkable strength. You feel unstoppable.";
	}
}
