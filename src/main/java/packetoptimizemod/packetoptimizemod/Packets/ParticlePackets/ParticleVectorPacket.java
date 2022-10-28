package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.PacketSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ParticleVectorPacket extends ParticleBasePacket {
    public static final byte ID = 6;

    protected final float speed;
    protected List<Float> offx = new ArrayList<>();
    protected List<Float> offy = new ArrayList<>();
    protected List<Float> offz = new ArrayList<>();


    public ParticleVectorPacket(int type, float speed) {
        super(type);
        this.speed = speed;
    }

    public ParticleVectorPacket(int type, float speed, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, x, y, z);
        this.speed = speed;
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
    }

    public boolean isSimilar(int type, float speed) {
        return this.type == type && this.speed == speed;
    }

    public static void encode(ParticleVectorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
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

    public static ParticleVectorPacket decode(FriendlyByteBuf buffer) {
        int type = buffer.readInt();
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
        return new ParticleVectorPacket(type, speed, x, y, z, offx, offy, offz);
    }

    public static void onMessageReceived(ParticleVectorPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleVectorPacket packet) {
        var world = Minecraft.getInstance().level;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            double x = packet.x.get(i);
            double y = packet.y.get(i);
            double z = packet.z.get(i);
            double offx = packet.offx.get(i) * packet.speed;
            double offy = packet.offy.get(i) * packet.speed;
            double offz = packet.offz.get(i) * packet.speed;

            if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                world.addParticle(PacketSystem.Particle.values()[packet.type].getTypes(),true,
                        x, y, z, offx, offy, offz);
            }
        }
    }

    public void addVector(float x, float y, float z) {
        this.offx.add(x);
        this.offy.add(y);
        this.offz.add(z);
    }
}
