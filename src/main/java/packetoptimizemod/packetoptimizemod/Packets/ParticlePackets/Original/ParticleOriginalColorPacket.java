package packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.Original;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleBasePacket;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleVectorPacket;
import packetoptimizemod.packetoptimizemod.Particles.ColorComposterParticle.ColorComposterParticleData;
import packetoptimizemod.packetoptimizemod.Particles.ColorCritParticle.ColorCritParticleData;
import packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle.ColorEndRodParticle;
import packetoptimizemod.packetoptimizemod.Particles.ColorEndRodParticle.ColorEndRodParticleData;
import packetoptimizemod.packetoptimizemod.Particles.ColorFireworkParticle.ColorFireworkParticleData;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlameParticle.ColorFlameParticleData;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle.ColorFlashParticle;
import packetoptimizemod.packetoptimizemod.Particles.ColorFlashParticle.ColorFlashParticleData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParticleOriginalColorPacket extends ParticleVectorPacket {
    public static final byte ID = 18;

    protected final int count;
    protected final float r;
    protected final float g;
    protected final float b;
    protected final float scale;


    public ParticleOriginalColorPacket(int type, int count, float r, float g, float b, float scale, float speed) {
        super(type, speed);
        this.count = count;
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
    }

    public ParticleOriginalColorPacket(int type, int count, float r, float g, float b, float scale, float speed, List<Double> x, List<Double> y, List<Double> z, List<Float> offx, List<Float> offy, List<Float> offz) {
        super(type, speed, x, y, z, offx, offy, offz);
        this.count = count;
        this.scale = scale;
        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public boolean isSimilar(int type, float speed, float r, float g, float b, float scale) {
        return this.type == type && this.speed == speed && this.r == r && this.g == g && this.b == b && this.scale == scale;
    }

    public boolean isSimilar(int type, float speed) {
        return this.type == type && this.speed == speed;
    }

    public static void encode(ParticleOriginalColorPacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.type);
        buffer.writeInt(packet.count);
        buffer.writeFloat(packet.r);
        buffer.writeFloat(packet.g);
        buffer.writeFloat(packet.b);
        buffer.writeFloat(packet.scale);
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

    public static ParticleOriginalColorPacket decode(PacketBuffer buffer) {
        int type = buffer.readInt();
        int count = buffer.readInt();
        float r = buffer.readFloat();
        float g = buffer.readFloat();
        float b = buffer.readFloat();
        float scale = buffer.readFloat();
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
        return new ParticleOriginalColorPacket(type, count, r, g, b, scale, speed, x, y, z, offx, offy, offz);
    }

    public static void onMessageReceived(ParticleOriginalColorPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(ParticleOriginalColorPacket packet) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null) return;
        for (int i = 0; i < packet.x.size(); i++) {
            if (packet.count == 0) {
                double x = packet.x.get(i);
                double y = packet.y.get(i);
                double z = packet.z.get(i);
                double offx = packet.offx.get(i) * packet.speed;
                double offy = packet.offy.get(i) * packet.speed;
                double offz = packet.offz.get(i) * packet.speed;

                if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                    switch (packet.type) {
                        case 0:
                            world.addParticle(new ColorFlashParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                            break;
                        case 1:
                            world.addParticle(new ColorFlameParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                            break;
                        case 2:
                            world.addParticle(new ColorEndRodParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                            break;
                        case 3:
                            world.addParticle(new ColorComposterParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                            break;
                        case 4:
                            world.addParticle(new ColorCritParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                            break;
                        case 5:
                            world.addParticle(new ColorFireworkParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                            break;
                    }

                }
            } else {
                for (int n = 0; n < packet.count; n++) {
                    double x = packet.x.get(i) + random.nextGaussian() * packet.offx.get(i);
                    double y = packet.y.get(i) + random.nextGaussian() * packet.offy.get(i);
                    double z = packet.z.get(i) + random.nextGaussian() * packet.offz.get(i);
                    double offx = random.nextGaussian() * packet.speed;
                    double offy = random.nextGaussian() * packet.speed;
                    double offz = random.nextGaussian() * packet.speed;

                    if (SettingScreen.drawingRate == 100 || random.nextInt(100) < SettingScreen.drawingRate) {
                        switch (packet.type) {
                            case 0:
                                world.addParticle(new ColorFlashParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                                break;
                            case 1:
                                world.addParticle(new ColorFlameParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z,offx, offy, offz);
                                break;
                            case 2:
                                world.addParticle(new ColorEndRodParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                                break;
                            case 3:
                                world.addParticle(new ColorComposterParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                                break;
                            case 4:
                                world.addParticle(new ColorCritParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                                break;
                            case 5:
                                world.addParticle(new ColorFireworkParticleData(packet.r, packet.g, packet.b, packet.scale), true, x, y, z, offx, offy, offz);
                                break;
                        }

                    }
                }
            }
        }
    }
}
