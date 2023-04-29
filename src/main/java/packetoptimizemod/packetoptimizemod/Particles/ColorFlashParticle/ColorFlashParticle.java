package packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class ColorFlashParticle extends TextureSheetParticle {
    public ColorFlashParticle(
            ClientLevel world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorFlashParticleData particleData,
            SpriteSet spriteWithAge
    ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.setColor(
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f,
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f,
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f
        );
        this.lifetime = 4;
        this.pickSprite(spriteWithAge);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        this.setAlpha(0.6F - ((float)this.age + partialTicks - 1.0F) * 0.25F * 0.5F);
        super.render(buffer, renderInfo, partialTicks);
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return (float) (7.1F * Math.sin(((float)this.age + scaleFactor - 1.0F) * 0.25F * (float)Math.PI));
    }
}
