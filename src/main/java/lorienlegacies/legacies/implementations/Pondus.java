package lorienlegacies.legacies.implementations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessagePondusDensityChange;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

/*
 * Also relies on GuiPondusDensity and MessagePondusDensityChange
 */
public class Pondus extends Legacy
{
	public static final int MIN_DENSITY = 1;
	public static final int MAX_DENSITY = 5;
	
	Map <UUID, Integer> densityLevels = new HashMap<>();
	
	public Pondus(Map<LegacyAbility, String> legacyAbilities)
	{
		NAME = "Pondus";
		DESCRIPTION = "Grants waterwalking";
		STAMINA_PER_TICK = 0; // Cannot be toggled
		
		AddLevel("Water walking", 11000);
		AddLevel("Lava walking", 17000);
		AddLevel("Air walking", 19000);
		AddLevel("Obsidian skin", 20000);
		
		legacyAbilities.put(new LegacyAbility("Increase density", 1), NAME);
		legacyAbilities.put(new LegacyAbility("Decrease density", 1), NAME);
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	protected void OnLegacyTick(PlayerEntity player) { }
	
	/*
	 * Apply actual Pondus logic
	 */
	@SubscribeEvent
	public void OnLivingUpdateEvent(LivingUpdateEvent event)
	{
		// Get player
		if (event.getEntityLiving() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity)event.getEntityLiving();
		
		// Get level
		int level = player.world.isRemote ? LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get("Pondus") : GetLegacyLevel(player);
		
		// The player may have been re-given legacies, so correct for this
		if (densityLevels.get(player.getUniqueID()) < GetMinimumDensityForLevel(level))
			densityLevels.put(player.getUniqueID(), GetMinimumDensityForLevel(level));
		if (densityLevels.get(player.getUniqueID()) > GetMaximumDensityForLevel(level))
			densityLevels.put(player.getUniqueID(), GetMaximumDensityForLevel(level));
		
		if (ShouldCorrectPosition(player))
		{
			player.setVelocity(player.getMotion().x, 0, player.getMotion().z);
			player.setPosition(player.getPosX(), (int)player.getPosY()+1, player.getPosZ());
		}
		
		if (ShouldApplyWalkingBehaviour(player)) player.setNoGravity(true);
		else player.setNoGravity(false);
		
		// Air density
		if (densityLevels.get(player.getUniqueID()) == 1 && level >= 3) player.abilities.isFlying = true;
		else if (player.isCreative() == false) player.abilities.isFlying = false;
		
		// Noclip
		if (densityLevels.get(player.getUniqueID()) == 1 && level >= 3) player.noClip = true;
		else player.noClip = false;
	}
	
	/*
	 * Obsidian skin
	 */
	@SubscribeEvent
	public void OnLivingAttackEvent(LivingAttackEvent event)
	{
		// Check side is server-side
		if (event.getEntity().world.isRemote) return;
		
		if (event.getEntity() instanceof PlayerEntity == false) return;
		
		// If player does not have Pondus, return
		int level = GetLegacyLevel((PlayerEntity)event.getEntity());
		if (level == 0) return;
		
		if (level >= 4 && densityLevels.get(((PlayerEntity)event.getEntity()).getUniqueID()) == 5)
		{
			if (event.getSource() == DamageSource.SWEET_BERRY_BUSH || event.getSource().isProjectile() || event.getSource() == DamageSource.CACTUS)
				event.setCanceled(true);
		}
	}
	
	/*
	 * Fix Pondus bug and setup map
	 */
	@SubscribeEvent
	public void PlayerLoggedInEvent(PlayerLoggedInEvent event)
	{
		densityLevels.put(event.getPlayer().getUniqueID(), 4);
		
    	// Pondus can break gravity so reset it
		event.getPlayer().setNoGravity(false);
	}
	
	/*
	 * Send message on jumping keyboard event
	 */
	@SubscribeEvent
	public void OnEvent(KeyInputEvent event)
	{
		// Are we even in game?
		if (Minecraft.getInstance().world == null) return;
		
		// Are we jumping?
		if (event.getKey() != Minecraft.getInstance().gameSettings.keyBindJump.getKey().getKeyCode() || event.getAction() != GLFW.GLFW_PRESS) return;
		
		PlayerEntity player = Minecraft.getInstance().player;
		
		// Contrived "you can't jump" message
		if (ShouldBlockJumping(player) && LorienLegacies.proxy.GetClientLegacyData().IsLegacyToggled(NAME))
		{
			player.sendMessage(new StringTextComponent("§cYou find yourself unable to jump"), player.getUniqueID());
		}
	}
	
	
	@Override
	public float GetAbilityStamina(String ability) { return 10; }
	
	/*
	 * Called server-side. 
	 * Alter density on server and send to client.
	 */
	@Override
	public void OnAbility(String ability, PlayerEntity player)
	{
		boolean directionUp = (ability == "Increase density");
		
		Integer currentDensity = densityLevels.get(player.getUniqueID());
		
		// Check level requirements
		int level = GetLegacyLevel(player);
		if ( (directionUp == false && currentDensity == GetMinimumDensityForLevel(level)) ||
			 (directionUp == true && currentDensity == GetMaximumDensityForLevel(level)))
		{
			return;
		}
		
		/*
		 * We must only apply the change if we are a dedicated server,
		 * as the client's "densityLevels" will change the server's
		 * too if not.
		 */
		
		if (FMLEnvironment.dist == Dist.DEDICATED_SERVER)
		{
			// Apply change
			currentDensity += directionUp ? 1 : -1;
			
			// Confine to reasonable bounds
			if (currentDensity < MIN_DENSITY) currentDensity = MIN_DENSITY;
			if (currentDensity > MAX_DENSITY) currentDensity = MAX_DENSITY;
			
			densityLevels.put(player.getUniqueID(), currentDensity);
		}
		
		// Send message to client for GUI
		MessagePondusDensityChange message = new MessagePondusDensityChange();
		message.directionUp = directionUp;
		NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)player);
	}
	
	/*
	 * Called client-side to update density from GUI.
	 * Returns old density.
	 */
	public int OnClientDensityChange(boolean directionUp)
	{	
		// Apply change
		Integer currentDensity = densityLevels.get(Minecraft.getInstance().player.getUniqueID());
		int oldDensity = currentDensity.intValue();
		currentDensity += directionUp ? 1 : -1;
		
		// Confine to reasonable bounds
		if (currentDensity < MIN_DENSITY) currentDensity = MIN_DENSITY;
		if (currentDensity > MAX_DENSITY) currentDensity = MAX_DENSITY;
		
		densityLevels.put(Minecraft.getInstance().player.getUniqueID(), currentDensity);
		
		return oldDensity;
	}
	
	/*
	 * Fog whilst no-clipping
	 */
	@SubscribeEvent
	public void FogDensityEvent(EntityViewRenderEvent.FogDensity event)
	{
		if (event.getInfo().getRenderViewEntity() instanceof PlayerEntity == false) return;
		PlayerEntity player = (PlayerEntity) event.getInfo().getRenderViewEntity();
		
		boolean suffocating = player.world.getBlockState(player.getPosition().add(0, 1, 0)).getMaterial() != Material.AIR;
		
		if (suffocating && densityLevels.get(player.getUniqueID()) == 1 && LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get("Pondus") >= 4)
		{
			event.setDensity(0.2f);
			event.setCanceled(true);
		}
	}
	
	/*
	 * Levels and density logic
	 */
	
	private boolean ShouldApplyWalkingBehaviour(PlayerEntity player)
	{
		// Get block
		BlockPos blockBelowPos = player.getPosition().down();
		BlockState blockBelow = player.world.getBlockState(blockBelowPos);
		
		int level = player.world.isRemote ? LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(NAME) : GetLegacyLevel(player);
		int densityLevel = densityLevels.get(player.getUniqueID());
		
		return  (blockBelow.getMaterial() == Material.WATER && densityLevel == 3) ||
				(blockBelow.getMaterial() == Material.LAVA && densityLevel == 2 && level >= 2);
	}
	
	private boolean ShouldCorrectPosition(PlayerEntity player)
	{
		int level = player.world.isRemote ? LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(NAME) : GetLegacyLevel(player);
		int densityLevel = densityLevels.get(player.getUniqueID());
		
		return (player.isInWater() && densityLevel == 3) || (player.isInLava() && densityLevel == 2 && level >= 2);
	}
	
	private boolean ShouldBlockJumping(PlayerEntity player)
	{
		BlockPos blockBelowPos = player.getPosition().down();
		BlockState blockBelow = player.world.getBlockState(blockBelowPos);
		
		// We're client side so can't use normal methods
		int level = LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(NAME);
		int densityLevel = densityLevels.get(player.getUniqueID());
		
		return  (blockBelow.getMaterial() == Material.WATER && densityLevel == 3) ||
				(blockBelow.getMaterial() == Material.LAVA && densityLevel == 2 && level >= 2);
	}
	
	private int GetMinimumDensityForLevel(int level)
	{
		if (level == 1) return 3; // Water walking
		if (level == 2) return 2; // Lava
		if (level == 3) return 1; // Air
		else return 1;
	}
	
	private int GetMaximumDensityForLevel(int level)
	{
		if (level == 4) return MAX_DENSITY;
		else return MAX_DENSITY-1;
	}
}
