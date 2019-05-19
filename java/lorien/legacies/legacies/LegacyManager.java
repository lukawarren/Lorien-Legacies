package lorien.legacies.legacies;

import org.lwjgl.input.Keyboard;

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
	
	public static boolean legaciesEnabled;
	
	public LumenLegacy lumenLegacy;
	public static boolean lumenLegacyEnabled;
	
	public NoxenLegacy noxenLegacy;
	public static boolean noxenLegacyEnabled;
	
	public SubmariLegacy submariLegacy;
	public static boolean submariLegacyEnabled;
	
	public NovisLegacy novisLegacy;
	public static boolean novisLegacyEnabled;
	
	public AccelixLegacy accelixLegacy;
	public static boolean accelixLegacyEnabled;
	
	public FortemLegacy fortemLegacy;
	public static boolean fortemLegacyEnabled;
	
	public PondusLegacy pondusLegacy;
	public static boolean pondusLegacyEnabled;
	
	public RegenerasLegacy regenerasLegacy;
	public static boolean regenerasLegacyEnabled;
	
	public AvexLegacy avexLegacy;
	public static boolean avexLegacyEnabled;
	
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
	
	private static final float DISTANCE = 10f;
	Entity previousEntity = null;
	Entity pointedEntity = null;
	
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
		
	}
	
	private boolean previousWaterDecision = false;
	
	// So you can't just spam it and crash the server (trust me, it was hilarious)
	private boolean lumenFireballShot = false;
	
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
	}

	
}
