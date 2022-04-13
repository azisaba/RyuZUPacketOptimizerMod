package packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle;

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
import packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle.ColorFireworkParticle;
import packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle.ColorFireworkParticleData;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = PacketOptimizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ColorFireworkParticleSetup {
    public static ParticleType<ColorFireworkParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorFireworkParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_firework");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(particleType, ColorFireworkParticleFactory::new);
    }

    public static class ColorFireworkParticleType extends ParticleType<ColorFireworkParticleData> {
        public ColorFireworkParticleType() {
            super(true, ColorFireworkParticleData.DESERIALIZER);
        }

        @Override
        public Codec<ColorFireworkParticleData> func_230522_e_() {
            return ColorFireworkParticleData.field_239802_b_;
        }
    }

    public static class ColorFireworkParticleFactory implements IParticleFactory<ColorFireworkParticleData> {
        private final IAnimatedSprite spriteSet;

        public ColorFireworkParticleFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(
                ColorFireworkParticleData typeIn, ClientWorld worldIn,
                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
        ) {
            return new ColorFireworkParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
        }
    }
}
