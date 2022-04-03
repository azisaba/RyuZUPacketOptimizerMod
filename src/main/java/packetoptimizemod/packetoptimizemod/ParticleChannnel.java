package packetoptimizemod.packetoptimizemod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.*;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Forve.ParticleForceColorPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Forve.ParticleForcePacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material.ParticleBlockPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material.ParticleFallingDustPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material.ParticleItemPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset.*;
import packetoptimizemod.packetoptimizemod.Packets.*;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Original.ParticleOriginalColorPacket;

import java.util.HashMap;
import java.util.Optional;

public class ParticleChannnel {
    private static final String PROTOCOL_VERSION = "754";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PacketOptimizeMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void initialize() {
        INSTANCE.registerMessage(SendUsingModPacket.ID , SendUsingModPacket.class , SendUsingModPacket::encode ,
                SendUsingModPacket::decode , SendUsingModPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        INSTANCE.registerMessage(ParticleBasePacket.ID , ParticleBasePacket.class , ParticleBasePacket::encode ,
                ParticleBasePacket::decode , ParticleBasePacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleBlockPacket.ID , ParticleBlockPacket.class , ParticleBlockPacket::encode ,
                ParticleBlockPacket::decode , ParticleBlockPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleColorPacket.ID , ParticleColorPacket.class , ParticleColorPacket::encode ,
                ParticleColorPacket::decode , ParticleColorPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleCountPacket.ID , ParticleCountPacket.class , ParticleCountPacket::encode ,
                ParticleCountPacket::decode , ParticleCountPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleOffsetPacket.ID , ParticleOffsetPacket.class , ParticleOffsetPacket::encode ,
                ParticleOffsetPacket::decode , ParticleOffsetPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleVectorPacket.ID , ParticleVectorPacket.class , ParticleVectorPacket::encode ,
                ParticleVectorPacket::decode , ParticleVectorPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleForcePacket.ID , ParticleForcePacket.class , ParticleForcePacket::encode ,
                ParticleForcePacket::decode , ParticleForcePacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleFallingDustPacket.ID , ParticleFallingDustPacket.class , ParticleFallingDustPacket::encode ,
                ParticleFallingDustPacket::decode , ParticleFallingDustPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleItemPacket.ID , ParticleItemPacket.class , ParticleItemPacket::encode ,
                ParticleItemPacket::decode , ParticleItemPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleOffsetBlockPacket.ID , ParticleOffsetBlockPacket.class , ParticleOffsetBlockPacket::encode ,
                ParticleOffsetBlockPacket::decode , ParticleOffsetBlockPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleOffsetItemPacket.ID , ParticleOffsetItemPacket.class , ParticleOffsetItemPacket::encode ,
                ParticleOffsetItemPacket::decode , ParticleOffsetItemPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleOffsetFallingDustPacket.ID , ParticleOffsetFallingDustPacket.class , ParticleOffsetFallingDustPacket::encode ,
                ParticleOffsetFallingDustPacket::decode , ParticleOffsetFallingDustPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleOffsetColorPacket.ID , ParticleOffsetColorPacket.class , ParticleOffsetColorPacket::encode ,
                ParticleOffsetColorPacket::decode , ParticleOffsetColorPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleForceColorPacket.ID , ParticleForceColorPacket.class , ParticleForceColorPacket::encode ,
                ParticleForceColorPacket::decode , ParticleForceColorPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(SetParticleDrawingRatePacket.ID , SetParticleDrawingRatePacket.class , SetParticleDrawingRatePacket::encode ,
                SetParticleDrawingRatePacket::decode , SetParticleDrawingRatePacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleCompressionPacket.ID , ParticleCompressionPacket.class , ParticleCompressionPacket::encode ,
                ParticleCompressionPacket::decode , ParticleCompressionPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(CheckUsingModPacket.ID , CheckUsingModPacket.class , CheckUsingModPacket::encode ,
                CheckUsingModPacket::decode , CheckUsingModPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(ParticleOriginalColorPacket.ID , ParticleOriginalColorPacket.class , ParticleOriginalColorPacket::encode ,
                ParticleOriginalColorPacket::decode , ParticleOriginalColorPacket::onMessageReceived ,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static <T> void sendPacket(T packet) {
        INSTANCE.sendToServer(packet);
    }
}
