package lorien.legacies.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lorien.legacies.commands.CommandLegacies;
import lorien.legacies.commands.CommandLegacyLevels;
import lorien.legacies.commands.CommandLegacyXp;
import lorien.legacies.config.ConfigManager;
import lorien.legacies.config.LorienLegaciesConfig;
import lorien.legacies.items.ModItems;
import lorien.legacies.legacies.KeyBindings;
import lorien.legacies.legacies.KeyInputHandler;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import lorien.legacies.proxy.CommonProxy;
import lorien.legacies.worldgen.OreGen;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(modid = LorienLegacies.MODID, name = LorienLegacies.NAME, version = LorienLegacies.VERSION)

@Mod.EventBusSubscriber
public class LorienLegacies {

	public static final String MODID = "lorienlegacies";
	public static final String NAME = "Lorien Legacies";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "lorien.legacies.proxy.ClientProxy", serverSide = "lorien.legacies.proxy.ServerProxy")
	public static CommonProxy proxy;

	// Networking channel
	//public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@Mod.Instance
	public static LorienLegacies instance;

	public static final Logger LOGGER = LogManager.getLogger(NAME);

	// Legacies
	public HashMap<UUID, LegacyManager> legacyManagers = new HashMap<UUID, LegacyManager>(); // Server-side only
	public LegacyManager clientLegacyManager; // For client-side only
	
	// Config
	public ConfigManager configManager;
	
	@SubscribeEvent
	public void PlayerLoggedInEvent(PlayerLoggedInEvent event)
	{				
		if (event.player.world.isRemote)
			loadLegaciesForClient(event);
		else
			loadLegaciesForServer(event); // Called first
	}
	
	@SubscribeEvent
	public void PlayerRespawnEvent(PlayerRespawnEvent event)
	{		
		// Get rid of old instances
		clientLegacyManager.legaciesEnabled = false;
		clientLegacyManager.destroy();
		System.gc(); // Yes, I am paranoid about multiple instances, and yes, I do hate Java's garbage collection.
		clientLegacyManager = null;
		
		if (legacyManagers.containsKey(event.player.getUniqueID()))
		{
			LegacyManager l = legacyManagers.get(event.player.getUniqueID());
			l.legaciesEnabled = false;
			l.destroy();
			// delete l; // Oh wait, I forgot... Java sucks!
		}
		legacyManagers.remove(event.player.getUniqueID());
		
		if (event.player.world.isRemote)
			loadLegaciesForClient(event);
		else
			loadLegaciesForServer(event); // Called first
	}
	
	@SubscribeEvent
	public void PlayerLoggedOutEvent(PlayerLoggedOutEvent event)
	{		
		// Write changes to save
		if (legacyManagers.containsKey(event.player.getUniqueID()))
		{
			LegacyLoader.saveLegaciesToSave(legacyManagers.get(event.player.getUniqueID()), LegacyWorldSaveData.get(legacyManagers.get(event.player.getUniqueID()).player.world));
		}
		
		// Get rid of old instances
		clientLegacyManager = null;
		legacyManagers.remove(event.player.getUniqueID());
	}
	
	private void loadLegaciesForClient(PlayerEvent event) // DOES NOT ACTUALLY GET CALLED!
	{
		clientLegacyManager = new LegacyManager((EntityPlayer) event.player); // No need for data, we get that from MessageLegacyData
	}

	private void loadLegaciesForServer(PlayerEvent event)
	{	
		EntityPlayer player = (EntityPlayer) event.player;
		//player.inventory = new LegacyInventory(player);
		
		// In order to fix Pondus
		player.setNoGravity(false);
				
		legacyManagers.put(player.getUniqueID(), new LegacyManager(player));

		// In order to fix Pondus
		player.setNoGravity(false);
				
		// Load legacies for the player
		LegacyLoader.generateLegacies(legacyManagers.get(player.getUniqueID()), false);
		
		// Send them the config
		configManager.updateConfigForClient(event.player);
	}

	// Equivalent of System.Out.Println(), except with nice formatting
	public static void print(String output) {
		LOGGER.log(Level.INFO, output);
	}

	// Creative tab
	public CreativeTabs lorienTab = new CreativeTabs("lorienlegacies") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.loraliteCrystal);
		}
	};

	public CreativeTabs blessersTab = new CreativeTabs("lorienblessers") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.legacyBlesser);
		}
	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// init packets
		//NETWORK.registerMessage(MessageMorphChimaera.Handle.class, MessageMorphChimaera.class, 0, Side.CLIENT);
		
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
		
		proxy.init(e);
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		
		configManager = new ConfigManager();
		
		if (e.getSide() == Side.CLIENT) KeyBindings.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	@Mod.EventHandler
	public void FMLServerStartingEvent(FMLServerStartingEvent event)
	{	
		event.registerServerCommand(new CommandLegacies());
		event.registerServerCommand(new CommandLegacyLevels());
		event.registerServerCommand(new CommandLegacyXp());
	}
	
	
}
