package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Forve.ParticleForceColorPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Forve.ParticleForcePacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material.ParticleBlockPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material.ParticleFallingDustPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material.ParticleItemPacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset.*;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Original.ParticleOriginalColorPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleCompressionPacket {
    public static final byte ID = 16;

    protected final List<ByteBuf> packets;

    public ParticleCompressionPacket() {
        this.packets = new ArrayList<>();
    }

    public ParticleCompressionPacket(List<ByteBuf> packets) {
        this.packets = packets;
    }

    public static void encode(ParticleCompressionPacket packet, FriendlyByteBuf buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.packets.size());
        for (ByteBuf buf : packet.packets) {
            buffer.writeInt(buf.array().length);
            buffer.writeBytes(buf.array());
        }
    }

    public static ParticleCompressionPacket decode(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        List<ByteBuf> packets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int length = buffer.readInt();
            packets.add(buffer.readBytes(length));
        }
        return new ParticleCompressionPacket(packets);
    }

    public static void onMessageReceived(ParticleCompressionPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleCompressionPacket packet) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world == null) return;
        for (ByteBuf buf : packet.packets) {
            int id = buf.readByte();
            switch (id) {
                case ParticleBasePacket.ID:
                    ParticleBasePacket.processMessage(ParticleBasePacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleBlockPacket.ID:
                    ParticleBlockPacket.processMessage(ParticleBlockPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleColorPacket.ID:
                    ParticleColorPacket.processMessage(ParticleColorPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleCountPacket.ID:
                    ParticleCountPacket.processMessage(ParticleCountPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleOffsetPacket.ID:
                    ParticleOffsetPacket.processMessage(ParticleOffsetPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleVectorPacket.ID:
                    ParticleVectorPacket.processMessage(ParticleVectorPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleForcePacket.ID:
                    ParticleForcePacket.processMessage(ParticleForcePacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleFallingDustPacket.ID:
                    ParticleFallingDustPacket.processMessage(ParticleFallingDustPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleItemPacket.ID:
                    ParticleItemPacket.processMessage(ParticleItemPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleOffsetBlockPacket.ID:
                    ParticleOffsetBlockPacket.processMessage(ParticleOffsetBlockPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleOffsetItemPacket.ID:
                    ParticleOffsetItemPacket.processMessage(ParticleOffsetItemPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleOffsetFallingDustPacket.ID:
                    ParticleOffsetFallingDustPacket.processMessage(ParticleOffsetFallingDustPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleOffsetColorPacket.ID:
                    ParticleOffsetColorPacket.processMessage(ParticleOffsetColorPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleForceColorPacket.ID:
                    ParticleForceColorPacket.processMessage(ParticleForceColorPacket.decode(new FriendlyByteBuf(buf)));
                    break;
                case ParticleOriginalColorPacket.ID:
                    ParticleOriginalColorPacket.processMessage(ParticleOriginalColorPacket.decode(new FriendlyByteBuf(buf)));
                    break;
            }
        }
    }

    public void addPacket(byte[] packet) {
        this.packets.add(Unpooled.wrappedBuffer(packet));
    }

    public int getSize() {
        return packets.stream().mapToInt(map -> map.array().length).sum();
    }

    public boolean ableAdd(byte[] packet) {
        return getSize() + packet.length <= 1000;
    }
}
