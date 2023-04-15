package ca.encodeous.pong.network.client;

import ca.encodeous.pong.physics.GameEntity;

import java.awt.geom.Rectangle2D;
import java.util.UUID;

/**
 * A fictitious entity that exists only on the client side as a shim to handle PongObjectPackets
 */
public class RemoteEntity extends GameEntity {

    public void setBounds(Rectangle2D bound) {
        this.bound = bound;
    }

    public RemoteEntity(Rectangle2D bound, UUID id) {
        this.bound = bound;
        this.id = id;
    }

    private Rectangle2D bound;
    private UUID id;

    @Override
    public Rectangle2D getBounds() {
        return bound;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
