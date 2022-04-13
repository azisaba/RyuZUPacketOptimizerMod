package packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle;

import net.minecraft.client.particle.DeceleratingParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle.ColorCritParticleData;

public class ColorCritParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    public ColorCritParticle(
            ClientWorld world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorCritParticleData particleData,
            IAnimatedSprite spriteWithAge
    ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.spriteWithAge = spriteWithAge;
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getRed() * f;
        this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getGreen() * f;
        this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * particleData.getBlue() * f;
        this.particleScale *= 0.75F;
        this.maxAge = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
        this.canCollide = false;
        this.selectSpriteWithAge(spriteWithAge);
        this.tick();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getScale(float scaleFactor) {
        return this.particleScale * MathHelper.clamp(((float)this.age + scaleFactor) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.particleGreen = (float)((double)this.particleGreen * 0.96D);
            this.particleBlue = (float)((double)this.particleBlue * 0.9D);
            this.motionX *= (double)0.7F;
            this.motionY *= (double)0.7F;
            this.motionZ *= (double)0.7F;
            this.motionY -= (double)0.02F;
            if (this.onGround) {
                this.motionX *= (double)0.7F;
                this.motionZ *= (double)0.7F;
            }

        }
    }
}
