package lorienlegacies.proxy;

import lorienlegacies.legacies.LegacyManager;
import lorienlegacies.legacies.PlayerLegacyData;
import lorienlegacies.network.NetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber
public class CommonProxy 
{
	// Legacies
	public LegacyManager legacyManager = new LegacyManager();
	
	public void Setup(FMLCommonSetupEvent event)
	{
		NetworkHandler.init();
    }
	
	public void Setup(FMLClientSetupEvent event)
	{
		
	}
	
	public PlayerLegacyData GetClientLegacyData()
	{
		return null;
	}
	
	public LegacyManager GetLegacyManager()
	{
		return legacyManager;
	}
	
	public PlayerEntity GetClientsidePlayer()
	{
		return null;
	}
	
	public void OpenPondusGUI(boolean up) {}
	public void ClosePondusOverlay() {}
	public void OpenLegacyBookGUI() {}
	public void OpenToggleGUI() {}
	public void OpenAbilityGUI() {}
}
