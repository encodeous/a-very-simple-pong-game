package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;

/**
 * An interface that represents a packet that can be sent and received over the network.
 */
public interface SerializablePacket {
    PacketType getType();
    void deserialize(ByteBuffer buf);
    void serialize(ByteBuffer buf);
}
