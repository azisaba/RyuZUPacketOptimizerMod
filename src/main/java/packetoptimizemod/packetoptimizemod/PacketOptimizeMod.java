package packetoptimizemod.packetoptimizemod;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle.ColorComposterParticleSetup;
import packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle.ColorCritParticleSetup;
import packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle.ColorEndRodParticleSetup;
import packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle.ColorFireworkParticleSetup;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlameParticle.ColorFlameParticleSetup;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle.ColorFlashParticle;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle.ColorFlashParticleSetup;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ryuzupacketoptimizer")
public class PacketOptimizeMod {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "ryuzupacketoptimizer";

    public PacketOptimizeMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);


        FMLJavaModLoadingContext.get().getModEventBus().register(ColorFlashParticleSetup.class);
        FMLJavaModLoadingContext.get().getModEventBus().register(ColorFlameParticleSetup.class);
        FMLJavaModLoadingContext.get().getModEventBus().register(ColorEndRodParticleSetup.class);
        FMLJavaModLoadingContext.get().getModEventBus().register(ColorComposterParticleSetup.class);
        FMLJavaModLoadingContext.get().getModEventBus().register(ColorCritParticleSetup.class);
        FMLJavaModLoadingContext.get().getModEventBus().register(ColorFireworkParticleSetup.class);

        // Register ourselves for server and other game events we are interested in
        LOGGER.info("msg");
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        ParticleChannnel.initialize();
//        SettingScreen.initialize();
        SettingScreen.read();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("PacketOptimizeMod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
