package packetoptimizemod.packetoptimizemod;

import net.minecraft.network.PacketBuffer;

public class ParticlePacket {
    public static final byte ID = 1;

    public ParticlePacket() {

    }

    public static void encode(ParticlePacket packet, PacketBuffer buffer) {

    }

    public static ParticlePacket decode(PacketBuffer buffer) {
        byte ID = buffer.readByte();
        int dataInt = buffer.readInt();
        return new ParticlePacket();
    }
}
