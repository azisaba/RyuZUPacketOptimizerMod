package packetoptimizemod.packetoptimizemod.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.PacketSystem;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset.ParticleOffsetPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleVectorPacket;

import java.util.function.Supplier;

public class SetParticleDrawingRatePacket {
    public static final byte ID = 15;

    protected final int value;

    public SetParticleDrawingRatePacket(int value) {
        this.value = value;
    }

    public static void encode(SetParticleDrawingRatePacket packet, PacketBuffer buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.value);
    }

    public static SetParticleDrawingRatePacket decode(PacketBuffer buffer) {
        int value = buffer.readInt();
        return new SetParticleDrawingRatePacket(value);
    }

    public static void onMessageReceived(SetParticleDrawingRatePacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    private static void processMessage(SetParticleDrawingRatePacket packet) {
        SettingScreen.drawingRate = packet.value;
    }
}
