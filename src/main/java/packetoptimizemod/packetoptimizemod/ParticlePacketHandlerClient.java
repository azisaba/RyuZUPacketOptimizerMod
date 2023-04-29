package packetoptimizemod.packetoptimizemod;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticlePacketHandlerClient {
    public static void onMessageReceived(ParticlePacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        if (sideReceived != LogicalSide.CLIENT) return;


        ctx.enqueueWork(() -> processMessage(packet));
    }

    private static void processMessage(ParticlePacket packet) {

    }
}
