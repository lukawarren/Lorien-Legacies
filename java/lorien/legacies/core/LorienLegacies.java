package lorien.legacies.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lorien.legacies.entities.Chimaera.Chimaera;
import lorien.legacies.items.ModItems;
import lorien.legacies.legacies.KeyBindings;
import lorien.legacies.legacies.KeyInputHandler;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.network.MessageMorphChimaera;
import lorien.legacies.proxy.CommonProxy;
import lorien.legacies.worldgen.OreGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = LorienLegacies.MODID, name = LorienLegacies.NAME, version = LorienLegacies.VERSION)

@Mod.EventBusSubscriber
public class LorienLegacies {

	public static final String MODID = "lorienlegacies";
	public static final String NAME = "Lorien Legacies";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "lorien.legacies.proxy.ClientProxy", serverSide = "mcjty.modtut.proxy.ServerProxy")
	public static CommonProxy proxy;

	// Networking channel
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@Mod.Instance
	public static LorienLegacies instance;

	public static final Logger LOGGER = LogManager.getLogger(NAME);

	// Legacies
	public static List<LegacyManager> legacyManagers = new ArrayList<LegacyManager>(); // List of legacy managers - one
																						// for each player.

	// Called when entity joins world. If it is a player, we need to load the
	// legacies
	// TODO: replace below method with "on player log in" event - it's only like
	// this so I can kill myself to reload legacies
	@SubscribeEvent
	public void EntityJoinWorldEvent(EntityJoinWorldEvent  event)
	{
		if (event.getEntity() instanceof EntityPlayer == false)
			return;
		
		// Delete old legacies
		// Doing it this way in case modifying the list as I iterate over it causes weirdness
		int size = legacyManagers.size();
		for (int i = 0; i < size; i++)
			legacyManagers.remove(i);
		
		// Load new legacies
		setupLegaciesForPlayer((EntityPlayer) event.getEntity());
	}
	

	public void setupLegaciesForPlayer(EntityPlayer player)
	{
		// In order to fix Pondus
		player.setNoGravity(false);
		
		// The LegacyManager class will handle all the legacies, and the keyboard events
		// associated with them
		LegacyManager playerLegacyManager = new LegacyManager(player);
		legacyManagers.add(playerLegacyManager);

		// In order to fix Pondus
		player.setNoGravity(false);
		
		// Load legacies for the player
		LegacyLoader.loadLegacies(playerLegacyManager, false);
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
		NETWORK.registerMessage(MessageMorphChimaera.Handle.class, MessageMorphChimaera.class, 0, Side.CLIENT);
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

}
