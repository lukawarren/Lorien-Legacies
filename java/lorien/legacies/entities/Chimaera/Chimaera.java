package lorien.legacies.entities.chimaera;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Chimaera extends EntityCreature {

	public static final String UNLOCALIZED_NAME = "chimaera";

	public EntityAIBase TARGET_PLAYER = new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true);

	public long lastMorph = 0;

	public long lastAngerPhase;

	public Chimaera(World worldIn) {
		super(worldIn);
		setSize(width, height);
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
		// TODO: import monster models, and randomly pick one
		this.morph(MorphHandler.ZOMBIE);
	}

	private void morph(String mobname) {
		if (!world.isRemote) {
			LorienLegacies.NETWORK.sendToAllAround(new MessageMorphChimaera(mobname, this),
					new TargetPoint(dimension, posX, posY, posY, 64));
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleMorph(String modelName) {

	}

	public static class MorphHandler {

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

		public static void postInit() {
			// TODO: insert renderers
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
		public IRenderFactory<? extends Entity> getRenderer(ResourceLocation morph) {
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
					for (Constructor constructor : renderclass.getConstructors()) {
						if (constructor.getParameterTypes().length == 1
								&& constructor.getParameterTypes()[0] == RenderManager.class) {
							try {
								return (Render<? super T>) constructor.newInstance(manager);
							} catch (InstantiationException e) {
								LorienLegacies.LOGGER.catching(e);
							} catch (IllegalAccessException e) {
								LorienLegacies.LOGGER.catching(e);
							} catch (IllegalArgumentException e) {
								LorienLegacies.LOGGER.catching(e);
							} catch (InvocationTargetException e) {
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
