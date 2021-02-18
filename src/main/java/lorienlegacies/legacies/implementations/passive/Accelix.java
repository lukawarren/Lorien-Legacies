package lorienlegacies.legacies.implementations.passive;

import lorienlegacies.legacies.PassiveLegacy;
import net.minecraft.potion.Effects;

public class Accelix extends PassiveLegacy
{
	public Accelix()
	{
		super("Accelix", Effects.SPEED);
		
		BOOK_DESCRIPTION = "Travel at super speed across vast distances.";
	}
}
