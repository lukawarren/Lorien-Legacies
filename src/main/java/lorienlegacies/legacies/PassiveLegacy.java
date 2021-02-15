package lorienlegacies.legacies;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class PassiveLegacy extends Legacy 
{
	Effect effect;
	
	public PassiveLegacy(String name, Effect effect)
	{
		NAME = name;
		STAMINA_PER_TICK = 0.85f;
		
		this.effect = effect;
		DESCRIPTION = "Grants " + effect.getDisplayName().getString().toLowerCase();
		
		AddLevel("Level I",		10000);
		AddLevel("Level II", 	10000);
		AddLevel("Level III",	10000);
		AddLevel("Level IV", 	10000);
		AddLevel("Level V", 	10000);
	}
	
	@Override
	protected void OnLegacyTick(PlayerEntity player)
	{
		int level = GetLegacyLevel(player);
		player.addPotionEffect(new EffectInstance(effect, 20*11, level-1, false, true)); // Level "0" is actually level 1
	}

}
