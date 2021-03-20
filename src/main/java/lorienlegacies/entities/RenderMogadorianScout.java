package lorienlegacies.entities;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderMogadorianScout extends MobRenderer<EntityMogadorianScout, ModelMogadorianScout>
{
	protected ResourceLocation mogadorianTexture;
	
	protected RenderMogadorianScout(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelMogadorianScout(), 0.5f);
		mogadorianTexture = new ResourceLocation(LorienLegacies.MODID + ":textures/entity/mogadorian_scout.png");
	}
	
	@Override
	public ResourceLocation getEntityTexture(EntityMogadorianScout entity)
	{
		return mogadorianTexture;
	}
}
