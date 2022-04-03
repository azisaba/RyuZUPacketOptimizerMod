package packetoptimizemod.packetoptimizemod.Particles.ColorFlameParticle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class ColorFlameParticleType extends ParticleType<ColorFlameParticleData> {
    public ColorFlameParticleType() {
        super(true, ColorFlameParticleData.DESERIALIZER);
    }

    @Override
    public Codec<ColorFlameParticleData> func_230522_e_() {
        return ColorFlameParticleData.field_239802_b_;
    }
}
