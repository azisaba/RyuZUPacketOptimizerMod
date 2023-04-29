package packetoptimizemod.packetoptimizemod.Particles.ColorFlameParticle;

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
public class ColorFlameParticleSetup {
    public static ParticleType<ColorFlameParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorFlameParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_flame");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(particleType, ColorFlameParticleFactory::new);
    }

    public static class ColorFlameParticleType extends ParticleType<ColorFlameParticleData> {
        public ColorFlameParticleType() {
            super(true, ColorFlameParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorFlameParticleData> codec() {
            return ColorFlameParticleData.field_239802_b_;
        }
    }

    public static class ColorFlameParticleFactory implements ParticleProvider<ColorFlameParticleData> {
        private final SpriteSet spriteSet;

        public ColorFlameParticleFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(
                ColorFlameParticleData typeIn, ClientLevel worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
