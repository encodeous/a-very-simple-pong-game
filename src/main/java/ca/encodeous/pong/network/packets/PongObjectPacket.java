package ca.encodeous.pong.network.packets;

import ca.encodeous.pong.network.client.RemoteEntity;
import ca.encodeous.pong.physics.GameEntity;

import java.awt.geom.Rectangle2D;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class PongObjectPacket implements SerializablePacket {
    private static final ConcurrentHashMap<UUID, GameEntity> entityCache = new ConcurrentHashMap<>();
    public PongObjectPacket(PacketType type) {
        this.type = type;
    }

    public GameEntity getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(GameEntity objectInfo) {
        this.objectInfo = objectInfo;
    }

    private GameEntity objectInfo;
    private final PacketType type;

    @Override
    public PacketType getType() {
        return type;
    }

    @Override
    public void deserialize(ByteBuffer buf) {
        UUID id = new UUID(
                buf.getLong(),
                buf.getLong()
        );
        Rectangle2D bound = new Rectangle2D.Double(
                buf.getDouble(),
                buf.getDouble(),
                buf.getDouble(),
                buf.getDouble()
        );
        if(entityCache.containsKey(id)){
            ((RemoteEntity)entityCache.get(id)).setBounds(bound);
        }else{
            entityCache.put(id, new RemoteEntity(bound, id));
        }
        objectInfo = entityCache.get(id);
    }

    @Override
    public void serialize(ByteBuffer buf) {
        buf.putLong(objectInfo.getId().getMostSignificantBits());
        buf.putLong(objectInfo.getId().getLeastSignificantBits());
        Rectangle2D bound = objectInfo.getBounds();
        buf.putDouble(bound.getX());
        buf.putDouble(bound.getY());
        buf.putDouble(bound.getWidth());
        buf.putDouble(bound.getHeight());
    }
}
