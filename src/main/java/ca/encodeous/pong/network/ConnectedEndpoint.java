package ca.encodeous.pong.network;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.network.packets.PacketType;
import ca.encodeous.pong.network.packets.PacketRegistry;
import ca.encodeous.pong.network.packets.SerializablePacket;
import ca.encodeous.pong.system.Controller;
import org.java_websocket.WebSocket;

import java.nio.ByteBuffer;

/**
 * A universal wrapper that is used both on the client and server side to encapsulate the methods and data required to maintain a connection with a remote party
 */
public class ConnectedEndpoint {
    private final ByteBuffer sendBuffer = ByteBuffer.allocate(Constants.MAX_PACKET_SIZE);
    public ConnectedEndpoint(WebSocket socket, Controller controller) {
        this.socket = socket;
        this.controls = controller;
    }
    public Controller getControls() {
        return controls;
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void sendPacket(SerializablePacket packet){
        String id = packet.getType().name();
        byte[] idBytes = id.getBytes();
        sendBuffer.clear();
        sendBuffer.putInt(idBytes.length);
        sendBuffer.put(idBytes);
        packet.serialize(sendBuffer);
        sendBuffer.limit(sendBuffer.position());
        sendBuffer.position(0);
        socket.send(sendBuffer);
    }

    public SerializablePacket parsePacket(ByteBuffer buf){
        int idLen = buf.getInt();
        byte[] strBytes = new byte[idLen];
        buf.get(strBytes);
        String id = new String(strBytes);
        PacketType type = PacketType.valueOf(id);
        SerializablePacket packet = PacketRegistry.mapPacket(type);
        packet.deserialize(buf);
        return packet;
    }

    private WebSocket socket;
    private Controller controls;
}
