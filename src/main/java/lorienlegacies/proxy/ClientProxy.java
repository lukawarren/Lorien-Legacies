package lorienlegacies.proxy;

import lorienlegacies.gui.GuiAbility;
import lorienlegacies.gui.GuiLegacyBook;
import lorienlegacies.gui.GuiLegacyToggle;
import lorienlegacies.gui.GuiPondusDensity;
import lorienlegacies.gui.ModGUIs;
import lorienlegacies.legacies.PlayerLegacyData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy extends CommonProxy
{
	
    public PlayerLegacyData clientLegacies = new PlayerLegacyData();
	private ModGUIs modGUIs;
    
	@Override
    public void Setup(FMLCommonSetupEvent event) 
	{
        super.Setup(event);
        modGUIs = new ModGUIs();
        MinecraftForge.EVENT_BUS.register(this);
	}
    
    @Override
    public PlayerLegacyData GetClientLegacyData()
	{
		return clientLegacies;
	}
    
    @Override
    public PlayerEntity GetClientsidePlayer()
	{
		return Minecraft.getInstance().player;
	}
    
    @Override
    public void OpenPondusGUI(boolean up)
    {
    	modGUIs.guiPondus = new GuiPondusDensity(up);
    }
    
    @Override
    public void ClosePondusOverlay()
    {
    	modGUIs.ClosePondusOverlay();
    }
    
    @Override
    public void OpenLegacyBookGUI()
    {
    	modGUIs.OpenGUI(new GuiLegacyBook());
    }
    
    @Override
    public void OpenToggleGUI()
    {
    	modGUIs.OpenGUI(new GuiLegacyToggle());
    }
	
    @Override
    public void OpenAbilityGUI()
    {
    	modGUIs.OpenGUI(new GuiAbility());
    }
    
    @SubscribeEvent
    public void OnClientTick(ClientTickEvent event)
    {
    	if (event.phase == Phase.END) modGUIs.OnTick();
    }
}
