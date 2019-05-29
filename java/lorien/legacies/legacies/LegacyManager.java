package lorien.legacies.legacies;

import org.lwjgl.input.Keyboard;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.gui.LegacyGui;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.AvexLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.implementations.LumenLegacy;
import lorien.legacies.legacies.implementations.NovisLegacy;
import lorien.legacies.legacies.implementations.NoxenLegacy;
import lorien.legacies.legacies.implementations.PondusLegacy;
import lorien.legacies.legacies.implementations.RegenerasLegacy;
import lorien.legacies.legacies.implementations.SubmariLegacy;
import lorien.legacies.legacies.implementations.Telekinesis;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import lorien.legacies.network.NetworkHandler;
import lorien.legacies.network.mesages.legacyActions.LegacyAction;
import lorien.legacies.network.mesages.legacyActions.LegacyActionConverter;
import lorien.legacies.network.mesages.legacyActions.MessageLegacyAction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LegacyManager {
	
	public EntityPlayer player;
	
	public boolean legaciesEnabled;
	
	public LumenLegacy lumenLegacy;
	public boolean lumenLegacyEnabled;
	
	public NoxenLegacy noxenLegacy;
	public boolean noxenLegacyEnabled;
	
	public SubmariLegacy submariLegacy;
	public boolean submariLegacyEnabled;
	
	public NovisLegacy novisLegacy;
	public boolean novisLegacyEnabled;
	
	public AccelixLegacy accelixLegacy;
	public boolean accelixLegacyEnabled;
	
	public FortemLegacy fortemLegacy;
	public boolean fortemLegacyEnabled;
	
	public PondusLegacy pondusLegacy;
	public boolean pondusLegacyEnabled;
	
	public RegenerasLegacy regenerasLegacy;
	public boolean regenerasLegacyEnabled;
	
	public AvexLegacy avexLegacy;
	public boolean avexLegacyEnabled;
	
	public Telekinesis telekinesis;
	
	public LegacyManager(EntityPlayer player)
	{
		this.player = player;
		MinecraftForge.EVENT_BUS.register(this);
		
		// Setup legacies
		lumenLegacy = new LumenLegacy();
		noxenLegacy = new NoxenLegacy();
		submariLegacy = new SubmariLegacy();
		accelixLegacy = new AccelixLegacy();
		fortemLegacy = new FortemLegacy();
		novisLegacy = new NovisLegacy();
		pondusLegacy = new PondusLegacy();
		regenerasLegacy = new RegenerasLegacy();
		avexLegacy = new AvexLegacy();
		telekinesis = new Telekinesis();
	}
	
	public void computeLegacyTick(boolean isServer)
	{
		
		if (lumenLegacyEnabled)
			lumenLegacy.computeLegacyTick(player);
			
		if (noxenLegacyEnabled)
			noxenLegacy.computeLegacyTick(player);
			
		if (submariLegacyEnabled)
			submariLegacy.computeLegacyTick(player);
			
		if (accelixLegacyEnabled)
			accelixLegacy.computeLegacyTick(player);
			
		if (fortemLegacyEnabled)
			fortemLegacy.computeLegacyTick(player);
			
		if (novisLegacyEnabled)
			novisLegacy.computeLegacyTick(player);
			
		if (pondusLegacyEnabled)
			pondusLegacy.computeLegacyTick(player);
			
		if (regenerasLegacyEnabled)
			regenerasLegacy.computeLegacyTick(player);
			
		if (avexLegacyEnabled)
			avexLegacy.computeLegacyTick(player);
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{	
	
		if (event.player == null || player == null)
			return;
		
		if (event.player.world.isRemote && legaciesEnabled) // Client
			computeLegacyTick(false);
		else if (legaciesEnabled) // Server
			computeLegacyTick(true);
	}
	
	/*
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{	
		// Fix Avex?
		if (!avexLegacyEnabled)
		{
			//event.player.capabilities.allowFlying = avexLegacyEnabled;
			event.player.capabilities.setFlySpeed(AvexLegacy.DEFAULT_SPEED);
			event.player.sendPlayerAbilities();
		}
		
		if (event.player != null && event.player.getUniqueID() == player.getUniqueID() && legaciesEnabled) // If player is this instance's player
		{
			if (lumenLegacyEnabled)
				lumenLegacy.computeLegacyTick(event.player);
			
			if (noxenLegacyEnabled)
				noxenLegacy.computeLegacyTick(event.player);
			
			if (submariLegacyEnabled)
				submariLegacy.computeLegacyTick(event.player);
			
			if (accelixLegacyEnabled)
				accelixLegacy.computeLegacyTick(event.player);
			
			if (fortemLegacyEnabled)
				fortemLegacy.computeLegacyTick(event.player);
			
			if (novisLegacyEnabled)
				novisLegacy.computeLegacyTick(player);
			
			if (pondusLegacyEnabled)
				pondusLegacy.computeLegacyTick(event.player);
			
			if (regenerasLegacyEnabled)
				regenerasLegacy.computeLegacyTick(event.player);
			
			if (avexLegacyEnabled)
				avexLegacy.computeLegacyTick(event.player);
			
			player = event.player; // should only be for telekinesis
			
			
		}
		
		// Telekinesis
		if (legaciesEnabled)
		{
			
			if (event.side.isServer())
			{
				telekinesis.launchEntity(player, true);
				telekinesis.computeLegacyTick(player, true);
			}
			else
			{
				
				if (KeyBindings.launchTelekinesis.isKeyDown())
					telekinesis.launchEntity(player, false);
				
				if (KeyBindings.activateTelekinesis.isPressed())
				{
					telekinesis.computeLegacyTick(player, event.side.isServer());
				}
			}
		}
		
		//Minecraft.getMinecraft().displayGuiScreen(new LegacyGui()); Client only!
		
		if (player.world.isRemote && KeyBindings.activateTelekinesis.isPressed())
			NetworkHandler.sendToServer(new MessageLegacyAction(69, 420));
		
	}
	*/
	
	private boolean previousWaterDecision = false;
	
	// So you can't just spam it and crash the server (trust me, it was hilarious)
	private boolean lumenFireballShot = false;
	
	public void onKeyClient()
	{
		
		LegacyAction action = null;
		
		if (KeyBindings.lumenFireball.isPressed())
			action = LegacyAction.LumenFireball;
		else if (KeyBindings.igniteLumen.isPressed())
			action = LegacyAction.LumenIgnition;
		else if (KeyBindings.toggleAccelix.isPressed())
			action = LegacyAction.Accelix;
		else if (KeyBindings.toggleFortem.isPressed())
			action = LegacyAction.Fortem;
		else if (KeyBindings.toggleNovis.isPressed())
			action = LegacyAction.Novis;
		else if (KeyBindings.togglePondus.isPressed())
			action = LegacyAction.Pondus;
		
		NetworkHandler.sendToServer(new MessageLegacyAction(LegacyActionConverter.intFromLegacyAction(action)));
		
	}

	/*
	public void onKeyPress()
	{	
		
		// Lumen fire powers
		if (org.lwjgl.input.Keyboard.isKeyDown(KeyBindings.lumenFireball.getKeyCode()) && legaciesEnabled && lumenLegacyEnabled && lumenFireballShot == false)
		{
			lumenLegacy.fireball(player);
			lumenFireballShot = true;
		} else if (org.lwjgl.input.Keyboard.isKeyDown(KeyBindings.lumenFireball.getKeyCode()) == false)
			lumenFireballShot = false;
		
		// Lumen self-igniting
		if (KeyBindings.igniteLumen.isPressed() && legaciesEnabled && lumenLegacyEnabled)
			lumenLegacy.ignite(player);
		
		// Accelix toggle
		if (KeyBindings.toggleAccelix.isPressed() && legaciesEnabled && accelixLegacyEnabled)
			accelixLegacy.toggle(player);
			
		// Fortem toggle
		if (KeyBindings.toggleFortem.isPressed() && legaciesEnabled && fortemLegacyEnabled)
			fortemLegacy.toggle(player);
		
		
		// Novis toggle
		if (KeyBindings.toggleNovis.isPressed() && legaciesEnabled && novisLegacyEnabled)
			novisLegacy.toggle(player);
		
		// Pondus toggle
		if (KeyBindings.togglePondus.isPressed() && legaciesEnabled && pondusLegacyEnabled)
			pondusLegacy.toggle(player);
	}*/
	
	
}
