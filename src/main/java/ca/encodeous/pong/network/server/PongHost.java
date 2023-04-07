package ca.encodeous.pong.network.server;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.network.ConnectedEndpoint;
import ca.encodeous.pong.network.RemoteController;
import ca.encodeous.pong.network.packets.*;
import ca.encodeous.pong.physics.GameEntity;
import ca.encodeous.pong.rendering.PongWindow;
import ca.encodeous.pong.system.GameSystemEvents;
import ca.encodeous.pong.system.PlayerController;
import ca.encodeous.pong.system.PongSystem;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class PongHost {
    private WebSocketServer server;
    private ConnectedEndpoint client;
    private PongSystem system;
    private PongWindow window;
    public String accept(){
        destroy();
        InetSocketAddress addr = new InetSocketAddress(Constants.PROTOCOL_PORT);
        server = new WebSocketServer(addr) {
            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
                if(client != null){
                    webSocket.closeConnection(0, "");
                    return;
                }
                client = new ConnectedEndpoint(webSocket, new RemoteController());
                createInstance();
            }

            @Override
            public void onClose(WebSocket webSocket, int i, String s, boolean b) {
                if(b && client != null && webSocket == client.getSocket()) destroyInstance();
            }

            @Override
            public void onMessage(WebSocket webSocket, String s) {
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteBuffer s) {
                if(webSocket != client.getSocket()) return;
                SerializablePacket packet = client.parsePacket(s);
                handlePacket(packet);
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
                destroyInstance();
            }

            @Override
            public void onStart() {

            }
        };
        server.start();
        try {
            return InetAddress.getLocalHost().getHostAddress() + ":" + Constants.PROTOCOL_PORT;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePacket(SerializablePacket packet){
        if(packet instanceof AccelerationPacket){
            ((RemoteController)client.getControls())
                    .setAccel(((AccelerationPacket)packet).getAcceleration());
        }
    }

    public void destroy(){
        if(server != null){
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void destroyInstance(){
        if(client != null){
            client.getSocket().close();
            client = null;
            window.setVisible(false);
            window.dispose();
            system.stopGame();
        }
    }
    private void createInstance(){
        window = new PongWindow();
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                destroyInstance();
            }
        });
        PlayerController playerControl = new PlayerController(window.getInputHandler());
        system = new PongSystem(client.getControls(), playerControl, new GameSystemEvents() {
            @Override
            public void preTick() {

            }

            @Override
            public void postTick() {
                client.sendPacket(new EndTickPacket());
            }

            @Override
            public void win(int playerId) {
                WinPacket pkt = new WinPacket();
                pkt.setPlayerId(playerId);
                client.sendPacket(pkt);
            }

            @Override
            public void scoreUpdate(int[] playerScores) {
                ScoreUpdatePacket packet = new ScoreUpdatePacket();
                packet.setScores(playerScores);
                client.sendPacket(packet);
            }

            @Override
            public void updated(GameEntity object) {
                PongObjectPacket packet = new PongObjectPacket(PacketType.OBJECT_UPDATE);
                packet.setObjectInfo(object);
                client.sendPacket(packet);
            }

            @Override
            public void created(GameEntity object) {
                PongObjectPacket packet = new PongObjectPacket(PacketType.OBJECT_ADD);
                packet.setObjectInfo(object);
                client.sendPacket(packet);
            }

            @Override
            public void removed(GameEntity object) {
                PongObjectPacket packet = new PongObjectPacket(PacketType.OBJECT_REMOVE);
                packet.setObjectInfo(object);
                client.sendPacket(packet);
            }
        }, window);
        system.startGame();
        playerControl.finishSubscription(system.getRightPaddle());
        window.setVisible(true);
    }
}
