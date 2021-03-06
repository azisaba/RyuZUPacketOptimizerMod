package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleType;
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
        Minecraft.getInstance().particles.registerFactory(particleType, ColorEndRodParticleFactory::new);
    }

    public static class ColorEndRodParticleType extends ParticleType<ColorEndRodParticleData> {
        public ColorEndRodParticleType() {
            super(true, ColorEndRodParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorEndRodParticleData> func_230522_e_() {
            return ColorEndRodParticleData.field_239802_b_;
        }
    }

    public static class ColorEndRodParticleFactory implements IParticleFactory<ColorEndRodParticleData> {
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
}
