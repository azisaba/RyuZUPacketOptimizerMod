package packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle.ColorFireworkParticleData;

public class ColorFireworkParticle extends SimpleAnimatedParticle {

    public ColorFireworkParticle(
            ClientWorld world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorFireworkParticleData particleData,
            IAnimatedSprite spriteWithAge
    ) {
        super(world, x, y, z, spriteWithAge, -0.004F);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleScale *= 0.75F;
        this.maxAge = 48 + this.rand.nextInt(12);
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f;
        this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f;
        this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f;
        this.setAlphaF(0.99F);
        this.selectSpriteWithAge(spriteWithAge);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        if (this.age < this.maxAge / 3 || (this.age + this.maxAge) / 3 % 2 == 0) {
            super.renderParticle(buffer, renderInfo, partialTicks);
        }
    }
}
