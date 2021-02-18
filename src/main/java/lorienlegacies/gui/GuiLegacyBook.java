package lorienlegacies.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;

import com.mojang.blaze3d.matrix.MatrixStack;

import lorienlegacies.core.LorienLegacies;
import lorienlegacies.legacies.Legacy.LegacyLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiLegacyBook extends Screen
{	
	private static final ResourceLocation BOOK_TEXTURE = new ResourceLocation("minecraft:textures/gui/book.png");
	
	class GuiButtonPage extends Button
	{

		private final boolean previousPage;
		
		public GuiButtonPage(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, boolean previousPage)
		{
			super(x, y, width, height, title, pressedAction);
			this.previousPage = previousPage;
		}
		
		@Override
		public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
		{
			Minecraft.getInstance().getTextureManager().bindTexture(BOOK_TEXTURE);
			
			int u = 0; int v = 192;
			if (this.isHovered()) u += 23;
			if (previousPage) v += 13;
			
			this.blit(matrixStack, x, y, u, v, 23, 13);
			
			if (this.isHovered())
			{
				this.renderToolTip(matrixStack, mouseX, mouseY);
			}
		}
		
	}
	
	class Page
	{
		public String title;
		public String[] text;
		
		public Page(String title, String[] text)
		{
			this.title = title;
			this.text = text;
		}
	}
	
	private GuiButtonPage buttonPrevPage;
    private GuiButtonPage buttonNextPage;
    private int page = 0;
    
    private List<Page> pageList = new ArrayList<Page>();
    
    /*
     * Uses WordUtils. Too bad!
     */
	@SuppressWarnings("deprecation")
	public GuiLegacyBook()
	{
		super(new StringTextComponent(""));
		
		// First page
		int nLegacies = 0;
		for (Integer entry : LorienLegacies.proxy.GetClientLegacyData().legacies.values())
			if (entry.intValue() > 0) nLegacies++;
		pageList.add(new Page("§1§n§lLorien Guide", new String[]{"This book has been", "given to your by your", "Cepan. It contains", "all your current", "legacies.", 
				"", "Perhaps in time", "this list will", "grow...", "", "§oAn FAQ is at the back.", "", "You have §1§l" + nLegacies + "§0 legacies"}));
		
		// Subsequent legacy pages
		for (Map.Entry<String, Integer> entry: LorienLegacies.proxy.GetClientLegacyData().legacies.entrySet())
		{
			if (entry.getValue() == 0) continue; // Check we have the legacy
			
			List<String> text = new ArrayList<>();
			
			// Description
			String[] wrappedDescription = WordUtils.wrap(LorienLegacies.proxy.GetLegacyManager().GetLegacies().get(entry.getKey()).GetBookDescription(), 20).split("\n");
			for (String line : wrappedDescription) text.add(line.replace('\r', ' '));
			
			// Level
			int level = LorienLegacies.proxy.GetClientLegacyData().clientLegacyLevels.get(entry.getKey());
			for (int i = text.size(); i < 12; ++i) text.add("");
			text.add("You are level §1§l" + level);
			
			pageList.add(new Page("§1§n§l" + entry.getKey(), text.toArray(new String[0])));
		}
		
		// FAQ
		pageList.add(new Page("§1§n§lFAQ", new String[] {"The following pages", "serve as an FAQ", "for the mod.", "", "§nQuestions:", "", "How do I get legacies?", "How do I use them?", "What of abilities?", "Are there keybinds?"}));
		pageList.add(new Page("§1§nHow do I get legacies?", new String[] {"Loric Ore can be", "mined to make a §1Loric", "§1Stone§0. It is said", "the energy it emits", "can prompt new", "legacies to emerge."}));
		pageList.add(new Page("§1§nHow do I use them?", new String[] {"Some legacies are", "always active, like", "Lumen. Others, like", "Avex, can be toggled", "via the toggle menu.", "", "The default keybinding", "for this menu is", "left alt."}));
		pageList.add(new Page("§1§nWhat of abilities?", new String[] {"Some legacies have", "abilities, like Pondus.", "", "The default keybinding", "for this is Z."}));
		pageList.add(new Page("§1§nAre there keybinds?", new String[] {"See the previous two", "pages for general", "purpose keybinds.", "", "For convenience,", "the last used legacy", "can be toggled bu", "holding down `", "(the key above tab)."}));
	}
	
	@Override
	protected void init()
	{
		// Make buttons
		int bookX = (this.width - 192) / 2;
		buttonPrevPage = new GuiButtonPage(bookX + 38, 2 + 154, 23, 13, new StringTextComponent("Previous page"), (e) ->
		{
			page--;
			if (page < 0) page = 0;
			
			// Play sound
			Minecraft.getInstance().player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
			
		}, true);
		buttonNextPage = new GuiButtonPage(bookX + 120, 2 + 154, 23, 13, new StringTextComponent("Next page"), (e) ->
		{
			page++;
			if (page >= pageList.size()) page = pageList.size()-1;
			
			// Play sound
			Minecraft.getInstance().player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
		}, false);
		
		this.addButton(buttonPrevPage);
		this.addButton(buttonNextPage);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		// Draw background
		super.renderBackground(matrixStack);
		
		// Draw book
		int bookX = (this.width - 192) / 2;
		int bookY = 2;
		Minecraft.getInstance().getTextureManager().bindTexture(BOOK_TEXTURE);
		super.blit(matrixStack, bookX, bookY, 0, 0, 192, 192);
		
		// Draw page
		font.drawString(matrixStack, pageList.get(page).title, bookX + 40, 17, 0);
		for (int i = 0; i < pageList.get(page).text.length; ++i)
			font.drawString(matrixStack, pageList.get(page).text[i], bookX + 40, 17 + 15 + 9*i, 0);
		
		// Draw other stuff
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean isPauseScreen() { return true; }
	
}