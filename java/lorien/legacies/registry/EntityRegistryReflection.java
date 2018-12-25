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

	private static Map<Class<? extends Entity>, IRenderFactory<? extends Entity>> entityRenderers;
	private static Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderersOld;
	private static RenderingRegistry renderingRegistry;
	private static Class<RenderingRegistry> class$renderingregistry = RenderingRegistry.class;

	public static Map<Class<? extends Entity>, IRenderFactory<? extends Entity>> getEntityRenderers() {
		return entityRenderers;
	}

	public static Map<Class<? extends Entity>, Render<? extends Entity>> getEntityRenderersOld() {
		return entityRenderersOld;
	}

	public static RenderingRegistry getRenderingRegistry() {
		return renderingRegistry;
	}

	public static Class<RenderingRegistry> getClass$renderingregistry() {
		return class$renderingregistry;
	}

	public static void setup() {
		try {
			Field instance = class$renderingregistry.getField("INSTANCE");
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
			Field entityRenderers = class$renderingregistry.getField("entityRenderers");
			entityRenderers.setAccessible(true);
			EntityRegistryReflection.entityRenderers = (Map<Class<? extends Entity>, IRenderFactory<? extends Entity>>) entityRenderers
					.get(renderingRegistry);
			Field entityRenderersOld = class$renderingregistry.getField("entityRenderersOld");
			entityRenderersOld.setAccessible(true);
			EntityRegistryReflection.entityRenderersOld = (Map<Class<? extends Entity>, Render<? extends Entity>>) entityRenderers
					.get(entityRenderersOld);
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
			LorienLegacies.LOGGER.catching(e);
		}

	}

}
