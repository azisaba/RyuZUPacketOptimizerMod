package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

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

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = PacketOptimizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ColorEndRodParticleSetup {
    public static ParticleType<ColorEndRodParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorEndRodParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_end_rod");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(particleType, ColorEndRodParticleFactory::new);
    }

    public static class ColorEndRodParticleType extends ParticleType<ColorEndRodParticleData> {
        public ColorEndRodParticleType() {
            super(true, ColorEndRodParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorEndRodParticleData> codec() {
            return ColorEndRodParticleData.field_239802_b_;
        }
    }

    public static class ColorEndRodParticleFactory implements ParticleProvider<ColorEndRodParticleData> {
        private final SpriteSet spriteSet;

        public ColorEndRodParticleFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(
                ColorEndRodParticleData typeIn, ClientLevel worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorEndRodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
