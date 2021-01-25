package lorienlegacies.proxy;

import lorienlegacies.legacies.PlayerLegacyData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy extends CommonProxy
{
	
    public PlayerLegacyData clientLegacies = new PlayerLegacyData();
	
	@Override
    public void Setup(FMLCommonSetupEvent event) 
	{
        super.Setup(event);
	}
    
    @Override
    public PlayerLegacyData GetClientLegacyData()
	{
		return clientLegacies;
	}
    
}
