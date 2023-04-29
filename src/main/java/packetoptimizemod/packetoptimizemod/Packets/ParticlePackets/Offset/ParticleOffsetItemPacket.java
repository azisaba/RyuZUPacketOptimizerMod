package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Offset;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleBasePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOffsetItemPacket extends ParticleOffsetPacket {
    public static final byte ID = 11;

    protected final int itemid;
    protected final int data;

    public ParticleOffsetItemPacket(int type, int count, float speed, int itemid, int data) {
        super(type, count, speed);
        this.itemid = itemid;
        this.data = data;
    }

    public ParticleOffsetItemPacket(int type, int count, float speed, int itemid, int data, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, count, speed, x, y, z, offx, offy, offz);
        this.itemid = itemid;
        this.data = data;
    }

    public boolean isSimilar(int type, int count, float speed, int itemid, int data) {
        return this.type == type && this.count == count && this.speed == speed && this.itemid == itemid && this.data == data;
    }

    public static void encode(ParticleOffsetItemPacket packet, FriendlyByteBuf buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.speed);
        buffer.writeInt(packet.itemid);
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

    public static ParticleOffsetItemPacket decode(FriendlyByteBuf buffer) {
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
        return new ParticleOffsetItemPacket(type, count, speed, blockid, data, x, y, z, offx, offy, offz);
    }


    public static void onMessageReceived(ParticleOffsetItemPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOffsetItemPacket packet) {
        var world = Minecraft.getInstance().level;
        if (world == null) return;
        ItemStack item = new ItemStack(Item.byId(packet.itemid));
        var nbt = item.getTag();
        if(nbt == null) nbt = new CompoundTag();
        nbt.putInt("CustomModelData" , packet.data);
        item.setTag(nbt);
        var particle = new ItemParticleOption(ParticleTypes.ITEM, item);
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
                            particle, true,
                            x, y, z, offx, offy, offz);
                }
            }
        }
    }
}
