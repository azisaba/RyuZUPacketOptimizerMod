package packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

public class ColorFireworkParticle extends SimpleAnimatedParticle {

    public ColorFireworkParticle(
            ClientLevel world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorFireworkParticleData particleData,
            SpriteSet spriteWithAge
    ) {
        super(world, x, y, z, spriteWithAge, -0.004F);
        this.x = motionX;
        this.y = motionY;
        this.z = motionZ;
        this.quadSize *= 0.75F;
        this.age = 48 + this.random.nextInt(12);
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.setColor(
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f,
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f,
                ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f
        );
        this.setAlpha(0.99F);
        this.setSpriteFromAge(spriteWithAge);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        if (this.age < this.age / 3 || (this.age + this.age) / 3 % 2 == 0) {
            super.render(buffer, renderInfo, partialTicks);
        }
    }
}
