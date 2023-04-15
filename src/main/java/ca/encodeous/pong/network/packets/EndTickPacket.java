package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Sent by the server to the client when the tick ends
 */
public class EndTickPacket implements SerializablePacket {
    @Override
    public PacketType getType() {
        return PacketType.END_TICK;
    }

    @Override
    public void deserialize(ByteBuffer buf) {
    }

    @Override
    public void serialize(ByteBuffer buf) {
    }
}
