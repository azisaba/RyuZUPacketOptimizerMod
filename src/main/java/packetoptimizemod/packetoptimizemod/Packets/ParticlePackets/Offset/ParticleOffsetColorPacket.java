package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
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
    protected int count;

    public ParticleOffsetColorPacket(int type, int count, float r, float g, float b, float scale) {
        super(type, r, b, g, scale);
        this.count = count;
    }

    public ParticleOffsetColorPacket(int type, int count, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, r, g, b, scale, x, y, z);
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
        this.count = count;
    }

    public boolean isSimilar(float r, float g, float b, float scale) {
        return this.r == r && this.g == g && this.b == b && this.scale == scale;
    }

    public static void encode(ParticleOffsetColorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
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

    public static ParticleOffsetColorPacket decode(FriendlyByteBuf buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
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
        return new ParticleOffsetColorPacket(type, count, r, g, b, scale, x, y, z, offx, offy, offz);
    }

    public static void onMessageReceived(ParticleOffsetColorPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOffsetColorPacket packet) {
        var world = Minecraft.getInstance().level;
        if (world == null) return;
        var dust = new DustParticleOptions(new Vector3f(packet.r, packet.g, packet.b), packet.scale);
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < packet.count; n++) {
                if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                    double x = packet.x.get(i) + random.nextGaussian() * packet.offx.get(i);
                    double y = packet.y.get(i) + random.nextGaussian() * packet.offy.get(i);
                    double z = packet.z.get(i) + random.nextGaussian() * packet.offz.get(i);

                    world.addParticle(
                            dust,true,
                            x, y, z, 0, 0, 0);
                }
            }
        }
    }

    public void addVector(float x, float y, float z) {
        this.offx.add(x);
        this.offy.add(y);
        this.offz.add(z);
    }
}
