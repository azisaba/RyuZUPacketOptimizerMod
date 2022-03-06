package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset;

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
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleBasePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOffsetItemPacket extends ParticleOffsetPacket {
    public static final byte ID = 11;

    protected final int itemid;

    public ParticleOffsetItemPacket(int type, int count, float speed, int itemid) {
        super(type, count, speed);
        this.itemid = itemid;
    }

    public ParticleOffsetItemPacket(int type, int count, float speed, int itemid, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, count, speed, x, y, z, offx, offy, offz);
        this.itemid = itemid;
    }

    public boolean isSimilar(int type, int count, float speed, int itemid) {
        return this.type == type && this.count == count && this.speed == speed && this.itemid == itemid;
    }

    public static void encode(ParticleOffsetItemPacket packet, PacketBuffer buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.speed);
        buffer.writeInt(packet.itemid);
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

    public static ParticleOffsetItemPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float speed = buffer.readFloat();
        int blockid = buffer.readInt();
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
        return new ParticleOffsetItemPacket(type, count, speed, blockid, x, y, z, offx, offy, offz);
    }


    public static void onMessageReceived(ParticleOffsetItemPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOffsetItemPacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        int count = packet.count == 810 ? 1 : packet.count;
        for (int i = 0; i < packet.x.size(); i++) {
            for (int n = 0; n < count; n++) {
                if (SettingScreen.drawingRate == 100 || ParticleBasePacket.random.nextInt(100) < SettingScreen.drawingRate || packet.count == 810) {
                    double x = packet.x.get(i) - packet.offx.get(i) + ParticleBasePacket.random.nextDouble() * packet.offx.get(i) * 2;
                    double y = packet.y.get(i) - packet.offy.get(i) + ParticleBasePacket.random.nextDouble() * packet.offy.get(i) * 2;
                    double z = packet.z.get(i) - packet.offz.get(i) + ParticleBasePacket.random.nextDouble() * packet.offz.get(i) * 2;
                    double offx = -packet.speed + ParticleBasePacket.random.nextDouble() * packet.speed * 2;
                    double offy = -packet.speed + ParticleBasePacket.random.nextDouble() * packet.speed * 2;
                    double offz = -packet.speed + ParticleBasePacket.random.nextDouble() * packet.speed * 2;

                    world.addParticle(
                            new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Item.getItemById(packet.itemid))),
                            x, y, z, offx * 2, offy * 2, offz * 2);
                }
            }
        }
    }
}
