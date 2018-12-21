package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LegacyBlesser extends Item {
	
	public static final String UNLOCALIZED_NAME = "legacyblesser";
	
	public LegacyBlesser()
	{
		setRegistryName(UNLOCALIZED_NAME);
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setCreativeTab(LorienLegacies.instance.blessersTab);
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		// Give player using it legacy by finding LegacyManager with player UUID that corresponds to player that is using the item's UUID
		for (LegacyManager l : LorienLegacies.legacyManagers)
		{
			if (l.player.getUniqueID() == player.getUniqueID() && player.world.isRemote == false)
			{
				LegacyLoader.loadLegacies(l, true);
			}
		}
		
        return EnumActionResult.PASS;
    }
	
}
