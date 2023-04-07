package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;

public class WinPacket extends SerializablePacket {
    @Override
    public PacketType getType() {
        return PacketType.WIN;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    private int playerId;

    @Override
    public void deserialize(ByteBuffer buf) {
        playerId = buf.getInt();
    }

    @Override
    public void serialize(ByteBuffer buf) {
        buf.putInt(playerId);
    }
}
