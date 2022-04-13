package packetoptimizemod.packetoptimizemod.Particles.ColorFlameParticle;

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
        Minecraft.getInstance().particles.registerFactory(particleType, ColorFlameParticleFactory::new);
    }

    public static class ColorFlameParticleType extends ParticleType<ColorFlameParticleData> {
        public ColorFlameParticleType() {
            super(true, ColorFlameParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorFlameParticleData> func_230522_e_() {
            return ColorFlameParticleData.field_239802_b_;
        }
    }

    public static class ColorFlameParticleFactory implements IParticleFactory<ColorFlameParticleData> {
        private final IAnimatedSprite spriteSet;

        public ColorFlameParticleFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(
                ColorFlameParticleData typeIn, ClientWorld worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
