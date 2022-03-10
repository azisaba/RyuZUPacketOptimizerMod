package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.PacketSystem;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleCountPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOffsetPacket extends ParticleCountPacket {
    public static final byte ID = 5;

    protected List<Float> offx = new ArrayList<>();
    protected List<Float> offy = new ArrayList<>();
    protected List<Float> offz = new ArrayList<>();


    public ParticleOffsetPacket(int type, int count, float speed) {
        super(type, count, speed);
    }

    public ParticleOffsetPacket(int type, int count, float speed, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, count, speed, x, y, z);
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
    }

    public static void encode(ParticleOffsetPacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.speed);
        buffer.writeInt(packet.x.size());
        for (int i = 0; i < packet.x.size(); i++) {
            buffer.writeDouble(packet.x.get(i));
            buffer.writeDouble(packet.y.get(i));
            buffer.writeDouble(packet.z.get(i));
            buffer.writeFloat(packet.offx.get(i));
            buffer.writeFloat(packet.offy.get(i));
            buffer.writeFloat(packet.offz.get(i));
        }
    }

    public static ParticleOffsetPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float speed = buffer.readFloat();
        int size = buffer.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        List<Float> offx = new ArrayList<>();
        List<Float> offy = new ArrayList<>();
        List<Float> offz = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buffer.readDouble());
            y.add(buffer.readDouble());
            z.add(buffer.readDouble());
            offx.add(buffer.readFloat());
            offy.add(buffer.readFloat());
            offz.add(buffer.readFloat());
        }
        return new ParticleOffsetPacket(type, count, speed, x, y, z, offx, offy, offz);
    }

    public static void onMessageReceived(ParticleOffsetPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOffsetPacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        int count = packet.count == 810 ? 1 : packet.count;
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < count; n++) {
                if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate || packet.count == 810) {
                    double x = packet.x.get(i) + (-packet.offx.get(i) + random.nextDouble() * packet.offx.get(i) * 2) * 2;
                    double y = packet.y.get(i) + (-packet.offy.get(i) + random.nextDouble() * packet.offy.get(i) * 2) * 2;
                    double z = packet.z.get(i) + (-packet.offz.get(i) + random.nextDouble() * packet.offz.get(i) * 2) * 2;
                    double offx = -packet.speed + random.nextDouble() * packet.speed * 2;
                    double offy = -packet.speed + random.nextDouble() * packet.speed * 2;
                    double offz = -packet.speed + random.nextDouble() * packet.speed * 2;

                    world.addParticle(PacketSystem.Particle.values()[packet.type].getTypes(),
                            x, y, z, offx * 2, offy * 2, offz * 2);
                }
            }
        }
    }
}
