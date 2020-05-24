package lorien.legacies.legacies;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.gui.LegacyGui;
import lorien.legacies.gui.StaminaGui;
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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LegacyManager {
	
	public EntityPlayer player;
	
	public boolean legaciesEnabled;
	
	// Used in levels GUI
	public List legacyList = new ArrayList<Legacy>();
	public List legacyEnabledList = new ArrayList<Boolean>();
	
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
	
	public static final float MAX_STAMINA = 100.0f;
	public float stamina;
	
	private StaminaGui staminaGui;
	
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
		
		// Add legacies to an array for levels purposes
		legacyList.add(lumenLegacy);
		legacyList.add(noxenLegacy);
		legacyList.add(submariLegacy);
		legacyList.add(accelixLegacy);
		legacyList.add(fortemLegacy);
		legacyList.add(novisLegacy);
		legacyList.add(pondusLegacy);
		legacyList.add(regenerasLegacy);
		legacyList.add(avexLegacy);
		legacyList.add(glacenLegacy);
		legacyList.add(telekinesis);
		
		staminaGui = new StaminaGui();
	}
	
	public void computeLegacyTick(boolean isServer)
	{
		if (lumenLegacyEnabled && lumenLegacy.getEnabledInConfig())lumenLegacy.computeLegacyTick(player);
		
		if (noxenLegacyEnabled && noxenLegacy.getEnabledInConfig()) noxenLegacy.computeLegacyTick(player);
				
		if (submariLegacyEnabled && submariLegacy.getEnabledInConfig()) submariLegacy.computeLegacyTick(player);
			
		if (accelixLegacyEnabled && accelixLegacy.getEnabledInConfig()) accelixLegacy.computeLegacyTick(player);
				
		if (fortemLegacyEnabled && fortemLegacy.getEnabledInConfig()) fortemLegacy.computeLegacyTick(player);
				
		if (novisLegacyEnabled && novisLegacy.getEnabledInConfig()) novisLegacy.computeLegacyTick(player);
				
		if (pondusLegacyEnabled && pondusLegacy.getEnabledInConfig()) pondusLegacy.computeLegacyTick(player);
				
		if (regenerasLegacyEnabled && regenerasLegacy.getEnabledInConfig()) regenerasLegacy.computeLegacyTick(player);
				
		if (avexLegacyEnabled && avexLegacy.getEnabledInConfig()) avexLegacy.computeLegacyTick(player);
		
		if (glacenLegacyEnabled && glacenLegacy.getEnabledInConfig()) glacenLegacy.computeLegacyTick(player);
		
		if (legaciesEnabled && telekinesis.getEnabledInConfig()) telekinesis.computeLegacyTick(player);
		
		legacyEnabledList.clear();
		legacyEnabledList.add(lumenLegacyEnabled);
		legacyEnabledList.add(noxenLegacyEnabled);
		legacyEnabledList.add(submariLegacyEnabled);
		legacyEnabledList.add(accelixLegacyEnabled);
		legacyEnabledList.add(fortemLegacyEnabled);
		legacyEnabledList.add(novisLegacyEnabled);
		legacyEnabledList.add(pondusLegacyEnabled);
		legacyEnabledList.add(regenerasLegacyEnabled);
		legacyEnabledList.add(avexLegacyEnabled);
		legacyEnabledList.add(glacenLegacyEnabled);
		legacyEnabledList.add(legaciesEnabled);
	}
	
	public void computeStaminaTick()
	{
		// Untoggle each legacy if no stamina and legacy requires stamina
		if (stamina <= 0)
		{
			for (int i = 0; i < legacyList.size(); ++i)
			{
				if (((Legacy) legacyList.get(i)).getStaminaPerTick() > 0 && ((Legacy) legacyList.get(i)).toggled)
				{
					((Legacy) legacyList.get(i)).toggled = false;
				}
			}
		}
		
		// Give the player some stamina every tick
		stamina += 7;
		
		if (player.isCreative() == false && player.isSpectator() == false)
		{
			// Go through all legacies, and apply stamina and XP logic
			for (int i = 0; i < legacyList.size(); i++)
			{
				Legacy l =  (Legacy) legacyList.get(i);
				if (l.getEnabledInConfig()) stamina -= l.getStaminaPerTick();
				l.addXPForPlayer((int)l.getStaminaPerTick(), this, false);
			}
		}
		else if (player.isCreative()) // If creative just do XP stuff
		{
			for (int i = 0; i < legacyList.size(); i++)
			{
				Legacy l =  (Legacy) legacyList.get(i);
				l.addXPForPlayer((int)l.getStaminaPerTick(), this, false);
			}
		}
		
		if (stamina > MAX_STAMINA) stamina = MAX_STAMINA;
		if (stamina < 0) stamina = 0;
		
		staminaGui.percentage = (float)stamina / (float)MAX_STAMINA;
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{				
		if (event.player == null || player == null)
			return;

		// Try getting the player's game mode, which requires them to be connected to server. 
		// If it fails, we need to wait. Best not crash too whilst we're at it!
		try { player.isCreative(); }
		catch (Exception e) { return; }
		
		if (event.player.world.isRemote && legaciesEnabled) // Client
		{
			computeStaminaTick();
			if (stamina > 0) computeLegacyTick(false);
		}
		else if (legaciesEnabled) // Server
		{
			computeStaminaTick();
			if (stamina > 0) computeLegacyTick(true);
		}
	}
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event)
	{
		onKeyClient();
		
		if (player == null)
			return;

		telekinesis.computeLegacyTick(player);
	}
	
	// Render stamina overlay
	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		if (legaciesEnabled)
			staminaGui.render(stamina, MAX_STAMINA, event);
	}
	
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
		else if (KeyBindings.activateTelekinesis.isPressed() && legaciesEnabled)
			action = LegacyAction.Telekinesis;
		else if (KeyBindings.launchTelekinesis.isPressed() && legaciesEnabled)
			action = LegacyAction.TelekinesisLaunch;
		
		if (action != null)
			NetworkHandler.sendToServer(new MessageLegacyAction(LegacyActionConverter.intFromLegacyAction(action)));
		
		// --- Also apply toggling and stuff on client-side --- \\
		
		if (action == null)
			return;
				
		// Accelix toggle
		if (action == LegacyAction.Accelix && legaciesEnabled && accelixLegacyEnabled && accelixLegacy.getEnabledInConfig())
			accelixLegacy.toggle(player);
					
		// Fortem toggle
		if (action == LegacyAction.Fortem && legaciesEnabled && fortemLegacyEnabled && fortemLegacy.getEnabledInConfig())
			fortemLegacy.toggle(player);
				
		// Novis toggle
		if (action == LegacyAction.Novis && legaciesEnabled && novisLegacyEnabled && novisLegacy.getEnabledInConfig())
			novisLegacy.toggle(player);
		
		// Pondus toggle
		if (action == LegacyAction.Pondus && legaciesEnabled && pondusLegacyEnabled && pondusLegacy.getEnabledInConfig())
			pondusLegacy.toggle(player);
		
		// Avex toggle
		if (action == LegacyAction.Avex && legaciesEnabled && avexLegacyEnabled && avexLegacy.getEnabledInConfig())
			avexLegacy.toggle(player);

		if (action == LegacyAction.Telekinesis && legaciesEnabled && telekinesis.getEnabledInConfig())
		{
			telekinesis.activated = !telekinesis.activated;
			telekinesis.toggle(player);
		}
		
		if (action == LegacyAction.TelekinesisLaunch && legaciesEnabled && telekinesis.getEnabledInConfig()) telekinesis.launch(player);
		
	}
	
	// Beause the garbage collector is trash (geddit?)
	public void destroy()
	{ 
		try { finalize(); } catch (Throwable e) { } 
	}
	
}
