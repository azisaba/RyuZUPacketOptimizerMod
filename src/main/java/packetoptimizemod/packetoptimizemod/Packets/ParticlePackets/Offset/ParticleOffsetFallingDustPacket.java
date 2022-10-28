package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleBasePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOffsetFallingDustPacket extends ParticleOffsetPacket {
    public static final byte ID = 12;

    protected final int blockid;
    protected final int data;

    public ParticleOffsetFallingDustPacket(int type, int count, float speed, int blockid, int data) {
        super(type, count, speed);
        this.blockid = blockid;
        this.data = data;
    }

    public ParticleOffsetFallingDustPacket(int type, int count, float speed, int blockid, int data, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, count, speed, x, y, z, offx, offy, offz);
        this.blockid = blockid;
        this.data = data;
    }

    public boolean isSimilar(int type, int count, float speed, int blockid, int data) {
        return this.type == type && this.count == count && this.speed == speed && this.blockid == blockid && this.data == data;
    }

    public static void encode(ParticleOffsetFallingDustPacket packet, FriendlyByteBuf buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.speed);
        buffer.writeInt(packet.blockid);
        buffer.writeInt(packet.data);
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

    public static ParticleOffsetFallingDustPacket decode(FriendlyByteBuf buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float speed = buffer.readFloat();
        int blockid = buffer.readInt();
        int data = buffer.readInt();
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


        return new ParticleOffsetFallingDustPacket(type, count, speed, blockid, data, x, y, z, offx, offy, offz);
    }

    public static void onMessageReceived(ParticleOffsetFallingDustPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOffsetFallingDustPacket packet) {
        var world = Minecraft.getInstance().level;
        if (world == null) return;
        int count = packet.count == 810 ? 1 : packet.count;
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < count; n++) {
                if (SettingScreen.drawingRate == 100 || ParticleBasePacket.random.nextInt(100) < SettingScreen.drawingRate || packet.count == 810) {
                    double x = packet.x.get(i) + random.nextGaussian() * packet.offx.get(i);
                    double y = packet.y.get(i) + random.nextGaussian() * packet.offy.get(i);
                    double z = packet.z.get(i) + random.nextGaussian() * packet.offz.get(i);
                    double offx = random.nextGaussian() * packet.speed;
                    double offy = random.nextGaussian() * packet.speed;
                    double offz = random.nextGaussian() * packet.speed;

                    world.addParticle(
                            new BlockParticleOption(ParticleTypes.FALLING_DUST, Block.stateById(packet.blockid)), true,
                            x, y, z, offx, offy, offz);
                }
            }
        }
    }
}
