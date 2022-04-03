package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Forve;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.PacketSystem;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleBasePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleForcePacket extends ParticleBasePacket {
    public static final byte ID = 7;

    public ParticleForcePacket(int type) {
        super(type);
    }

    public ParticleForcePacket(int type, List<Double> x, List<Double> y, List<Double> z) {
        super(type, x, y, z);
    }

    public static void encode(ParticleForcePacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.x.size());
        for (int i = 0; i < packet.x.size(); i++) {
            buffer.writeDouble(packet.x.get(i));
            buffer.writeDouble(packet.y.get(i));
            buffer.writeDouble(packet.z.get(i));
        }
    }

    public static ParticleForcePacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int size = buffer.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buffer.readDouble());
            y.add(buffer.readDouble());
            z.add(buffer.readDouble());
        }

        return new ParticleForcePacket(type, x, y, z);
    }

    public static void onMessageReceived(ParticleForcePacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleForcePacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            double x = packet.x.get(i);
            double y = packet.y.get(i);
            double z = packet.z.get(i);

            world.addParticle(PacketSystem.Particle.values()[packet.type].getTypes(),true,
                    x, y, z, 0, 0, 0);
        }
    }
}
