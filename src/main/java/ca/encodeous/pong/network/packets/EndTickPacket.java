package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;
import java.util.UUID;

public class EndTickPacket extends SerializablePacket {
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
