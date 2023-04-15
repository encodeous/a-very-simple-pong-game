package ca.encodeous.pong.physics;

import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.UUID;

/**
 * A super abstract representation of any object in the game.
 * This is the minimal set of information transferred over the network or sent to the renderer.
 */
public abstract class GameEntity {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public abstract Rectangle2D getBounds();
    public abstract UUID getId();
}
