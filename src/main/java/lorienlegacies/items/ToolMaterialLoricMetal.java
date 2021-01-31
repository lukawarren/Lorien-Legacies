package lorienlegacies.items;

import lorienlegacies.core.LorienLegacies;

import net.minecraft.recipe.Ingredient;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

public class ToolMaterialLoricMetal implements ToolMaterial {
	
	@Override
    public int getMaxUses() {
        return 3072;
    }
 
    @Override
    public float getEfficiency() {
        return 4.0F;
    }
 
    @Override
    public float getDamageVsEntity() {
        return 6.0F;
    }
 
    @Override
    public int getHarvestLevel() {
        return 1;
    }
 
    @Override
    public int getEnchantability() {
        return 13;
    }
 
 	/*
    @Override
    public Ingredient getRepairMaterial() {
        return (Ingredient) this.repairmaterial;
    }
	*/
	
}
