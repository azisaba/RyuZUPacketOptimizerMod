package packetoptimizemod.packetoptimizemod.Packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.GUI.SettingScreen;

import java.util.function.Supplier;

public class SetParticleDrawingRatePacket {
    public static final byte ID = 15;

    protected final int value;

    public SetParticleDrawingRatePacket(int value) {
        this.value = value;
    }

    public static void encode(SetParticleDrawingRatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeByte(ID);
        buffer.writeInt(packet.value);
    }

    public static SetParticleDrawingRatePacket decode(FriendlyByteBuf buffer) {
        int value = buffer.readInt();
        return new SetParticleDrawingRatePacket(value);
    }

    public static void onMessageReceived(SetParticleDrawingRatePacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    private static void processMessage(SetParticleDrawingRatePacket packet) {
        SettingScreen.drawingRate = packet.value;
        SettingScreen.write();
    }
}
