package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleCountPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleItemPacket extends ParticleCountPacket {
    public static final byte ID = 9;

    protected final int itemid;

    public ParticleItemPacket(int type, int count, float speed, int itemid) {
        super(type, count, speed);
        this.itemid = itemid;
    }

    public ParticleItemPacket(int type, int count, float speed, int itemid, List<Double> x, List<Double> y, List<Double> z) {
        super(type, count, speed, x, y, z);
        this.itemid = itemid;
    }

    public boolean isSimilar(int type, int count, float speed, int itemid) {
        return this.type == type && this.count == count && this.speed == speed && this.itemid == itemid;
    }

    public static void encode(ParticleItemPacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.speed);
        buffer.writeInt(packet.itemid);
        buffer.writeInt(packet.x.size());
        for (int i = 0; i < packet.x.size(); i++) {
            buffer.writeDouble(packet.x.get(i));
            buffer.writeDouble(packet.y.get(i));
            buffer.writeDouble(packet.z.get(i));
        }
    }

    public static ParticleItemPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float speed = buffer.readFloat();
        int blockid = buffer.readInt();
        int size = buffer.readInt();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<Double> z = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(buffer.readDouble());
            y.add(buffer.readDouble());
            z.add(buffer.readDouble());
        }
        return new ParticleItemPacket(type, count, speed, blockid, x, y, z);
    }

    public static void onMessageReceived(ParticleItemPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleItemPacket packet) {

        ClientWorld world = Minecraft.getInstance().world;
        int count = packet.count == 810 ? 1 : packet.count;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < count; n++) {
                double x = packet.x.get(i);
                double y = packet.y.get(i);
                double z = packet.z.get(i);
                double offx = -packet.speed + random.nextDouble() * packet.speed * 2;
                double offy = -packet.speed + random.nextDouble() * packet.speed * 2;
                double offz = -packet.speed + random.nextDouble() * packet.speed * 2;

                if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate || packet.count == 810) {
                    world.addParticle(
                            new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Item.getItemById(packet.itemid))),
                            x, y, z, offx * 2, offy * 2, offz * 2);
                }
            }
        }
    }
}