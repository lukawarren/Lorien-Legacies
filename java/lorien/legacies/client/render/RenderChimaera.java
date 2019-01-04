package lorien.legacies.client.render;

import lorien.legacies.entities.Chimaera.Chimaera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderChimaera extends Render<Chimaera> {

	Render<Entity> render;

	protected RenderChimaera(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(Chimaera entity) {
		return TextureManager.RESOURCE_LOCATION_EMPTY;
	}

	@Override
	public void doRender(Chimaera entity, double x, double y, double z, float entityYaw, float partialTicks) {
		render = (Render<Entity>) entity.morphHandler.getCurrent().createRenderFor(renderManager);
		this.render.doRender((Entity) entity, x, y, z, entityYaw, partialTicks);
	}

}
