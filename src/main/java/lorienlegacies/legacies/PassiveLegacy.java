package lorienlegacies.legacies;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class PassiveLegacy extends Legacy 
{
	protected Effect effect;
	
	public PassiveLegacy(String name, Effect effect)
	{
		NAME = name;
		STAMINA_PER_TICK = 0.85f;
		GENERATION_WEIGHTING = 3;
		GENERATION_POINTS = 1;
		
		this.effect = effect;
		DESCRIPTION = "Grants " + effect.getDisplayName().getString().toLowerCase();
		
		AddLevel("Level 2", 10000);
		AddLevel("Level 3",	10000);
		AddLevel("Level 4", 10000);
		AddLevel("Level 5", 10000);
	}
	
	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		player.addPotionEffect(new EffectInstance(effect, 2, level-1, true, false)); // Level "0" is actually level 1
	}

}
