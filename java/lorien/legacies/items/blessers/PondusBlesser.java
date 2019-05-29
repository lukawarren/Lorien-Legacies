package lorien.legacies.items.blessers;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.legacies.LegacyLoader;
import lorien.legacies.legacies.LegacyManager;
import lorien.legacies.legacies.implementations.AccelixLegacy;
import lorien.legacies.legacies.implementations.PondusLegacy;
import lorien.legacies.legacies.worldSave.LegacyWorldSaveData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PondusBlesser extends Blesser {
	
	private static String UNLOCALIZED_NAME = "pondusblesser";
	
	public PondusBlesser()
	{
		setUnlocalizedName(LorienLegacies.MODID + "." + UNLOCALIZED_NAME);
		setRegistryName(new ResourceLocation(LorienLegacies.MODID, UNLOCALIZED_NAME));
		setCreativeTab(LorienLegacies.instance.blessersTab);
		setMaxStackSize(1);
		setMaxDamage(1);
	}
	
	
	@Override
	protected void handleClient(EntityPlayer player)
	{
		LorienLegacies.clientLegacyManager.legaciesEnabled = true;
		LorienLegacies.clientLegacyManager.pondusLegacyEnabled = true;
		new PondusLegacy().blessedMessage(player);
	}


	@Override
	protected void handleServer(LegacyManager l) 
	{
		l.legaciesEnabled = true;
		l.pondusLegacyEnabled = true;
	}
	
}
