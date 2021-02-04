package lorienlegacies.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {
	
	//in order presented by ModItemTier
	LORICMETAL(3, 3072, 2.5F, 5.5F, 3F, () -> Ingredient.fromItems(Items.IRON_INGOT)); //Iron Ingot is placeholder for now
	
	//ITEMNAME(harvest level, durability points, efficiency, damage, enchantability, () -> Ingredient.fromItems(repair item name));
	
	private final int harvestLevel;

	private final int maxUses;

	private final float efficiency;

	private final float attackDamage;

	private final int enchantability;

	private final LazyValue<Ingredient> repairMaterial;

	ModItemTier(final int harvestLevel, final int maxUses, final float efficiency, final float attackDamage, final int enchantability, final Supplier<Ingredient> repairMaterial) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairMaterial = new LazyValue<>(repairMaterial);
	}

	@Override
	public int getMaxUses() {
		return maxUses;
	}

	@Override
	public float getEfficiency() {
		return efficiency;
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairMaterial.getValue();
	}
}
