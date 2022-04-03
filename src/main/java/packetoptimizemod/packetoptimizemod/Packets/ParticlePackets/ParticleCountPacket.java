package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.PacketSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleCountPacket extends ParticleBasePacket {
    public static final byte ID = 4;

    protected final int count;
    protected final float speed;

    public ParticleCountPacket(int type, int count, float speed) {
        super(type);
        this.count = count;
        this.speed = speed;
    }

    public ParticleCountPacket(int type, int count, float speed, List<Double> x, List<Double> y, List<Double> z) {
        super(type, x, y, z);
        this.count = count;
        this.speed = speed;
    }

    public boolean isSimilar(int type, int count, float speed) {
        return this.type == type && this.count == count && this.speed == speed;
    }

    public static void encode(ParticleCountPacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.speed);
        buffer.writeInt(packet.x.size());
        for (int i = 0; i < packet.x.size(); i++) {
            buffer.writeDouble(packet.x.get(i));
            buffer.writeDouble(packet.y.get(i));
            buffer.writeDouble(packet.z.get(i));
        }
    }

    public static ParticleCountPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float speed = buffer.readFloat();
        int size = buffer.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buffer.readDouble());
            y.add(buffer.readDouble());
            z.add(buffer.readDouble());
        }
        return new ParticleCountPacket(type, count, speed, x, y, z);
    }

    public static void onMessageReceived(ParticleCountPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleCountPacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        int count = packet.count == 810 ? 1 : packet.count;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < count; n++) {
                if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate || packet.count == 810) {
                    double x = packet.x.get(i);
                    double y = packet.y.get(i);
                    double z = packet.z.get(i);
                    double offx = -packet.speed + random.nextDouble() * packet.speed * 2;
                    double offy = -packet.speed + random.nextDouble() * packet.speed * 2;
                    double offz = -packet.speed + random.nextDouble() * packet.speed * 2;

                    world.addParticle(PacketSystem.Particle.values()[packet.type].getTypes(),true,
                            x, y, z, offx * 2, offy * 2, offz * 2);
                }
            }
        }
    }
}
