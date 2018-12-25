package lorien.legacies.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class tigerram extends ModelBase {
	ModelRenderer Part_0; // FrontLeftPaw
	ModelRenderer Part_1; // FrontLeftForeleg
	ModelRenderer Part_2; // FrontLeftBicep
	ModelRenderer Part_3; // FrontLeftShoulder
	ModelRenderer Part_4; // Body
	ModelRenderer Part_5; // FrontRightPaw
	ModelRenderer Part_6; // FrontRightForeleg
	ModelRenderer Part_7; // FrontRightBicep
	ModelRenderer Part_8; // FrontRightShoulder
	ModelRenderer Part_9; // LeftHip
	ModelRenderer Part_10; // BackLeftPaw
	ModelRenderer Part_11; // BackRightPaw
	ModelRenderer Part_12; // LeftShin
	ModelRenderer Part_13; // RightShin
	ModelRenderer Part_14; // LeftThigh
	ModelRenderer Part_15; // RightThigh
	ModelRenderer Part_16; // RightHip
	ModelRenderer Part_17; // Tail1
	ModelRenderer Part_18; // Tail2
	ModelRenderer Part_19; // Body
	ModelRenderer Part_20; // Head
	ModelRenderer Part_21; // Head
	ModelRenderer Part_22; // Head
	ModelRenderer Part_23; // Head
	ModelRenderer Part_24; // LowerJaw
	ModelRenderer Part_25; // Head
	ModelRenderer Part_26; // Head
	ModelRenderer Part_27; // Head
	ModelRenderer Part_28; // Head
	ModelRenderer Part_29; // Head
	ModelRenderer Part_30; // Head
	ModelRenderer Part_31; // Head
	ModelRenderer Part_32; // Head
	ModelRenderer Part_33; // Head
	ModelRenderer Part_34; // Head
	ModelRenderer Part_35; // Head
	ModelRenderer Part_36; // Head
	ModelRenderer Part_37; // Head
	ModelRenderer Part_38; // Head
	ModelRenderer Part_39; // Head
	ModelRenderer Part_40; // Head
	ModelRenderer Part_41; // Head
	ModelRenderer Part_42; // Head
	ModelRenderer Part_43; // Head
	ModelRenderer Part_44; // Head
	ModelRenderer Part_45; // Head
	ModelRenderer Part_46; // Head

	public tigerram() {
		textureWidth = 512;
		textureHeight = 512;

		Part_0 = new ModelRenderer(this, 1, 1); // FrontLeftPaw
		Part_0.addBox(0F, 0F, 0F, 4, 2, 5);
		Part_0.setRotationPoint(0F, 8F, -2F);
		Part_0.setTextureSize(512, 512);
		Part_0.mirror = true;
		setRotation(Part_0, 0F, 3.14159265F, 0F);
		Part_1 = new ModelRenderer(this, 25, 1); // FrontLeftForeleg
		Part_1.addBox(0F, 0F, 0F, 4, 8, 3);
		Part_1.setRotationPoint(3F, 2F, -3F);
		Part_1.setTextureSize(512, 512);
		Part_1.mirror = true;
		setRotation(Part_1, 0F, 3.14159265F, -0.39269908F);
		Part_2 = new ModelRenderer(this, 41, 1); // FrontLeftBicep
		Part_2.addBox(0F, 0F, 0F, 4, 5, 3);
		Part_2.setRotationPoint(1F, -3F, -3F);
		Part_2.setTextureSize(512, 512);
		Part_2.mirror = true;
		setRotation(Part_2, 0F, 3.14159265F, 0.39269908F);
		Part_3 = new ModelRenderer(this, 57, 1); // FrontLeftShoulder
		Part_3.addBox(0F, 0F, 0F, 8, 12, 5);
		Part_3.setRotationPoint(3F, -13F, -2F);
		Part_3.setTextureSize(512, 512);
		Part_3.mirror = true;
		setRotation(Part_3, 0F, 3.14159265F, 0F);
		Part_4 = new ModelRenderer(this, 75, 17); // Body
		Part_4.addBox(0F, 0F, 0F, 10, 2, 12);
		Part_4.setRotationPoint(4F, -16F, 6F);
		Part_4.setTextureSize(512, 512);
		Part_4.mirror = true;
		setRotation(Part_4, 0F, 3.14159265F, 0F);
		Part_5 = new ModelRenderer(this, 129, 1); // FrontRightPaw
		Part_5.addBox(0F, 0F, 0F, 4, 2, 5);
		Part_5.setRotationPoint(0F, 8F, 7F);
		Part_5.setTextureSize(512, 512);
		Part_5.mirror = true;
		setRotation(Part_5, 0F, 3.14159265F, 0F);
		Part_6 = new ModelRenderer(this, 153, 1); // FrontRightForeleg
		Part_6.addBox(0F, 0F, 0F, 4, 8, 3);
		Part_6.setRotationPoint(3F, 2F, 6F);
		Part_6.setTextureSize(512, 512);
		Part_6.mirror = true;
		setRotation(Part_6, 0F, 3.14159265F, -0.39269908F);
		Part_7 = new ModelRenderer(this, 169, 1); // FrontRightBicep
		Part_7.addBox(0F, 0F, 0F, 4, 5, 3);
		Part_7.setRotationPoint(1F, -3F, 6F);
		Part_7.setTextureSize(512, 512);
		Part_7.mirror = true;
		setRotation(Part_7, 0F, 3.14159265F, 0.39269908F);
		Part_8 = new ModelRenderer(this, 185, 1); // FrontRightShoulder
		Part_8.addBox(0F, 0F, 0F, 8, 12, 5);
		Part_8.setRotationPoint(3F, -13F, 7F);
		Part_8.setTextureSize(512, 512);
		Part_8.mirror = true;
		setRotation(Part_8, 0F, 3.14159265F, 0F);
		Part_9 = new ModelRenderer(this, 217, 1); // LeftHip
		Part_9.addBox(0F, 0F, 0F, 9, 12, 5);
		Part_9.setRotationPoint(27F, -13F, -2F);
		Part_9.setTextureSize(512, 512);
		Part_9.mirror = true;
		setRotation(Part_9, 0F, 3.14159265F, 0F);
		Part_10 = new ModelRenderer(this, 249, 1); // BackLeftPaw
		Part_10.addBox(0F, 0F, 0F, 5, 2, 5);
		Part_10.setRotationPoint(26F, 8F, -2F);
		Part_10.setTextureSize(512, 512);
		Part_10.mirror = true;
		setRotation(Part_10, 0F, 3.14159265F, 0F);
		Part_11 = new ModelRenderer(this, 273, 1); // BackRightPaw
		Part_11.addBox(0F, 0F, 0F, 5, 2, 5);
		Part_11.setRotationPoint(26F, 8F, 7F);
		Part_11.setTextureSize(512, 512);
		Part_11.mirror = true;
		setRotation(Part_11, 0F, 3.14159265F, 0F);
		Part_12 = new ModelRenderer(this, 297, 1); // LeftShin
		Part_12.addBox(0F, 0F, 0F, 4, 7, 3);
		Part_12.setRotationPoint(28F, 3F, -3F);
		Part_12.setTextureSize(512, 512);
		Part_12.mirror = true;
		setRotation(Part_12, 0F, 3.14159265F, -0.39269908F);
		Part_13 = new ModelRenderer(this, 313, 1); // RightShin
		Part_13.addBox(0F, 0F, 0F, 4, 7, 3);
		Part_13.setRotationPoint(28F, 3F, 6F);
		Part_13.setTextureSize(512, 512);
		Part_13.mirror = true;
		setRotation(Part_13, 0F, 3.14159265F, -0.39269908F);
		Part_14 = new ModelRenderer(this, 329, 1); // LeftThigh
		Part_14.addBox(0F, 0F, 0F, 4, 7, 3);
		Part_14.setRotationPoint(25F, -3F, -3F);
		Part_14.setTextureSize(512, 512);
		Part_14.mirror = true;
		setRotation(Part_14, 0F, 3.14159265F, 0.39269908F);
		Part_15 = new ModelRenderer(this, 345, 1); // RightThigh
		Part_15.addBox(0F, 0F, 0F, 4, 7, 3);
		Part_15.setRotationPoint(25F, -3F, 6F);
		Part_15.setTextureSize(512, 512);
		Part_15.mirror = true;
		setRotation(Part_15, 0F, 3.14159265F, 0.39269908F);
		Part_16 = new ModelRenderer(this, 361, 1); // RightHip
		Part_16.addBox(0F, 0F, 0F, 9, 12, 5);
		Part_16.setRotationPoint(27F, -13F, 7F);
		Part_16.setTextureSize(512, 512);
		Part_16.mirror = true;
		setRotation(Part_16, 0F, 3.14159265F, 0F);
		Part_17 = new ModelRenderer(this, 393, 1); // Tail1
		Part_17.addBox(0F, 0F, 0F, 17, 4, 4);
		Part_17.setRotationPoint(42F, -7F, 2F);
		Part_17.setTextureSize(512, 512);
		Part_17.mirror = true;
		setRotation(Part_17, 0F, 3.14159265F, -0.39269908F);
		Part_18 = new ModelRenderer(this, 441, 1); // Tail2
		Part_18.addBox(0F, 0F, 0F, 17, 4, 4);
		Part_18.setRotationPoint(54F, -13F, 2F);
		Part_18.setTextureSize(512, 512);
		Part_18.mirror = true;
		setRotation(Part_18, 0F, 3.14159265F, 0.39269908F);
		Part_19 = new ModelRenderer(this, 73, 17); // Body
		Part_19.addBox(0F, 0F, 0F, 32, 13, 12);
		Part_19.setRotationPoint(26F, -14F, 6F);
		Part_19.setTextureSize(512, 512);
		Part_19.mirror = true;
		setRotation(Part_19, 0F, 3.14159265F, 0F);
		Part_20 = new ModelRenderer(this, 241, 9); // Head
		Part_20.addBox(0F, 0F, 0F, 10, 10, 12);
		Part_20.setRotationPoint(-6F, -16F, 6F);
		Part_20.setTextureSize(512, 512);
		Part_20.mirror = true;
		setRotation(Part_20, 0F, 3.14159265F, 0F);
		Part_21 = new ModelRenderer(this, 81, 1); // Head
		Part_21.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_21.setRotationPoint(-7F, -18F, 5F);
		Part_21.setTextureSize(512, 512);
		Part_21.mirror = true;
		setRotation(Part_21, 0F, 3.14159265F, 0F);
		Part_22 = new ModelRenderer(this, 209, 1); // Head
		Part_22.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_22.setRotationPoint(-7F, -18F, -3F);
		Part_22.setTextureSize(512, 512);
		Part_22.mirror = true;
		setRotation(Part_22, 0F, 3.14159265F, 0F);
		Part_23 = new ModelRenderer(this, 1, 9); // Head
		Part_23.addBox(0F, 0F, 0F, 6, 3, 6);
		Part_23.setRotationPoint(-14F, -11F, 3F);
		Part_23.setTextureSize(512, 512);
		Part_23.mirror = true;
		setRotation(Part_23, 0F, 3.14159265F, 0F);
		Part_24 = new ModelRenderer(this, 1, 23); // LowerJaw
		Part_24.addBox(0F, 0F, 0F, 5, 2, 6);
		Part_24.setRotationPoint(-14F, -7F, 3F);
		Part_24.setTextureSize(512, 512);
		Part_24.mirror = true;
		setRotation(Part_24, 0F, 3.14159265F, 0F);
		Part_25 = new ModelRenderer(this, 300, 22); // Head
		Part_25.addBox(0F, 0F, 0F, 1, 1, 1);
		Part_25.setRotationPoint(-19F, -8F, 3F);
		Part_25.setTextureSize(512, 512);
		Part_25.mirror = true;
		setRotation(Part_25, 0F, 3.14159265F, 0F);
		Part_26 = new ModelRenderer(this, 300, 22); // Head
		Part_26.addBox(0F, 0F, 0F, 1, 1, 1);
		Part_26.setRotationPoint(-19F, -8F, -2F);
		Part_26.setTextureSize(512, 512);
		Part_26.mirror = true;
		setRotation(Part_26, 0F, 3.14159265F, 0F);
		Part_27 = new ModelRenderer(this, 300, 22); // Head
		Part_27.addBox(0F, 0F, 0F, 0, 1, 1);
		Part_27.setRotationPoint(-20F, -8F, 1F);
		Part_27.setTextureSize(512, 512);
		Part_27.mirror = true;
		setRotation(Part_27, 0F, 3.14159265F, 0F);
		Part_28 = new ModelRenderer(this, 300, 22); // Head
		Part_28.addBox(0F, 0F, 0F, 1, 1, 0);
		Part_28.setRotationPoint(-19F, -8F, -1F);
		Part_28.setTextureSize(512, 512);
		Part_28.mirror = true;
		setRotation(Part_28, 0F, 3.14159265F, 0F);
		Part_29 = new ModelRenderer(this, 300, 22); // Head
		Part_29.addBox(0F, 0F, 0F, 1, 1, 0);
		Part_29.setRotationPoint(-17F, -8F, -3F);
		Part_29.setTextureSize(512, 512);
		Part_29.mirror = true;
		setRotation(Part_29, 0F, 3.14159265F, 0F);
		Part_30 = new ModelRenderer(this, 300, 22); // Head
		Part_30.addBox(0F, 0F, 0F, 1, 1, 0);
		Part_30.setRotationPoint(-17F, -8F, 3F);
		Part_30.setTextureSize(512, 512);
		Part_30.mirror = true;
		setRotation(Part_30, 0F, 3.14159265F, 0F);
		Part_31 = new ModelRenderer(this, 38, 37); // Head
		Part_31.addBox(0F, 0F, 0F, 2, 2, 2);
		Part_31.setRotationPoint(-15F, -15F, -7F);
		Part_31.setTextureSize(512, 512);
		Part_31.mirror = true;
		setRotation(Part_31, 0F, 0F, -0.78539816F);
		Part_32 = new ModelRenderer(this, 38, 37); // Head
		Part_32.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_32.setRotationPoint(-14F, -16F, -8F);
		Part_32.setTextureSize(512, 512);
		Part_32.mirror = true;
		setRotation(Part_32, 0F, 0F, -0.78539816F);
		Part_33 = new ModelRenderer(this, 38, 37); // Head
		Part_33.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_33.setRotationPoint(-12F, -19F, -8F);
		Part_33.setTextureSize(512, 512);
		Part_33.mirror = true;
		setRotation(Part_33, 0F, 0.39269908F, 0F);
		Part_34 = new ModelRenderer(this, 38, 37); // Head
		Part_34.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_34.setRotationPoint(-8F, -19F, -9F);
		Part_34.setTextureSize(512, 512);
		Part_34.mirror = true;
		setRotation(Part_34, 0F, 0F, 0.78539816F);
		Part_35 = new ModelRenderer(this, 38, 37); // Head
		Part_35.addBox(0F, 0F, 0F, 2, 4, 2);
		Part_35.setRotationPoint(-7F, -17F, -9F);
		Part_35.setTextureSize(512, 512);
		Part_35.mirror = true;
		setRotation(Part_35, -0.39269908F, 0F, 0F);
		Part_36 = new ModelRenderer(this, 38, 37); // Head
		Part_36.addBox(0F, 0F, 0F, 2, 4, 2);
		Part_36.setRotationPoint(-7F, -14F, -10F);
		Part_36.setTextureSize(512, 512);
		Part_36.mirror = true;
		setRotation(Part_36, 0F, 0F, 0.39269908F);
		Part_37 = new ModelRenderer(this, 38, 37); // Head
		Part_37.addBox(0F, 0F, 0F, 2, 5, 2);
		Part_37.setRotationPoint(-7F, -12F, -10F);
		Part_37.setTextureSize(512, 512);
		Part_37.mirror = true;
		setRotation(Part_37, 0F, 0F, 1.17809725F);
		Part_38 = new ModelRenderer(this, 38, 37); // Head
		Part_38.addBox(0F, 0F, 0F, 2, 6, 2);
		Part_38.setRotationPoint(-10F, -10F, -10F);
		Part_38.setTextureSize(512, 512);
		Part_38.mirror = true;
		setRotation(Part_38, 0F, 0F, 1.96349541F);
		Part_39 = new ModelRenderer(this, 38, 37); // Head
		Part_39.addBox(0F, 0F, 0F, 2, 2, 2);
		Part_39.setRotationPoint(-15F, -15F, 5F);
		Part_39.setTextureSize(512, 512);
		Part_39.mirror = true;
		setRotation(Part_39, 0F, 0F, -0.78539816F);
		Part_40 = new ModelRenderer(this, 38, 37); // Head
		Part_40.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_40.setRotationPoint(-14F, -16F, 6F);
		Part_40.setTextureSize(512, 512);
		Part_40.mirror = true;
		setRotation(Part_40, 0F, 0F, -0.78539816F);
		Part_41 = new ModelRenderer(this, 38, 37); // Head
		Part_41.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_41.setRotationPoint(-11F, -19F, 6F);
		Part_41.setTextureSize(512, 512);
		Part_41.mirror = true;
		setRotation(Part_41, 0F, -0.39269908F, 0F);
		Part_42 = new ModelRenderer(this, 38, 37); // Head
		Part_42.addBox(0F, 0F, 0F, 4, 2, 2);
		Part_42.setRotationPoint(-8F, -19F, 7F);
		Part_42.setTextureSize(512, 512);
		Part_42.mirror = true;
		setRotation(Part_42, 0F, 0F, 0.78539816F);
		Part_43 = new ModelRenderer(this, 38, 37); // Head
		Part_43.addBox(0F, 0F, 0F, 2, 4, 2);
		Part_43.setRotationPoint(-7F, -16F, 7F);
		Part_43.setTextureSize(512, 512);
		Part_43.mirror = true;
		setRotation(Part_43, 0.39269908F, 0F, 0F);
		Part_44 = new ModelRenderer(this, 38, 37); // Head
		Part_44.addBox(0F, 0F, 0F, 2, 4, 2);
		Part_44.setRotationPoint(-7F, -14F, 8F);
		Part_44.setTextureSize(512, 512);
		Part_44.mirror = true;
		setRotation(Part_44, 0F, 0F, 0.39269908F);
		Part_45 = new ModelRenderer(this, 38, 37); // Head
		Part_45.addBox(0F, 0F, 0F, 2, 5, 2);
		Part_45.setRotationPoint(-7F, -12F, 8F);
		Part_45.setTextureSize(512, 512);
		Part_45.mirror = true;
		setRotation(Part_45, 0F, 0F, 1.17809725F);
		Part_46 = new ModelRenderer(this, 38, 37); // Head
		Part_46.addBox(0F, 0F, 0F, 2, 6, 2);
		Part_46.setRotationPoint(-10F, -10F, 8F);
		Part_46.setTextureSize(512, 512);
		Part_46.mirror = true;
		setRotation(Part_46, 0F, 0F, 1.96349541F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Part_0.render(f5); // FrontLeftPaw
		Part_1.render(f5); // FrontLeftForeleg
		Part_2.render(f5); // FrontLeftBicep
		Part_3.render(f5); // FrontLeftShoulder
		Part_4.render(f5); // Body
		Part_5.render(f5); // FrontRightPaw
		Part_6.render(f5); // FrontRightForeleg
		Part_7.render(f5); // FrontRightBicep
		Part_8.render(f5); // FrontRightShoulder
		Part_9.render(f5); // LeftHip
		Part_10.render(f5); // BackLeftPaw
		Part_11.render(f5); // BackRightPaw
		Part_12.render(f5); // LeftShin
		Part_13.render(f5); // RightShin
		Part_14.render(f5); // LeftThigh
		Part_15.render(f5); // RightThigh
		Part_16.render(f5); // RightHip
		Part_17.render(f5); // Tail1
		Part_18.render(f5); // Tail2
		Part_19.render(f5); // Body
		Part_20.render(f5); // Head
		Part_21.render(f5); // Head
		Part_22.render(f5); // Head
		Part_23.render(f5); // Head
		Part_24.render(f5); // LowerJaw
		Part_25.render(f5); // Head
		Part_26.render(f5); // Head
		Part_27.render(f5); // Head
		Part_28.render(f5); // Head
		Part_29.render(f5); // Head
		Part_30.render(f5); // Head
		Part_31.render(f5); // Head
		Part_32.render(f5); // Head
		Part_33.render(f5); // Head
		Part_34.render(f5); // Head
		Part_35.render(f5); // Head
		Part_36.render(f5); // Head
		Part_37.render(f5); // Head
		Part_38.render(f5); // Head
		Part_39.render(f5); // Head
		Part_40.render(f5); // Head
		Part_41.render(f5); // Head
		Part_42.render(f5); // Head
		Part_43.render(f5); // Head
		Part_44.render(f5); // Head
		Part_45.render(f5); // Head
		Part_46.render(f5); // Head
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f1, f2, f3, f4, f5, f5, entity);
	}

}
