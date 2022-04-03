package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class ColorEndRodParticleFactory implements IParticleFactory<ColorEndRodParticleData> {
    private final IAnimatedSprite spriteSet;

    public ColorEndRodParticleFactory(IAnimatedSprite spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Nullable
    @Override
    public Particle makeParticle(
            ColorEndRodParticleData typeIn, ClientWorld worldIn,
            double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
    ) {
        return new ColorEndRodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
    }
}
