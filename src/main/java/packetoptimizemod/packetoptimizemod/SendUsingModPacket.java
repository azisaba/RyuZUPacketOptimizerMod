package packetoptimizemod.packetoptimizemod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendUsingModPacket {
    public static final byte ID = 0;
    private static final int version = 6;

    public static void encode(SendUsingModPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(version);
    }

    public static SendUsingModPacket decode(FriendlyByteBuf buffer) {
        return new SendUsingModPacket();
    }

    public static void onMessageReceived(SendUsingModPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
    }

    private static void processMessage(SendUsingModPacket packet) {
    }
}
