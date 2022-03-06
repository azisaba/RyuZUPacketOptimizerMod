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
import java.util.Random;
import java.util.function.Supplier;

public class ParticleBasePacket {
    public static final byte ID = 1;

    protected static final Random random = new Random();
    protected final int type;

    protected List<Double> x = new ArrayList<>();
    protected List<Double> y = new ArrayList<>();
    protected List<Double> z = new ArrayList<>();

    public ParticleBasePacket(int type) {
        this.type = type;
    }

    public ParticleBasePacket(int type, List<Double> x, List<Double> y, List<Double> z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void encode(ParticleBasePacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.x.size());
        for (int i = 0; i < packet.x.size(); i++) {
            buffer.writeDouble(packet.x.get(i));
            buffer.writeDouble(packet.y.get(i));
            buffer.writeDouble(packet.z.get(i));
        }
    }

    public boolean isSimilar(int type) {
        return this.type == type;
    }

    public static ParticleBasePacket decode(PacketBuffer buffer) {
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

        return new ParticleBasePacket(type, x, y, z);
    }

    public static void onMessageReceived(ParticleBasePacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleBasePacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            double x = packet.x.get(i);
            double y = packet.y.get(i);
            double z = packet.z.get(i);

            if(SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {

                world.addParticle(PacketSystem.Particle.values()[packet.type].getTypes(),
                        x, y, z, 0, 0, 0);

            }
        }
    }

    public int getType() {
        return type;
    }
}
