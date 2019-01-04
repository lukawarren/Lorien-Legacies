package lorien.legacies.items;

import lorien.legacies.items.blessers.*;
import lorien.legacies.items.tools.*;
import lorien.legacies.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	@GameRegistry.ObjectHolder("lorienlegacies:loralitecrystal")
	public static LoraliteCrystal loraliteCrystal = new LoraliteCrystal();
	
	// Blessers
	@GameRegistry.ObjectHolder("lorienlegacies:legacyblesser")
	public static LegacyBlesser legacyBlesser = new LegacyBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:accelixblesser")
	public static AccelixBlesser accelixBlesser = new AccelixBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:fortemblesser")
	public static FortemBlesser fortemBlesser = new FortemBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:lumenbblesser")
	public static LumenBlesser lumenBlesser = new LumenBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:novisblesser")
	public static NovisBlesser novisBlesser = new NovisBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:noxenblesser")
	public static NoxenBlesser noxenBlesser = new NoxenBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:pondusblesser")
	public static PondusBlesser pondusBlesser = new PondusBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:regenerasblesser")
	public static RegenerasBlesser regenerasBlesser = new RegenerasBlesser();
	@GameRegistry.ObjectHolder("lorienlegacies:submariblesser")
	public static SubmariBlesser submariBlesser = new SubmariBlesser();
	
	// Tools
	public static final ToolMaterial loraliteMaterial = EnumHelper.addToolMaterial("loralitematerial", 3, 2048, 16.0f, 5.0f, 30);
	public static final ToolMaterial loraliteMaterialDagger = EnumHelper.addToolMaterial("loralitematerialdagger", 3, 2048, 16.0f, 3.0f, 30);
	
	@GameRegistry.ObjectHolder("lorienlegacies:loralitepickaxe")
	public static LoralitePickaxe loralitepickaxe = new LoralitePickaxe(loraliteMaterial);
	
	@GameRegistry.ObjectHolder("lorienlegacies:loraliteaxe")
	public static LoraliteAxe loraliteaxe = new LoraliteAxe(loraliteMaterial);
	
	@GameRegistry.ObjectHolder("lorienlegacies:loraliteshovel")
	public static LoraliteShovel loralitespade = new LoraliteShovel(loraliteMaterial);
	
	@GameRegistry.ObjectHolder("lorienlegacies:loralitesword")
	public static LoraliteSword loralitesword = new LoraliteSword(loraliteMaterial);
	
	@GameRegistry.ObjectHolder("lorienlegacies:loralitedagger")
	public static LoraliteDagger loralitedagger = new LoraliteDagger(loraliteMaterialDagger);
	
	@GameRegistry.ObjectHolder("lorienlegacies:loralitehoe")
	public static LoraliteHoe loralitehoe = new LoraliteHoe(loraliteMaterial);
	
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(loraliteCrystal);
		
		event.getRegistry().register(legacyBlesser);
		event.getRegistry().register(accelixBlesser);
		event.getRegistry().register(fortemBlesser);
		event.getRegistry().register(lumenBlesser);
		event.getRegistry().register(novisBlesser);
		event.getRegistry().register(noxenBlesser);
		event.getRegistry().register(pondusBlesser);
		event.getRegistry().register(submariBlesser);
		event.getRegistry().register(regenerasBlesser);
		
		event.getRegistry().register(loralitepickaxe);
		event.getRegistry().register(loraliteaxe);
		event.getRegistry().register(loralitespade);
		event.getRegistry().register(loralitesword);
		event.getRegistry().register(loralitedagger);
		event.getRegistry().register(loralitehoe);
	}
	
	public static void registerModels()
	{
		CommonProxy.registerRender(loraliteCrystal);
		
		CommonProxy.registerRender(legacyBlesser);
		CommonProxy.registerRender(accelixBlesser);
		CommonProxy.registerRender(fortemBlesser);
		CommonProxy.registerRender(lumenBlesser);
		CommonProxy.registerRender(novisBlesser);
		CommonProxy.registerRender(noxenBlesser);
		CommonProxy.registerRender(pondusBlesser);
		CommonProxy.registerRender(submariBlesser);
		CommonProxy.registerRender(regenerasBlesser);
		
		CommonProxy.registerRender(loralitepickaxe);
		CommonProxy.registerRender(loraliteaxe);
		CommonProxy.registerRender(loralitesword);
		CommonProxy.registerRender(loralitespade);
		CommonProxy.registerRender(loralitedagger);
		CommonProxy.registerRender(loralitehoe);
	}
	
}
