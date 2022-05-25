package lorienlegacies.legacies.implementations.passive;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.PassiveLegacy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class Accelix extends PassiveLegacy
{
	public Accelix() {

		super("Accelix", Effects.SPEED);
		BOOK_DESCRIPTION = "Travel at super speed across vast distances.";
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void OnLivingUpdateEvent(LivingEvent.LivingUpdateEvent event){

		if (event.getEntityLiving() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity)event.getEntityLiving();

		if(player.getEntity().isInWater()){
			effect = Effects.DOLPHINS_GRACE;
		}
		else {effect = Effects.SPEED;}

	}
}
