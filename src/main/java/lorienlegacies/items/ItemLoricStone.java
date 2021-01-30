package lorienlegacies.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy;
import lorienlegacies.legacies.PlayerLegacyData;
import lorienlegacies.network.NetworkHandler;
import lorienlegacies.network.mesages.MessageLegacyData;
import lorienlegacies.network.mesages.MessageLegacyLevel;
import lorienlegacies.world.WorldLegacySaveData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemLoricStone extends Item
{

	public ItemLoricStone()
	{
		super(new Item.Properties().maxStackSize(1).group(LorienLegacies.creativeTab));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{	
		PlayerLegacyData playerLegacyData = worldIn.isRemote ? LorienLegacies.proxy.GetClientLegacyData() :
											WorldLegacySaveData.get(playerIn.getServer()).GetPlayerData().get(playerIn.getUniqueID());
		
		// Get legacies that can be given
		List<String> possibleLegacies = new ArrayList<String>();
		possibleLegacies.addAll(LorienLegacies.proxy.GetLegacyManager().GetLegacies().keySet());
		possibleLegacies.removeIf(e -> playerLegacyData.legacies.containsKey(e) && playerLegacyData.legacies.get(e) > 0);
		
		// Check if there's still at least one to get
		if (possibleLegacies.size() == 0)
		{
			if (worldIn.isRemote == false) playerIn.sendMessage(new StringTextComponent("§cYou already have all leacies"), playerIn.getUniqueID());
			return ActionResult.resultFail(playerIn.getHeldItem(handIn));
		}
		
		// Check we're on the server
		if (worldIn.isRemote) return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
		
		// Give player a new legacy
		String legacy = possibleLegacies.stream().skip(new Random().nextInt(possibleLegacies.size())).findFirst().orElse(null);
		playerLegacyData.legacies.put(legacy, 1);
		
		// Send legacies to client - this will detoggle all legacies too...
		MessageLegacyData message = new MessageLegacyData();
		message.legacies = playerLegacyData.ToIntArray();
		NetworkHandler.sendToPlayer(message, (ServerPlayerEntity)playerIn);
		
		// ...so send some contrived excuse
		playerIn.sendMessage(new StringTextComponent("You now have §9" + legacy + "§f and rest your legacies temporarily..."), playerIn.getUniqueID());
		
		// Send legacy levels to client
		for (Legacy legacyToBeSent : LorienLegacies.proxy.GetLegacyManager().GetLegacies().values())
		{
			MessageLegacyLevel levelMessage = new MessageLegacyLevel();
			levelMessage.legacyName = legacyToBeSent.GetName();
			levelMessage.legacyLevel = legacyToBeSent.GetLegacyLevel(playerIn);
			NetworkHandler.sendToPlayer(levelMessage, (ServerPlayerEntity)playerIn);
		}
		
		return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
	}
}
