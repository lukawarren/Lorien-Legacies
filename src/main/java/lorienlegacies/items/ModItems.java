package lorienlegacies.items;

import lorienlegacies.core.LorienLegacies;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
	// Register
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LorienLegacies.MODID);
	
	// Items
	public static final RegistryObject<Item> loricStone = ITEMS.register("loric_stone", () -> new ItemLoricStone());
}
