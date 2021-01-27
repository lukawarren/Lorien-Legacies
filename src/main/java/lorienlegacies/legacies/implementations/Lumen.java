package lorienlegacies.legacies.implementations;

import java.util.Map;
import java.util.Random;

import lorienlegacies.config.ConfigLorienLegacies;
import lorienlegacies.legacies.Legacy;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion.Mode;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Lumen extends Legacy 
{
	private static final int FIREBALL_EXPLOSION_RADIUS = 3;
	private static final int FIREWAVE_PROJECTILES = 10;
	private static final float FIREWAVE_FALL_AMOUNT = -0.3f;
	
	private Random random = new Random();
	
	public Lumen(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Lumen";
		DESCRIPTION = "Grants fire resistance and powers";
		STAMINA_PER_TICK = 1;
		
		AddLevel("Flammable hands", 1200);
		AddLevel("Some fire and lava resistance", 1800);
		AddLevel("Fully fire and lava proof", 2200);
		AddLevel("Fireballs", 3000);
		AddLevel("Fire wave", 5000);
		
		legacyAbilities.put(new LegacyAbility("Fireball", 4), NAME);
		legacyAbilities.put(new LegacyAbility("Firewave", 5), NAME);
		
		MinecraftForge.EVENT_BUS.register(this); // Need to receive events
	}

	@SubscribeEvent
	public void OnLivingAttackEvent(LivingAttackEvent event)
	{
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		if (event.getEntity() instanceof PlayerEntity == false) return;
		
		// If player does not have Lumen, return
		if (GetLegacyLevel((PlayerEntity)event.getEntity()) == 0) return;
		
		// If player does not have Lumen toggled, return
		if (IsLegacyToggled((PlayerEntity)event.getEntity()) == false) return;
		
		int level = GetLegacyLevel((PlayerEntity)event.getEntity());
		
		// Fire resistance
		if (level >= 2 && event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE))  event.setCanceled(true);
		
		// Magma blocks
		if (level >= 2 && event.getSource().equals(DamageSource.HOT_FLOOR)) event.setCanceled(true);
		
		// Lava resistance
		if (level >= 3 && event.getSource().equals(DamageSource.LAVA)) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void OnPlayerInteractEvent(PlayerInteractEvent event)
	{
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		// Check it was a right click
		if (event instanceof PlayerInteractEvent.RightClickBlock == false) return;
		
		if (event.getEntity() instanceof PlayerEntity == false) return;
		
		// If player does not have Lumen, return
		if (GetLegacyLevel((PlayerEntity)event.getEntity()) == 0) return;
		
		// If player does not have Lumen toggled, return
		if (IsLegacyToggled((PlayerEntity)event.getEntity()) == false) return;
		
		// Check the hand is empty
		if (event.getItemStack().getItem().getTranslationKey() != Item.getItemById(0).getTranslationKey()) return;
		
		// Get the block the player is looking at
		PlayerEntity player = (PlayerEntity)event.getEntity();
		Vector3d startVec = player.getEyePosition(1.0f);
		Vector3d endVec = startVec.add(player.getLookVec().scale(10.0f));
		BlockRayTraceResult rayResult = event.getWorld().rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
		
		if (rayResult == null || rayResult.getPos() == null || event.getWorld().getBlockState(rayResult.getPos()) == null) return;
		
		IgniteBlock(player, rayResult.getPos(), event.getHand(), event.getFace());
		event.getFace();
		
		event.setCanceled(true);
	}
	
	// Partially lifted from FlintAndSteelItem#onItemUse, ignites block
	private void IgniteBlock(PlayerEntity player, BlockPos block, Hand hand, Direction direction)
	{
		BlockState blockState = player.world.getBlockState(block);
		
		if (CampfireBlock.canBeLit(blockState))
		{
			// Play sound audible to everyone by making a new entity at the position of player and making it play the sound
			SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ());
			entity.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0f, random.nextFloat() * 0.4f + 0.8f);
			entity.onKillCommand();
			
			player.world.setBlockState(block, blockState.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11); // Ignite block
		}
		else
		{
			BlockPos offsetBlockPos = block.offset(direction); // Find adjacent block
			
			if (AbstractFireBlock.canLightBlock(player.world, offsetBlockPos, player.getHorizontalFacing()))
			{
				// Play sound audible to everyone by making a new entity at the position of player and making it play the sound
				SnowballEntity entity = new SnowballEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ());
				entity.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0f, random.nextFloat() * 0.4f + 0.8f);
				entity.onKillCommand();
				
				// Set alight
				BlockState offsetBlockState = AbstractFireBlock.getFireForPlacement(player.world, offsetBlockPos);
				player.world.setBlockState(offsetBlockPos, offsetBlockState, 11);
			}
		}
	}

	@Override
	public int OnAbility(String ability, PlayerEntity player)
	{
		if (ability == "Fireball")
		{
			// Fireball
			ShootFireProjectile(player, 0.0f, false);
			
			return 25;
			
		}
		else
		{
			// Firewave
			for (int i = 0; i < FIREWAVE_PROJECTILES; ++i)
				ShootFireProjectile(player, (float) Math.toRadians(i * (360/FIREWAVE_PROJECTILES)), true);
			
			return ConfigLorienLegacies.legacyStamina.maxStamina * 2;
		}
	}
	
	private void ShootFireProjectile(PlayerEntity player, float rotateAmount, boolean wave)
	{
		Vector3d velocity = wave ?  new Vector3d(Math.sin(rotateAmount), FIREWAVE_FALL_AMOUNT, Math.cos(rotateAmount)) : player.getLookVec();
		Vector3d position = wave ? player.getEyePosition(1).add(velocity.scale(2.0f)) : player.getPositionVec().add(player.getLookVec().rotateYaw(rotateAmount));
		
		FireballEntity fireball = new FireballEntity(player.world, position.x, position.y, position.z, velocity.x, velocity.y, velocity.z)
		{
			@Override
			protected void onImpact(RayTraceResult result)
			{
				this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
				if (this.world.isRemote == false)
				{
					this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), FIREBALL_EXPLOSION_RADIUS, true, Mode.DESTROY);
					this.onKillCommand();
				}
			}
		};
		
		player.world.addEntity(fireball);
		fireball.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0f, 1.0f);
	}
	
	@Override
	protected void OnLegacyTick(PlayerEntity player) {}

}
