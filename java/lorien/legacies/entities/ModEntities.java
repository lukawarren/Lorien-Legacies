package lorien.legacies.entities;

import lorien.legacies.client.render.RenderChimaera;
import lorien.legacies.core.LorienLegacies;
import lorien.legacies.entities.Chimaera.Chimaera;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(Chimaera.class, new RenderChimaera.Factory());
	}

	public static void register() {
		int entityID = 420;

		EntityRegistry.registerModEntity(new ResourceLocation(LorienLegacies.MODID + ":" + "chimaera"), Chimaera.class,
				"Chimaera", ++entityID, LorienLegacies.instance, 244, 1, false);
	}

}
