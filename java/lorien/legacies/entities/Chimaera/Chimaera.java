package lorien.legacies.entities.Chimaera;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

import lorien.legacies.core.LorienLegacies;
import lorien.legacies.network.MessageMorphChimaera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Chimaera extends EntityCreature {

	public static final String UNLOCALIZED_NAME = "chimaera";

	public EntityAIBase TARGET_PLAYER = new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true);

	@SideOnly(Side.CLIENT)
	public MorphHandler morphHandler;

	public long lastMorph = 0;

	public long lastAngerPhase = 0;

	public Chimaera(World worldIn) {
		super(worldIn);
		setSize(width, height);
	}

	@Override
	public void onEntityUpdate() {
		if ((world.getTotalWorldTime() - lastMorph) > 4000) {
			morph();
		}
		if ((world.getTotalWorldTime() - lastAngerPhase) > 4000) {
			setFriendly();
		}
		super.onEntityUpdate();
	}

	private void morph() {
		switch (this.getRNG().nextInt(1)) {
		case 0:
			int index = this.getRNG().nextInt(MorphHandler.renders$forge.size() - 1);
			Entity key = (Entity) MorphHandler.renders$forge.keySet().toArray()[index];
			IRenderFactory<?> factory = MorphHandler.renders$forge.get(key);
			this.morphHandler.setCurrent(factory);
			break;
		case 1:
			int index1 = this.getRNG().nextInt(MorphHandler.renders$vanilla.size() - 1);
			Entity key1 = (Entity) MorphHandler.renders$forge.keySet().toArray()[index1];
			this.morphHandler
					.setCurrent(morphHandler.getRenderer(EntityRegistry.getEntry(key1.getClass()).getRegistryName()));
			break;
		default:
			return;
		}
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.7));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		super.initEntityAI();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity attacker = source.getTrueSource();
		if (attacker instanceof EntityPlayer) {
			this.setAngry();
		}
		return super.attackEntityFrom(source, amount);
	}

	private void setAngry() {
		this.lastAngerPhase = world.getTotalWorldTime();
		this.lastMorph = lastAngerPhase;
		tasks.addTask(10, TARGET_PLAYER);
		this.morph((String) MorphHandler.renders$lorienlegacies.keySet().toArray()//
		[this.getRNG().nextInt(MorphHandler.renders$lorienlegacies.size() - 1)]);
	}

	private void setFriendly() {
		this.morph();
		tasks.removeTask(TARGET_PLAYER);
	}

	private void morph(String mobname) {
		if (!world.isRemote) {
			LorienLegacies.NETWORK.sendToAllAround(new MessageMorphChimaera(mobname, this),
					new TargetPoint(dimension, posX, posY, posY, 64));
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleMorph(String modelName) {
		this.morphHandler.setCurrent(morphHandler.getRenderer(modelName));
	}

	@SideOnly(Side.CLIENT)
	public static class MorphHandler {

		public static <T extends Entity> T getEntityFor(Render<T> renderer, World world) {
			Class<T> clazz = (Class<T>) renders$classes.get(renderer.getClass());
			Class[] args = { World.class };
			if (clazz != null) {
				for (Constructor constructor : clazz.getConstructors()) {
					if (Arrays.equals(constructor.getParameterTypes(), args)) {

						try {
							return (T) constructor.newInstance(world);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							LorienLegacies.LOGGER.catching(e);
						}
					}
				}
			}
			return null;
		}

		private IRenderFactory<? extends Entity> current;

		public IRenderFactory<? extends Entity> getCurrent() {
			return current;
		}

		protected void setCurrent(IRenderFactory<? extends Entity> current) {
			this.current = current;
		}

		// Vanilla renders
		static HashMap<Entity, Render<?>> renders$vanilla = new HashMap<>();
		// All modded renders
		static HashMap<Entity, IRenderFactory<?>> renders$forge = new HashMap<>();
		// Monster renders for Lorien Legacies
		static HashMap<String, IRenderFactory<?>> renders$lorienlegacies = new HashMap<>();
		// All classes
		static HashMap<Class<? extends Render<? extends Entity>>, Class<? extends Entity>> renders$classes = new HashMap<>();

		public static void postInit() {
			for(EntityEntry entry : ForgeRegistries.ENTITIES) {
				
				
			}
		}

		public static final String ZOMBIE = "zombie";

		@SideOnly(Side.CLIENT)
		public IRenderFactory<? extends Entity> getRenderer(String morph) {
			if (morph.contains(":")) {
				String[] split = ResourceLocation.splitObjectName(morph);
				return getRenderer(new ResourceLocation(split[0], split[1]));
			}
			if (renders$lorienlegacies.containsKey(morph)) {
				return renders$lorienlegacies.get(morph);
			}
			return null;
		}

		@SideOnly(Side.CLIENT)
		private IRenderFactory<? extends Entity> getRenderer(ResourceLocation morph) {
			for (Entity entity : renders$forge.keySet()) {
				if (EntityRegistry.getEntry(entity.getClass()).getRegistryName().equals(morph)) {
					return renders$forge.get(entity);
				}
			}
			for (Entity entity : renders$vanilla.keySet()) {
				if (EntityRegistry.getEntry(entity.getClass()).getRegistryName().equals(morph)) {
					return genFactory((Render<?>) renders$vanilla.get(entity));
				}
			}
			return null;
		}

		private <T extends Entity> IRenderFactory<T> genFactory(Render<T> render) {
			return new IRenderFactory<T>() {
				@Override
				public Render<? super T> createRenderFor(RenderManager manager) {
					Class<? extends Render> renderclass = render.getClass();
					Class[] args = { RenderManager.class };
					for (Constructor constructor : renderclass.getConstructors()) {
						if (Arrays.equals(constructor.getParameterTypes(), args)) {
							try {
								return (Render<? super T>) constructor.newInstance(manager);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e) {
								LorienLegacies.LOGGER.catching(e);
							}

							LorienLegacies.LOGGER.error(
									"Couldn't create the render!! Falling back into the default one! This might cause problems");
						}
					}
					return render;
				}
			};
		}

	}

}
