package packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

public class ColorFlashParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    public ColorFlashParticle(
            ClientWorld world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorFlashParticleData particleData,
            IAnimatedSprite spriteWithAge
    ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.spriteWithAge = spriteWithAge;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f;
        this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f;
        this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f;
        this.particleScale *= 0.75F * particleData.getAlpha();
        int i = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.maxAge = 4;
        this.selectSpriteWithAge(spriteWithAge);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        this.setAlphaF(0.6F - ((float)this.age + partialTicks - 1.0F) * 0.25F * 0.5F);
        super.renderParticle(buffer, renderInfo, partialTicks);
    }

    @Override
    public float getScale(float scaleFactor) {
        return 7.1F * MathHelper.sin(((float)this.age + scaleFactor - 1.0F) * 0.25F * (float)Math.PI);
    }
}
