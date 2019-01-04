package lorien.legacies.registry;

import java.lang.reflect.Field;
import java.util.Map;

import lorien.legacies.core.LorienLegacies;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityRegistryReflection {

	private EntityRegistryReflection() {

	}

	private static Map<Class<? extends Entity>, IRenderFactory> entityRenderers;
	private static Map<Class<? extends Entity>, Render> entityRenderersOld;
	private static RenderingRegistry renderingRegistry;
	private static Class<RenderingRegistry> class$renderingregistry = RenderingRegistry.class;

	public static Map<Class<? extends Entity>, IRenderFactory> getEntityRenderers() {
		return entityRenderers;
	}

	public static Map<Class<? extends Entity>, Render> getEntityRenderersOld() {
		return entityRenderersOld;
	}

	public static RenderingRegistry getRenderingRegistry() {
		return renderingRegistry;
	}

	public static void setup() {
		try {
			Field instance = class$renderingregistry.getDeclaredField("INSTANCE");
			instance.setAccessible(true);
			renderingRegistry = (RenderingRegistry) instance.get(null);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			LorienLegacies.LOGGER.catching(e);
		}
		if (renderingRegistry == null) {
			LorienLegacies.LOGGER.error(
					"Failed to get the field RenderingRegistry.INSTANCE trough reflection!! The Chimaera won't work this way!!");
			return;
		}

		try {
			Field entityRenderers = class$renderingregistry.getDeclaredField("entityRenderers");
			entityRenderers.setAccessible(true);
			EntityRegistryReflection.entityRenderers = (Map<Class<? extends Entity>, IRenderFactory>) entityRenderers
					.get(renderingRegistry);
			Field entityRenderersOld = class$renderingregistry.getDeclaredField("entityRenderersOld");
			entityRenderersOld.setAccessible(true);
			EntityRegistryReflection.entityRenderersOld = (Map<Class<? extends Entity>, Render>) entityRenderers
					.get(renderingRegistry);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			LorienLegacies.LOGGER.catching(e);
		}

	}

}
