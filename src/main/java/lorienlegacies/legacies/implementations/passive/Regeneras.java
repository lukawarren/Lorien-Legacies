package lorienlegacies.legacies.implementations.passive;

import lorienlegacies.legacies.PassiveLegacy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Regeneras extends PassiveLegacy
{
	public Regeneras()
	{
		super("Regeneras", Effects.REGENERATION);
		
		BOOK_DESCRIPTION = "Healing comes naturally, and you find yourself able to regenerate rapidly.";
	}
	
	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		
		if (player.world.getDayTime() % (20*5) == 0) // Every 5 seconds
			player.addPotionEffect(new EffectInstance(effect, 100, level-1, true, false));
	}
}
