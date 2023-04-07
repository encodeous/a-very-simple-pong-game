package ca.encodeous.pong.network.packets;

public enum PacketType {
    WIN,
    SCORE_UPDATE,
    OBJECT_UPDATE,
    OBJECT_REMOVE,
    OBJECT_ADD,
    END_TICK,
    ACCEL_UPDATE;
}
