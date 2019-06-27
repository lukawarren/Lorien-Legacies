package lorien.legacies.gui;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class LegacyInventory extends InventoryPlayer
{

	public LegacyInventory(EntityPlayer player)
	{
		super(player);
		
		if (armorInventory.get(0) == null)
			return;
		
		NonNullList<ItemStack> newArmourStack = NonNullList.create();
		
		for (ItemStack s : this.armorInventory)
			newArmourStack.add(s);
		
		newArmourStack.add(new ItemStack(Block.getBlockById(1)));
		
		//this.armorInventory.clear();
		
		armorInventory.add(new ItemStack(new BlockStone()));
		armorInventory.add(new ItemStack(new BlockStone()));

		armorInventory.add(new ItemStack(new BlockStone()));

		armorInventory.add(new ItemStack(new BlockStone()));

		armorInventory.add(new ItemStack(new BlockStone()));

				
	}

	

}
