package packetoptimizemod.packetoptimizemod;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PacketOptimizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SendUsingModPacket {
    public static final byte ID = 0;
    private static final int version = 1;

    public static void encode(SendUsingModPacket packet, PacketBuffer buffer) {
        buffer.writeInt(version);
    }

    public static SendUsingModPacket decode(PacketBuffer buffer) {
        return new SendUsingModPacket();
    }

    public static void onMessageReceived(SendUsingModPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
    }

    private static void processMessage(SendUsingModPacket packet) {
    }

    @SubscribeEvent
    public static void sendUsingModPacket(AttackEntityEvent e) {
        //if (e.getWorld().getServer() == null) return;
        //if (!e.getWorld().getServer().getServerHostname().equals("azisaba.net") || !e.getWorld().getServer().getServerHostname().equals("localhost")) return;
        ParticleChannnel.sendPacket(new SendUsingModPacket());
    }

    @SubscribeEvent
    public static void sendUsingModPacket(EntityJoinWorldEvent e) {
        //if (e.getWorld().getServer() == null) return;
        //if (!e.getWorld().getServer().getServerHostname().equals("azisaba.net") || !e.getWorld().getServer().getServerHostname().equals("localhost")) return;
        ParticleChannnel.sendPacket(new SendUsingModPacket());
    }

    @SubscribeEvent
    public static void sendUsingModPacket(PlayerEvent.Clone e) {
        //if (e.getWorld().getServer() == null) return;
        //if (!e.getWorld().getServer().getServerHostname().equals("azisaba.net") || !e.getWorld().getServer().getServerHostname().equals("localhost")) return;
        ParticleChannnel.sendPacket(new SendUsingModPacket());
    }

    @SubscribeEvent
    public static void sendUsingModPacket(PlayerEvent.PlayerLoggedInEvent e) {
        //if (e.getWorld().getServer() == null) return;
        //if (!e.getWorld().getServer().getServerHostname().equals("azisaba.net") || !e.getWorld().getServer().getServerHostname().equals("localhost")) return;
        ParticleChannnel.sendPacket(new SendUsingModPacket());
    }
}
