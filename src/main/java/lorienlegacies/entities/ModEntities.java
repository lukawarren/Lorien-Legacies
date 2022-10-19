package lorienlegacies.entities;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;

public class ModEntities
{
	// Register
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, LorienLegacies.MODID);
		
	// Mogodorian Scout
	public static final RegistryObject<EntityType<EntityMogadorianScout>> mogadorianScout = ENTITIES.register("mogadorian_scout", () ->
			EntityType.Builder.create(EntityMogadorianScout::new, EntityClassification.CREATURE)
			.size(EntityType.VILLAGER.getSize().width, EntityType.VILLAGER.getSize().height)
			.build(new ResourceLocation(LorienLegacies.MODID, "mogadorian_scout").toString()));
	
	public static void RegisterEntites(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{	
			GlobalEntityTypeAttributes.put(mogadorianScout.get(), EntityMogadorianScout.getAttributes());	
		});
	}
	
	public static void RegisterRenderers(FMLClientSetupEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(mogadorianScout.get(), RenderMogadorianScout::new);
	}


}
