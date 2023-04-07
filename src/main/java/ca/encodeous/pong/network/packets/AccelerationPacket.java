package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;
import java.util.UUID;

public class AccelerationPacket extends SerializablePacket {

    private double acceleration;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public PacketType getType() {
        return PacketType.ACCEL_UPDATE;
    }

    @Override
    public void deserialize(ByteBuffer buf) {
        id = new UUID(
                buf.getLong(),
                buf.getLong()
        );
        acceleration = buf.getDouble();
    }

    @Override
    public void serialize(ByteBuffer buf) {
        buf.putLong(id.getMostSignificantBits());
        buf.putLong(id.getLeastSignificantBits());
        buf.putDouble(acceleration);
    }
}
