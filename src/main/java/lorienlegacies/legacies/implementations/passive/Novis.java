package lorienlegacies.legacies.implementations.passive;

import lorienlegacies.legacies.PassiveLegacy;
import net.minecraft.potion.Effects;

public class Novis extends PassiveLegacy
{
	public Novis()
	{
		super("Novis", Effects.INVISIBILITY);
		
		BOOK_DESCRIPTION = "Light seems to defract around you. Wait... where did you go?";
	}
}
