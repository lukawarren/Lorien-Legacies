package lorien.legacies.legacies;

import org.lwjgl.input.Keyboard;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.gui.LegacyGui;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.AvexLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.implementations.GlacenLegacy;
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
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
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
	
	public GlacenLegacy glacenLegacy;
	public boolean glacenLegacyEnabled;
	
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
		glacenLegacy = new GlacenLegacy();
		
		telekinesis = new Telekinesis();
	}
	
	public void computeLegacyTick(boolean isServer)
	{
		//System.out.println("flying video desktop");
		
		if (lumenLegacyEnabled && lumenLegacy.toggled)
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
		
		if (glacenLegacyEnabled)
			glacenLegacy.computeLegacyTick(player);
		
		telekinesis.computeLegacyTick(player, isServer);
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
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event)
	{	
		
		onKeyClient();
		
		if (player == null)
			return;

		//telekinesis.computeLegacyTick(player, event.side.isServer());
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
		
		if (KeyBindings.lumenFireball.isPressed() && lumenLegacyEnabled) // Don't worry, I'm not doing client-side verification!
			action = LegacyAction.LumenFireball;
		else if (KeyBindings.igniteLumen.isPressed() && lumenLegacyEnabled) // I'm testing it on the server-side too!
			action = LegacyAction.LumenIgnition;
		else if (KeyBindings.toggleAccelix.isPressed() && accelixLegacyEnabled) // Phew!
			action = LegacyAction.Accelix;
		else if (KeyBindings.toggleFortem.isPressed() && fortemLegacyEnabled)
			action = LegacyAction.Fortem;
		else if (KeyBindings.toggleNovis.isPressed() && novisLegacyEnabled)
			action = LegacyAction.Novis;
		else if (KeyBindings.togglePondus.isPressed() && pondusLegacyEnabled)
			action = LegacyAction.Pondus;
		else if (KeyBindings.glacenFreeze.isKeyDown() && glacenLegacyEnabled)
			action = LegacyAction.GlacenFreeze;
		else if (KeyBindings.toggleAvex.isPressed() && avexLegacyEnabled)
			action = LegacyAction.Avex;
		else if (KeyBindings.avexHover.isPressed() && avexLegacyEnabled)
			action = LegacyAction.AvexHover;

		if (action != null)
			NetworkHandler.sendToServer(new MessageLegacyAction(LegacyActionConverter.intFromLegacyAction(action)));
		
		// --- Also apply toggling on client-side --- \\
		
		if (action == null)
			return;
				
		// Accelix toggle
		if (action == LegacyAction.Accelix && legaciesEnabled && accelixLegacyEnabled)
			accelixLegacy.toggle(player);
					
		// Fortem toggle
		if (action == LegacyAction.Fortem && legaciesEnabled && fortemLegacyEnabled)
			fortemLegacy.toggle(player);
				
		// Novis toggle
		if (action == LegacyAction.Novis && legaciesEnabled && novisLegacyEnabled)
			novisLegacy.toggle(player);
		
		// Pondus toggle
		if (action == LegacyAction.Pondus && legaciesEnabled && pondusLegacyEnabled)
			pondusLegacy.toggle(player);
		
		// Avex toggle
		if (action == LegacyAction.Avex && legaciesEnabled && avexLegacyEnabled)
			avexLegacy.toggle(player);
		
		if (action == LegacyAction.AvexHover && legaciesEnabled && avexLegacyEnabled)
			System.out.println("jeff");
		
		// Avex hover
		if (action == LegacyAction.AvexHover && legaciesEnabled && avexLegacyEnabled)
			avexLegacy.hover(player);
		
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
		
		
	}*/
	
	
}
