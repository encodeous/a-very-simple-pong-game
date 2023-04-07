package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;

public abstract class SerializablePacket {
    public abstract PacketType getType();
    public abstract void deserialize(ByteBuffer buf);
    public abstract void serialize(ByteBuffer buf);
}
