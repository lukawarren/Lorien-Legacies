package lorien.legacies.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lorien.legacies.commands.CommandLegacies;
import lorien.legacies.items.ModItems;
import lorien.legacies.legacies.KeyBindings;
import lorien.legacies.legacies.KeyInputHandler;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.proxy.CommonProxy;
import lorien.legacies.worldgen.OreGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
	public static List<LegacyManager> legacyManagers = new ArrayList<LegacyManager>(); // List of legacy managers - one
																						// for each player. For server-side only
	public static LegacyManager clientLegacyManager; // For client-side only

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
		clientLegacyManager = null;
		
		List<Integer> offendingLegacyManagers = new ArrayList<>();
		for (int i = 0; i < legacyManagers.size(); i++)
		{
			if (legacyManagers.get(i).player.getUniqueID().equals(event.player.getUniqueID()))
				offendingLegacyManagers.add(i);
		}
		
		
		for (int i : offendingLegacyManagers)
			legacyManagers.remove(i);
		
		System.out.println(offendingLegacyManagers.size());
		
		if (event.player.world.isRemote)
			loadLegaciesForClient(event);
		else
			loadLegaciesForServer(event); // Called first
	}
	
	@SubscribeEvent
	public void PlayerLoggedOutEvent(PlayerLoggedOutEvent event)
	{		
		boolean managerAlreadyExists = false;
		int index = 0;
		for (int i = 0; i < legacyManagers.size(); i++)
		{
			if (legacyManagers.get(i).player.getUniqueID().equals(event.player.getUniqueID()))
			{
				index = i;
			}
		}
		
		if (managerAlreadyExists)
			legacyManagers.remove(index);
	}
	
	private void loadLegaciesForClient(PlayerEvent event)
	{
		clientLegacyManager = new LegacyManager((EntityPlayer) event.player);
		
		
		//((EntityPlayer) event.player).inventory.armorInventory.add(new ItemStack(new ItemRedstone()));
	}

	private void loadLegaciesForServer(PlayerEvent event)
	{	
		EntityPlayer player = (EntityPlayer) event.player;
		//player.inventory = new LegacyInventory(player);
		
		// In order to fix Pondus
		player.setNoGravity(false);
				
		LegacyManager playerLegacyManager = new LegacyManager(player);
		legacyManagers.add(playerLegacyManager);

		// In order to fix Pondus
		player.setNoGravity(false);
				
		// Load legacies for the player
		LegacyLoader.generateLegacies(playerLegacyManager, false);
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
		KeyBindings.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	@Mod.EventHandler
	public void FMLServerStartingEvent(FMLServerStartingEvent event)
	{	
		event.registerServerCommand(new CommandLegacies());
	}
	
	
}
