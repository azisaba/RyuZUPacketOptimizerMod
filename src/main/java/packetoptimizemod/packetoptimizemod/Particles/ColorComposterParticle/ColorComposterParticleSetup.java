package packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle;

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
public class ColorComposterParticleSetup {
    public static ParticleType<ColorComposterParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorComposterParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_composter");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(particleType, ColorComposterParticleFactory::new);
    }

    public static class ColorComposterParticleType extends ParticleType<ColorComposterParticleData> {
        public ColorComposterParticleType() {
            super(true, ColorComposterParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorComposterParticleData> codec() {
            return ColorComposterParticleData.field_239802_b_;
        }
    }

    public static class ColorComposterParticleFactory implements ParticleProvider<ColorComposterParticleData> {
        private final SpriteSet spriteSet;

        public ColorComposterParticleFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(
                ColorComposterParticleData typeIn, ClientLevel worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorComposterParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
