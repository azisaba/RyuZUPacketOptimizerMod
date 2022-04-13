package packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle;

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
import packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle.ColorComposterParticle;
import packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle.ColorComposterParticleData;

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
        Minecraft.getInstance().particles.registerFactory(particleType, ColorComposterParticleFactory::new);
    }

    public static class ColorComposterParticleType extends ParticleType<ColorComposterParticleData> {
        public ColorComposterParticleType() {
            super(true, ColorComposterParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorComposterParticleData> func_230522_e_() {
            return ColorComposterParticleData.field_239802_b_;
        }
    }

    public static class ColorComposterParticleFactory implements IParticleFactory<ColorComposterParticleData> {
        private final IAnimatedSprite spriteSet;

        public ColorComposterParticleFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(
                ColorComposterParticleData typeIn, ClientWorld worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorComposterParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
