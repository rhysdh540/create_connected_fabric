package com.hlysine.create_connected;

import com.hlysine.create_connected.content.contraption.jukebox.PlayContraptionJukeboxPacket;
import com.hlysine.create_connected.content.sequencedpulsegenerator.ConfigureSequencedPulseGeneratorPacket;
import io.github.fabricators_of_create.porting_lib.util.NetworkDirection;
import me.pepperbell.simplenetworking.SimpleChannel;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.function.Function;

import static io.github.fabricators_of_create.porting_lib.util.NetworkDirection.PLAY_TO_CLIENT;
import static io.github.fabricators_of_create.porting_lib.util.NetworkDirection.PLAY_TO_SERVER;


public enum CCPackets {
    CONFIGURE_SEQUENCER(ConfigureSequencedPulseGeneratorPacket.class, PLAY_TO_SERVER),
    PLAY_CONTRAPTION_JUKEBOX(PlayContraptionJukeboxPacket.class, PLAY_TO_CLIENT);

    public static final ResourceLocation CHANNEL_NAME = CreateConnected.asResource("main");
    public static final int NETWORK_VERSION = 1;
    public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
    private static SimpleChannel channel;

    private final PacketType<?> packetType;

    <T extends SimplePacketBase> CCPackets(Class<T> type, NetworkDirection direction) {
        packetType = new PacketType<>(type, direction);
    }

    public static void registerPackets() {
        channel = new SimpleChannel(CHANNEL_NAME);
//                .serverAcceptedVersions(NETWORK_VERSION_STR::equals)
//                .clientAcceptedVersions(NETWORK_VERSION_STR::equals)
//                .networkProtocolVersion(() -> NETWORK_VERSION_STR)
//                .simpleChannel();

        for (CCPackets packet : values())
            packet.packetType.register();
    }

    public static SimpleChannel getChannel() {
        return channel;
    }

    public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
        getChannel().send(
                PacketDistributor.NEAR.with(TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), range, world.dimension())),
                message);
    }

    private record PacketType<T extends SimplePacketBase>(Class<T> type, NetworkDirection direction) {
        private static int index = 0;

        private void register() {
                if(direction == PLAY_TO_SERVER)
                    getChannel().registerC2SPacket(type, index++);
                else
                    getChannel().registerS2CPacket(type, index++);
            }
        }

}

