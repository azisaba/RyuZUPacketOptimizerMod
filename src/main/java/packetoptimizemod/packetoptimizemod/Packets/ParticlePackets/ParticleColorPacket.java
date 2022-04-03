package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlameParticle.ColorFlameParticleData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleColorPacket extends ParticleBasePacket {
    public static final byte ID = 3;

    protected final float r;
    protected final float g;
    protected final float b;
    protected final float scale;


    public ParticleColorPacket(int type, float r, float g, float b, float scale) {
        super(type);
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public ParticleColorPacket(int type, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z) {
        super(type, x, y, z);
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public boolean isSimilar(float r, float g, float b, float scale) {
        return this.r == r && this.g == g && this.b == b && this.scale == scale;
    }

    public static void encode(ParticleColorPacket packet, PacketBuffer buffer) {
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
        }
    }

    public static ParticleColorPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        float r = buffer.readFloat();
        float g = buffer.readFloat();
        float b = buffer.readFloat();
        float scale = buffer.readFloat();
        int size = buffer.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buffer.readDouble());
            y.add(buffer.readDouble());
            z.add(buffer.readDouble());
        }
        return new ParticleColorPacket(type, r, g, b, scale, x, y, z);
    }

    public static void onMessageReceived(ParticleColorPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleColorPacket packet) {

        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        RedstoneParticleData dust = new RedstoneParticleData(packet.r, packet.g, packet.b, packet.scale);
        for (int i = 0; i < packet.x.size(); i++) {
            double x = packet.x.get(i);
            double y = packet.y.get(i);
            double z = packet.z.get(i);

            if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                world.addParticle(dust, x, y, z, 0, 0, 0);
            }
        }
    }
}
