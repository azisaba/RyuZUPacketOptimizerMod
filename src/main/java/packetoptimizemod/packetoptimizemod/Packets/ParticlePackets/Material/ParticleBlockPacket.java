package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleCountPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleBlockPacket extends ParticleCountPacket {
    public static final byte ID = 2;

    protected final int blockid;
    protected final int data;

    public ParticleBlockPacket(int type, int count, float speed, int blockid, int data) {
        super(type, count, speed);
        this.blockid = blockid;
        this.data = data;
    }

    public ParticleBlockPacket(int type, int count, float speed, int blockid, int data, List<Double> x, List<Double> y, List<Double> z) {
        super(type, count, speed, x, y, z);
        this.blockid = blockid;
        this.data = data;
    }

    public boolean isSimilar(int type, int count, float speed, int blockid, int data) {
        return this.type == type && this.count == count && this.speed == speed && this.blockid == blockid && this.data == data;
    }

    public static void encode(ParticleBlockPacket packet, PacketBuffer buffer) {
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
        }
    }

    public static ParticleBlockPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float speed = buffer.readFloat();
        int blockid = buffer.readInt();
        int data = buffer.readInt();
        int size = buffer.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buffer.readDouble());
            y.add(buffer.readDouble());
            z.add(buffer.readDouble());
        }
        return new ParticleBlockPacket(type, count, speed, blockid,data, x, y, z);
    }

    public static void onMessageReceived(ParticleBlockPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleBlockPacket packet) {

        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        BlockParticleData block = new BlockParticleData(ParticleTypes.BLOCK, Block.getBlockFromItem(Item.getItemById(packet.blockid)).getDefaultState());
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < packet.count; n++) {
                double x = packet.x.get(i);
                double y = packet.y.get(i);
                double z = packet.z.get(i);
                double offx = random.nextGaussian() * packet.speed;
                double offy = random.nextGaussian() * packet.speed;
                double offz = random.nextGaussian() * packet.speed;

                if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                    world.addParticle(
                            block, true,
                            x, y, z, offx, offy, offz);
                }
            }
        }
    }
}
