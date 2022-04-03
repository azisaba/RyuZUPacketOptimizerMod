package packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class ColorFlashParticleType extends ParticleType<ColorFlashParticleData> {
    public ColorFlashParticleType() {
        super(true, ColorFlashParticleData.DESERIALIZER);
    }

    @Override
    public Codec<ColorFlashParticleData> func_230522_e_() {
        return ColorFlashParticleData.field_239802_b_;
    }
}
