package ca.encodeous.pong.network.packets;

/**
 * Register packets to map the packet type to a new object of the type of the packet.
 */
public class PacketRegistry {
    public static SerializablePacket mapPacket(PacketType type){
        switch (type){
            case WIN:
                return new WinPacket();
            case OBJECT_ADD:
            case OBJECT_REMOVE:
            case OBJECT_UPDATE:
                return new PongObjectPacket(type);
            case ACCEL_UPDATE:
                return new AccelerationPacket();
            case SCORE_UPDATE:
                return new ScoreUpdatePacket();
            case END_TICK:
                return new EndTickPacket();
        }
        return null;
    }
}
