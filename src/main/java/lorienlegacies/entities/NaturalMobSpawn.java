package lorienlegacies.entities;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LorienLegacies.MODID)
public class NaturalMobSpawn
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void spawnEntities(BiomeLoadingEvent e)
    {
        for (Biome biome : ForgeRegistries.BIOMES)
        {
            if (e.getCategory().equals(Biome.Category.NETHER) == false && e.getCategory().equals(Biome.Category.THEEND) == false)
            {
                e.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntities.mogadorianScout.get(),
                        7, 1, 1));
            }
        }
    }
}
