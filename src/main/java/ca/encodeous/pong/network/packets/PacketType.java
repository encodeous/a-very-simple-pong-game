package ca.encodeous.pong.network.packets;

/**
 * An enum to represent all the packets required to synchronize the game.
 */
public enum PacketType {
    WIN,
    SCORE_UPDATE,
    OBJECT_UPDATE,
    OBJECT_REMOVE,
    OBJECT_ADD,
    END_TICK,
    ACCEL_UPDATE;
}
