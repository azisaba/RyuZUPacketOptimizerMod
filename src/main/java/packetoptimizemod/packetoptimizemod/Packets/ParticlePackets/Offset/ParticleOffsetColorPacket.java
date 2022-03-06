package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleColorPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOffsetColorPacket extends ParticleColorPacket {
    public static final byte ID = 13;

    protected List<Float> offx = new ArrayList<>();
    protected List<Float> offy = new ArrayList<>();
    protected List<Float> offz = new ArrayList<>();

    public ParticleOffsetColorPacket(int type, float r, float g, float b, float scale) {
        super(type, r, b, g, scale);
    }

    public ParticleOffsetColorPacket(int type, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, r, g, b, scale, x, y, z);
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
    }

    public boolean isSimilar(float r, float g, float b, float scale) {
        return this.r == r && this.g == g && this.b == b && this.scale == scale;
    }

    public static void encode(ParticleOffsetColorPacket packet, PacketBuffer buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.type);
        buffer.writeFloat(packet.r);
        buffer.writeFloat(packet.g);
        buffer.writeFloat(packet.b);
        buffer.writeFloat(packet.scale);
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

    public static ParticleOffsetColorPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        float r = buffer.readFloat();
        float g = buffer.readFloat();
        float b = buffer.readFloat();
        float scale = buffer.readFloat();
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
        return new ParticleOffsetColorPacket(type, r, g, b, scale, x, y, z, offx, offy, offz);
    }

    public static void onMessageReceived(ParticleOffsetColorPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOffsetColorPacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                double x = packet.x.get(i) - packet.offx.get(i) + random.nextDouble() * packet.offx.get(i) * 2;
                double y = packet.y.get(i) - packet.offy.get(i) + random.nextDouble() * packet.offy.get(i) * 2;
                double z = packet.z.get(i) - packet.offz.get(i) + random.nextDouble() * packet.offz.get(i) * 2;

                world.addParticle(
                        new RedstoneParticleData(packet.r, packet.g, packet.b, packet.scale),
                        x, y, z, 0, 0, 0);
            }
        }
    }

    public void addVector(float x, float y, float z) {
        this.offx.add(x);
        this.offy.add(y);
        this.offz.add(z);
    }
}
