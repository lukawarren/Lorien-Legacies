package lorienlegacies.legacies;

import java.util.concurrent.atomic.AtomicBoolean;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/*
 * Handles passive effects like a small speed increase,
 * and slightly increased damage.
 */
public class PassiveLegacyEffects
{
	private static final float WALK_SPEED = 0.15f;
	private static final float DAMAGE_BOOST = 3.0f;
	
	public PassiveLegacyEffects()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private boolean PlayerHasLegacies(PlayerEntity player)
	{
		AtomicBoolean hasLegacies = new AtomicBoolean(false);
		
		// Client side
		if (player.world.isRemote) LorienLegacies.proxy.GetClientLegacyData().legacies.values().forEach(i -> {
			if (i > 0) hasLegacies.set(true);
		});
		
		// Server side
		else WorldLegacySaveData.get(player.getServer()).GetPlayerData().get(player.getUniqueID()).legacies.values().forEach(i -> {
			if (i > 0) hasLegacies.set(true);
		});
		
		return hasLegacies.get();
		
	}
	
	@SubscribeEvent
	public void OnLivingAttackEvent(AttackEntityEvent event)
	{
		// Check player has legacies
		if (PlayerHasLegacies(event.getPlayer()) == false) return;
		
		if (event.getTarget() instanceof LivingEntity)
		{
			LivingEntity entity = (LivingEntity)event.getTarget();
			entity.setHealth(entity.getHealth() - DAMAGE_BOOST);
		}
	}
	
	@SubscribeEvent
	public void OnLivingUpdateEvent(LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity) event.getEntity();

		// Check player has legacies
		if (PlayerHasLegacies(player) == false) return;
		
		player.abilities.setWalkSpeed(WALK_SPEED);
    }
}
