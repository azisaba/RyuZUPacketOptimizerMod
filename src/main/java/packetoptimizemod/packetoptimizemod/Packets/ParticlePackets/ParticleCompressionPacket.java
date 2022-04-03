package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
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

    public static void encode(ParticleCompressionPacket packet, PacketBuffer buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.packets.size());
        for (ByteBuf buf : packet.packets) {
            buffer.writeInt(buf.array().length);
            buffer.writeBytes(buf.array());
        }
    }

    public static ParticleCompressionPacket decode(PacketBuffer buffer) {
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
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        for (ByteBuf buf : packet.packets) {
            int id = buf.readByte();
            switch (id) {
                case ParticleBasePacket.ID:
                    ParticleBasePacket.processMessage(ParticleBasePacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleBlockPacket.ID:
                    ParticleBlockPacket.processMessage(ParticleBlockPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleColorPacket.ID:
                    ParticleColorPacket.processMessage(ParticleColorPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleCountPacket.ID:
                    ParticleCountPacket.processMessage(ParticleCountPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleOffsetPacket.ID:
                    ParticleOffsetPacket.processMessage(ParticleOffsetPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleVectorPacket.ID:
                    ParticleVectorPacket.processMessage(ParticleVectorPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleForcePacket.ID:
                    ParticleForcePacket.processMessage(ParticleForcePacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleFallingDustPacket.ID:
                    ParticleFallingDustPacket.processMessage(ParticleFallingDustPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleItemPacket.ID:
                    ParticleItemPacket.processMessage(ParticleItemPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleOffsetBlockPacket.ID:
                    ParticleOffsetBlockPacket.processMessage(ParticleOffsetBlockPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleOffsetItemPacket.ID:
                    ParticleOffsetItemPacket.processMessage(ParticleOffsetItemPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleOffsetFallingDustPacket.ID:
                    ParticleOffsetFallingDustPacket.processMessage(ParticleOffsetFallingDustPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleOffsetColorPacket.ID:
                    ParticleOffsetColorPacket.processMessage(ParticleOffsetColorPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleForceColorPacket.ID:
                    ParticleForceColorPacket.processMessage(ParticleForceColorPacket.decode(new PacketBuffer(buf)));
                    break;
                case ParticleOriginalColorPacket.ID:
                    ParticleOriginalColorPacket.processMessage(ParticleOriginalColorPacket.decode(new PacketBuffer(buf)));
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
