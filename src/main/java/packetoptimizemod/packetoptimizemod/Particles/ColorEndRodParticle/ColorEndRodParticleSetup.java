package packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import packetoptimizemod.packetoptimizemod.PacketOptimizeMod;

@Mod.EventBusSubscriber(modid = PacketOptimizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ColorEndRodParticleSetup {
    public static ParticleType<ColorEndRodParticleData> particleType;

    @SubscribeEvent
    public static void onTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
        particleType = new ColorEndRodParticleType();
        particleType.setRegistryName(PacketOptimizeMod.MOD_ID, "color_flame");
        event.getRegistry().register(particleType);
    }

    @SubscribeEvent
    public static void onFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(particleType, ColorEndRodParticleFactory::new);
    }
}
