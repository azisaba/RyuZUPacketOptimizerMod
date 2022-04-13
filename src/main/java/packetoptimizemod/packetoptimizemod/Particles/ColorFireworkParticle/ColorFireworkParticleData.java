package packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle.ColorFireworkParticleSetup;

import java.util.Locale;

public class ColorFireworkParticleData implements IParticleData {
    public static final ColorFireworkParticleData COLOR_FIREWORK = new ColorFireworkParticleData(1.0F, 0.0F, 0.0F, 1.0F);
    public static final Codec<ColorFireworkParticleData> field_239802_b_ = RecordCodecBuilder.create((p_239803_0_) -> {
        return p_239803_0_.group(Codec.FLOAT.fieldOf("r").forGetter((p_239807_0_) -> {
            return p_239807_0_.red;
        }), Codec.FLOAT.fieldOf("g").forGetter((p_239806_0_) -> {
            return p_239806_0_.green;
        }), Codec.FLOAT.fieldOf("b").forGetter((p_239805_0_) -> {
            return p_239805_0_.blue;
        }), Codec.FLOAT.fieldOf("scale").forGetter((p_239804_0_) -> {
            return p_239804_0_.alpha;
        })).apply(p_239803_0_, ColorFireworkParticleData::new);
    });
    public static final IDeserializer<ColorFireworkParticleData> DESERIALIZER = new IDeserializer<ColorFireworkParticleData>() {
        public ColorFireworkParticleData deserialize(ParticleType<ColorFireworkParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float) reader.readDouble();
            reader.expect(' ');
            float f1 = (float) reader.readDouble();
            reader.expect(' ');
            float f2 = (float) reader.readDouble();
            reader.expect(' ');
            float f3 = (float) reader.readDouble();
            return new ColorFireworkParticleData(f, f1, f2, f3);
        }

        public ColorFireworkParticleData read(ParticleType<ColorFireworkParticleData> particleTypeIn, PacketBuffer buffer) {
            return new ColorFireworkParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };

    public ParticleType<ColorFireworkParticleData> getType() {
        return ColorFireworkParticleSetup.particleType;
    }

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public ColorFireworkParticleData(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = MathHelper.clamp(alpha, 0.01F, 4.0F);
    }

    public void write(PacketBuffer buffer) {
        buffer.writeFloat(this.red);
        buffer.writeFloat(this.green);
        buffer.writeFloat(this.blue);
        buffer.writeFloat(this.alpha);
    }

    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue, this.alpha);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return this.alpha;
    }
}
