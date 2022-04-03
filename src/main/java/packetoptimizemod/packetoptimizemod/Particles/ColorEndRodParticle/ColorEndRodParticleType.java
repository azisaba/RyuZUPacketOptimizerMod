package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class ColorEndRodParticleType extends ParticleType<ColorEndRodParticleData> {
    public ColorEndRodParticleType() {
        super(true, ColorEndRodParticleData.DESERIALIZER);
    }

    @Override
    public Codec<ColorEndRodParticleData> func_230522_e_() {
        return ColorEndRodParticleData.field_239802_b_;
    }
}
