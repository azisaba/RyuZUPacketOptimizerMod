package packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class ColorFlashParticleFactory implements IParticleFactory<ColorFlashParticleData> {
    private final IAnimatedSprite spriteSet;

    public ColorFlashParticleFactory(IAnimatedSprite spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Nullable
    @Override
    public Particle makeParticle(
            ColorFlashParticleData typeIn, ClientWorld worldIn,
            double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
    ) {
        return new ColorFlashParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
    }
}
