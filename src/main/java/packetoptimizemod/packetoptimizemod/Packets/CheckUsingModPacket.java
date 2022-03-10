package packetoptimizemod.packetoptimizemod.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import packetoptimizemod.packetoptimizemod.Packets.ParticlePackets.ParticleCompressionPacket;
import packetoptimizemod.packetoptimizemod.ParticleChannnel;
import packetoptimizemod.packetoptimizemod.SendUsingModPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CheckUsingModPacket {
    public static final byte ID = 17;

    public static void encode(CheckUsingModPacket packet, PacketBuffer buffer) {
        buffer.writeByte(ID);
    }

    public static CheckUsingModPacket decode(PacketBuffer buffer) {
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
