package lorienlegacies.entities;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderMogadorianScout extends MobRenderer<EntityMogadorianScout, ModelMogadorianScout>
{
	
	protected ResourceLocation mogadorianTextureOne = new ResourceLocation(LorienLegacies.MODID + ":textures/entity/mogadorian_scout_one.png");
	protected ResourceLocation mogadorianTextureTwo = new ResourceLocation(LorienLegacies.MODID + ":textures/entity/mogadorian_scout_two.png");
	
	protected RenderMogadorianScout(EntityRendererManager renderManager)
	{
		super(renderManager, new ModelMogadorianScout(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityMogadorianScout entity)
	{
		return entity.getEntityId() % 2 == 0 ? mogadorianTextureOne : mogadorianTextureTwo;
	}


}
