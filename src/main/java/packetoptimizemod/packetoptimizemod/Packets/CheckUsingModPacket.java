package packetoptimizemod.packetoptimizemod.Packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.ParticleChannnel;
import packetoptimizemod.packetoptimizemod.SendUsingModPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CheckUsingModPacket {
    public static final byte ID = 17;

    public static void encode(CheckUsingModPacket packet, FriendlyByteBuf buffer) {
        buffer.writeByte(ID);
    }

    public static CheckUsingModPacket decode(FriendlyByteBuf buffer) {
        return new CheckUsingModPacket();
    }

    public static void onMessageReceived(CheckUsingModPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();

        ctx.enqueueWork(() -> processMessage(packet));
        ctx.setPacketHandled(true);
    }

    public static void processMessage(CheckUsingModPacket packet) {
        ParticleChannnel.sendPacket(new SendUsingModPacket());
    }
}
