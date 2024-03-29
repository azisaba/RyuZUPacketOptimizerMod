package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Force;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleColorPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleForceColorPacket extends ParticleColorPacket {
    public static final byte ID = 14;

    public ParticleForceColorPacket(int type, float r, float g, float b, float scale) {
        super(type, r, b, g, scale);
    }

    public ParticleForceColorPacket(int type, float r, float g, float b, float scale, List<Double> x, List<Double> y, List<Double> z) {
        super(type, r, b, g, scale, x, y, z);
    }

    public static ParticleForceColorPacket decode(FriendlyByteBuf buffer) {
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
        return new ParticleForceColorPacket(type, r, g, b, scale, x, y, z);
    }


    public static void onMessageReceived(ParticleForceColorPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleForceColorPacket packet) {

        var world = Minecraft.getInstance().level;
        if(world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            double x = packet.x.get(i);
            double y = packet.y.get(i);
            double z = packet.z.get(i);

            world.addParticle(
                    new DustParticleOptions(new Vector3f(packet.r, packet.g, packet.b), packet.scale), true,
                    x, y, z, 0, 0, 0);
        }
    }
}
