package ca.encodeous.pong.network.client;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.network.ConnectedEndpoint;
import ca.encodeous.pong.network.packets.*;
import ca.encodeous.pong.rendering.PongWindow;
import ca.encodeous.pong.system.Controller;
import ca.encodeous.pong.system.PlayerController;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.net.URI;
import java.nio.ByteBuffer;

public class PongClient {
    private WebSocketClient client;
    private ConnectedEndpoint endpoint;
    private Timer syncControlTimer;
    private PongWindow window;
    private PlayerController controller;
    public void connect(URI serverUri){
        client = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                window = new PongWindow();
                window.setVisible(true);
                controller = new PlayerController(window.getInputHandler());
                endpoint = new ConnectedEndpoint(client.getConnection(), controller);
                syncControlTimer = new Timer(1, (x)->{
                    window.preTick();
                    if(controller.getEntity() != null){
                        AccelerationPacket packet = new AccelerationPacket();
                        packet.setAcceleration(controller.getPaddleAccel());
                        packet.setId(controller.getEntity().getId());
                        endpoint.sendPacket(packet);
                    }
                });
                syncControlTimer.start();
                window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        destroy();
                    }
                });
            }

            @Override
            public void onMessage(String s) {
                System.out.println(s);
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                handlePacket(endpoint.parsePacket(bytes));
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                cleanup();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        };
        client.connect();
    }
    public void destroy(){
        cleanup();
    }
    private void cleanup(){
        client.close();
        if(syncControlTimer != null){
            syncControlTimer.stop();
        }
        window.setVisible(false);
        window.dispose();
    }
    private void handlePacket(SerializablePacket packet){
        if(packet instanceof PongObjectPacket){
            PongObjectPacket pop = (PongObjectPacket) packet;
            if(pop.getType() == PacketType.OBJECT_ADD){
                window.created(pop.getObjectInfo());
                if(pop.getObjectInfo().getId().equals(Constants.LEFT_PADDLE)){
                    // assign paddle to controller
                    controller.finishSubscription(pop.getObjectInfo());
                }
            }
            if(pop.getType() == PacketType.OBJECT_UPDATE){
                window.updated(pop.getObjectInfo());
            }
            if(pop.getType() == PacketType.OBJECT_REMOVE){
                window.removed(pop.getObjectInfo());
            }
        }
        if(packet instanceof ScoreUpdatePacket){
            ScoreUpdatePacket sup = (ScoreUpdatePacket) packet;
            window.scoreUpdate(sup.getScores());
        }
        if(packet instanceof EndTickPacket){
            window.postTick();
        }
        if(packet instanceof WinPacket){
            window.win(((WinPacket)packet).getPlayerId());
        }
    }
}
