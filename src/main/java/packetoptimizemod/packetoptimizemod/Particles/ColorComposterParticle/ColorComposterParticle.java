package packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle.ColorEndRodParticleData;

public class ColorComposterParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    public ColorComposterParticle(
            ClientWorld world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            ColorComposterParticleData particleData,
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
        this.setSize(0.02F, 0.02F);
        this.maxAge = 3 + world.getRandom().nextInt(5);
        this.particleScale *= this.rand.nextFloat() * 0.6F + 0.5F;
        this.selectSpriteWithAge(spriteWithAge);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.99D;
            this.motionY *= 0.99D;
            this.motionZ *= 0.99D;
        }
    }
}
