package ca.encodeous.pong.gui;

import ca.encodeous.pong.physics.Vec2d;
import ca.encodeous.pong.rendering.PongWindow;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class responsible for handling input
 */
public class InputHandler {
    private Vec2d mousePosition = new Vec2d(0, 0);
    private boolean isMouseDown;
    private HashSet<Integer> keysDown = new HashSet<>();
    private HashMap<Runnable, Integer> keyDown = new HashMap<>();
    private HashMap<Runnable, Integer> keyUp = new HashMap<>();
    private HashMap<Runnable, Integer> keyTick = new HashMap<>();
    private HashSet<Consumer<Vec2d>> mouseTick = new HashSet<>();
    public void subscribeKeyDown(int code, Runnable callback){
        keyDown.put(callback, code);
    }
    public void subscribeKeyUp(int code, Runnable callback){
        keyUp.put(callback, code);
    }
    public void subscribeKeyTick(int code, Runnable callback){
        keyTick.put(callback, code);
    }
    public void subscribeMouseTick(Consumer<Vec2d> event){
        mouseTick.add(event);
    }
    public void unsubscribeKeyDown(Runnable callback){
        keyDown.remove(callback);
    }
    public void unsubscribeKeyUp(Runnable callback){
        keyUp.remove(callback);
    }
    public void unsubscribeKeyTick(Runnable callback){
        keyTick.remove(callback);
    }
    public void unsubscribeMouseTick(Consumer<Vec2d> event){
        mouseTick.remove(event);
    }

    public void preTick(){
        for(Map.Entry<Runnable, Integer> val : keyTick.entrySet()){
            if(keysDown.contains(val.getValue())){
                val.getKey().run();
            }
        }
        if(isMouseDown){
            for(Consumer<Vec2d> val : mouseTick){
                val.accept(mousePosition.multiply(1 / PongWindow.S_FACTOR));
            }
        }
    }
    public void addTo(Component component){
        component.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                keysDown.add(e.getKeyCode());
                for(Map.Entry<Runnable, Integer> val : keyDown.entrySet()){
                    if(val.getValue() == e.getKeyCode()){
                        val.getKey().run();
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                keysDown.remove(e.getKeyCode());
                for(Map.Entry<Runnable, Integer> val : keyUp.entrySet()){
                    if(val.getValue() == e.getKeyCode()){
                        val.getKey().run();
                    }
                }
            }
        });
        component.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mousePosition = new Vec2d(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        component.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                isMouseDown = true;
                mousePosition = new Vec2d(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMouseDown = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
