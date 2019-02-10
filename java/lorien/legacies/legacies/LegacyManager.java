package lorien.legacies.legacies;

import lorien.legacies.CustomPlayer;
import lorien.legacies.items.blessers.RegenerasBlesser;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.AvexLegacy;
import lorien.legacies.legacies.implementations.FortemLegacy;
import lorien.legacies.legacies.implementations.LumenLegacy;
import lorien.legacies.legacies.implementations.NovisLegacy;
import lorien.legacies.legacies.implementations.NoxenLegacy;
import lorien.legacies.legacies.implementations.PondusLegacy;
import lorien.legacies.legacies.implementations.RegenerasLegacy;
import lorien.legacies.legacies.implementations.SubmariLegacy;
import net.java.games.input.Keyboard;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) { // called every time player is updated
		
		if (event.player != null && event.player.getUniqueID() == player.getUniqueID() && legaciesEnabled) // If player is this instance's player
		{
			if (lumenLegacyEnabled)
				lumenLegacy.computeLegacyTick(event.player);
			
			if (noxenLegacyEnabled)
				noxenLegacy.computeLegacyTick(event.player);
			
			if (submariLegacyEnabled)
				submariLegacy.computeLegacyTick(event.player);
			
			if (accelixLegacyEnabled && accelixLegacy.toggled)
				accelixLegacy.computeLegacyTick(event.player);
			
			if (fortemLegacyEnabled && fortemLegacy.toggled)
				fortemLegacy.computeLegacyTick(event.player);
			
			if (novisLegacyEnabled && novisLegacy.toggled)
				novisLegacy.computeLegacyTick(event.player);
			
			if (pondusLegacyEnabled && pondusLegacy.toggled)
				pondusLegacy.computeLegacyTick(event.player);
			
			if (regenerasLegacyEnabled)
				regenerasLegacy.computeLegacyTick(event.player);
			
			if (avexLegacyEnabled)
				avexLegacy.computeLegacyTick(event.player);
			
		}
		
	}
	
	@SubscribeEvent
	public void WorldEvent(TickEvent.WorldTickEvent event)
	{
		
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
		
		/*
		if (org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_HOME))
		{
			EntityPlayerSP player = Minecraft.getMinecraft().player;
	    	//CustomPlayer customPlayer = new CustomPlayer();
			EntityPlayerSP customPlayer = new EntityPlayerSP(Minecraft.getMinecraft(), Minecraft.getMinecraft().player.world, Minecraft.getMinecraft().player.connection, Minecraft.getMinecraft().player.getStatFileWriter(), Minecraft.getMinecraft().player.getRecipeBook());
	    	
	    	customPlayer.capabilities = player.capabilities;
	    	customPlayer.bedLocation = player.bedLocation;
	    	customPlayer.capturedDrops = player.capturedDrops;
	    	customPlayer.fishEntity = player.fishEntity;
	    	customPlayer.inventory = player.inventory;
	    	customPlayer.inventoryContainer = player.inventoryContainer;
	    	customPlayer.movementInput = player.movementInput;
	    	customPlayer.openContainer = player.openContainer;
	    	customPlayer.swingingHand = player.swingingHand;
	    	
	    	customPlayer.posX = player.posX;
	    	customPlayer.posY = player.posY;
	    	customPlayer.posZ = player.posZ;
	    	
	    	customPlayer.rotationPitch = player.rotationPitch;
	    	customPlayer.rotationYaw = player.rotationYaw;
	    	
	    	customPlayer.motionX = player.motionX;
	    	customPlayer.motionY = player.motionY;
	    	customPlayer.motionZ = player.motionZ;
	    	
	    	customPlayer.chasingPosX = player.chasingPosX;
	    	customPlayer.chasingPosY = player.chasingPosY;
	    	customPlayer.chasingPosZ = player.chasingPosZ;
	    	
	    	customPlayer.setAir(player.getAir());
	    	customPlayer.setEntityId(player.getEntityId());
	    	customPlayer.setArrowCountInEntity(player.getArrowCountInEntity());
	    	customPlayer.setHealth(player.getHealth());
	    	
	    	//customPlayer.setGameType(gameType);;
	    	
	    	Minecraft.getMinecraft().player = customPlayer;
	    	
	    	customPlayer.isDead = true;
		}
		*/
		
	}

	
}
