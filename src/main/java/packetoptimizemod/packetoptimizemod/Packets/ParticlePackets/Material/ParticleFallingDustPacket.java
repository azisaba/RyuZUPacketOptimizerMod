package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Material;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.PacketOptimizeMod;
import packetoptimizemod.packetoptimizemod.PacketSystem;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleCountPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleFallingDustPacket extends ParticleCountPacket {
    public static final byte ID = 8;

    protected final int blockid;
    protected final int data;

    public ParticleFallingDustPacket(int type, int count, float speed, int blockid, int data) {
        super(type, count, speed);
        this.blockid = blockid;
        this.data = data;
    }

    public ParticleFallingDustPacket(int type, int count, float speed, int blockid,int data, List<Double> x, List<Double> y, List<Double> z) {
        super(type, count, speed, x, y, z);
        this.blockid = blockid;
        this.data = data;
    }

    public boolean isSimilar(int type, int count, float speed, int blockid,int data) {
        return this.type == type && this.count == count && this.speed == speed && this.blockid == blockid && this.data == data;
    }

    public static void encode(ParticleFallingDustPacket packet, FriendlyByteBuf buffer) {
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

    public static ParticleFallingDustPacket decode(FriendlyByteBuf buffer) {
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
        return new ParticleFallingDustPacket(type, count, speed, blockid,data, x, y, z);
    }

    public static void onMessageReceived(ParticleFallingDustPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleFallingDustPacket packet) {
        var world = Minecraft.getInstance().level;
        if (world == null) return;
        var falling_dust = new BlockParticleOption(ParticleTypes.FALLING_DUST, PacketSystem.MaterialTypes.values()[packet.blockid].getBlockState());
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
                            falling_dust,true,
                            x, y, z, offx, offy, offz);
                }
            }
        }
    }
}
