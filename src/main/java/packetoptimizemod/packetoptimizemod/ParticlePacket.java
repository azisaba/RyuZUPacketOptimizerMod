package packetoptimizemod.packetoptimizemod;

import net.minecraft.network.FriendlyByteBuf;

public class ParticlePacket {
    public static final byte ID = 1;

    public ParticlePacket() {

    }

    public static void encode(ParticlePacket packet, FriendlyByteBuf buffer) {

    }

    public static ParticlePacket decode(FriendlyByteBuf buffer) {
        byte ID = buffer.readByte();
        int dataInt = buffer.readInt();
        return new ParticlePacket();
    }
}
