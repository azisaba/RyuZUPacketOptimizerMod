package packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import packetoptimizemod.packetoptimizemod.PacketOptimizeMod;
import packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle.ColorCritParticle;
import packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle.ColorCritParticleData;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = PacketOptimizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ColorCritParticleSetup {
    public static ParticleType<ColorCritParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorCritParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_crit");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(particleType, ColorCritParticleFactory::new);
    }

    public static class ColorCritParticleType extends ParticleType<ColorCritParticleData> {
        public ColorCritParticleType() {
            super(true, ColorCritParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorCritParticleData> codec() {
            return ColorCritParticleData.field_239802_b_;
        }
    }

    public static class ColorCritParticleFactory implements ParticleProvider<ColorCritParticleData> {
        private final SpriteSet spriteSet;

        public ColorCritParticleFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(
                ColorCritParticleData typeIn, ClientLevel worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorCritParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
