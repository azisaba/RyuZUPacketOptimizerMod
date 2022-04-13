package packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle;

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
public class ColorFlashParticleSetup {
    public static ParticleType<ColorFlashParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorFlashParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_flash");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(particleType, ColorFlashParticleFactory::new);
    }

    public static class ColorFlashParticleType extends ParticleType<ColorFlashParticleData> {
        public ColorFlashParticleType() {
            super(true, ColorFlashParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorFlashParticleData> func_230522_e_() {
            return ColorFlashParticleData.field_239802_b_;
        }
    }

    public static class ColorFlashParticleFactory implements IParticleFactory<ColorFlashParticleData> {
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
}
