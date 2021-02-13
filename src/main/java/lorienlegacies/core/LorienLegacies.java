package lorienlegacies.core;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lorienlegacies.commands.ModCommands;
import lorienlegacies.config.ConfigLorienLegacies;
import lorienlegacies.gui.ModGUIs;
import lorienlegacies.items.ModItems;
import lorienlegacies.keybinds.ModKeybinds;
import lorienlegacies.proxy.ClientProxy;
import lorienlegacies.proxy.CommonProxy;
import lorienlegacies.proxy.ServerProxy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lorienlegacies")
public class LorienLegacies
{
	// Forge mod configuration and naming
    public static final String MODID = "lorienlegacies";
    public static final String NAME = "Lorien Legacies";
    public static final String VERSION = "1.0";

    // Logging
    public static final Logger logger = LogManager.getLogger(MODID);

    // Proxy
    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    // Creative tab
    public static final ItemGroup creativeTab = new ItemGroup(ItemGroup.GROUPS.length, MODID)
    {	
    	private Supplier<ItemStack> displayStack = () -> new ItemStack(ModItems.loricStone.get());
    	
		@Override
		public ItemStack createIcon() 
		{
			return displayStack.get();
		}
	};
    
    public LorienLegacies()
    {
    	// Register lifetime methods
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::Setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::EnqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::ProcessIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::OnClientSetup);
        
        // Forge event bus
        MinecraftForge.EVENT_BUS.register(this);
        
        // Items
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigLorienLegacies.COMMON_SPEC);
    }
    
    private void Setup(final FMLCommonSetupEvent event)
    {
    	logger.info("Initialising {} version {}", NAME, VERSION);
    
    	proxy.GetLegacyManager().RegisterLegacies(); // Register legacies
        
        proxy.Setup(event); // Proxy
    }
    
    private void OnClientSetup(final FMLClientSetupEvent event)
    {
    	proxy.GetLegacyManager().RegisterClientData(proxy.GetClientLegacyData()); // Client legacy data
    	ModKeybinds.RegisterKeybinds(); // Keybinds
    }
    
    private void EnqueueIMC(final InterModEnqueueEvent event) { } // For sending intermod communication
    private void ProcessIMC(final InterModProcessEvent event) { } // For receiving intermod communication
    
    @SubscribeEvent
	public void PlayerLoggedInEvent(PlayerLoggedInEvent event)
	{
    	// Server-side
    	if (!event.getEntity().world.isRemote) proxy.GetLegacyManager().RegisterPlayer((PlayerEntity)event.getEntity(), false);
	}
    
    @SubscribeEvent
    public void PlayerLoggedOutEvent(PlayerLoggedOutEvent event)
    {
    	// Server-side
    	if (!event.getEntity().world.isRemote) proxy.GetLegacyManager().DisconnectPlayer((PlayerEntity)event.getEntity());
    }
    
    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event)
    {
    	// Server-side - events fire twice per tick, so check phase
    	if (!event.world.isRemote && event.phase == Phase.START) proxy.GetLegacyManager().OnLegacyTick(event.world);
    }
    
    @SubscribeEvent
    public void OnClientTick(ClientTickEvent event)
    {
    	if (event.phase == Phase.END) ModGUIs.OnTick();
    }
    
    @SubscribeEvent
    public void RegisterCommandsEvent(RegisterCommandsEvent event)
	{
        ModCommands.RegisterCommands(event);
    }
}
