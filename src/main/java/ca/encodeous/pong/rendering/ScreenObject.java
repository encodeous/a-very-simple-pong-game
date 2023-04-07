package ca.encodeous.pong.rendering;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.physics.GameEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ScreenObject extends JComponent {
    Color c = Color.white;

    public GameEntity getLastEntity() {
        return lastEntity;
    }

    private GameEntity lastEntity;
    @Override
    public void paint(Graphics g) {
        if(lastEntity.getId().equals(Constants.BALL_ID)){
            // render ball as circle
            g.setColor(Color.red);
            g.fillOval(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight());
        } else if(lastEntity.getId().equals(Constants.LEFT_PADDLE)
                || lastEntity.getId().equals(Constants.RIGHT_PADDLE)){
            // render paddle
            g.setColor(Color.white);
            g.fillRoundRect(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight(), 4, 4);
        }
        else{
            g.setColor(c);
            g.fillRect(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight());
        }
    }
    public void sync(GameEntity object){
        lastEntity = object;
        Rectangle2D bound = object.getBounds();
        bound = new Rectangle2D.Double(
                bound.getX() * PongWindow.S_FACTOR,
                bound.getY() * PongWindow.S_FACTOR,
                bound.getWidth() * PongWindow.S_FACTOR,
                bound.getHeight() * PongWindow.S_FACTOR
        );
        setSize((int) bound.getWidth(), (int) bound.getHeight());
        setLocation((int) bound.getX(), (int) bound.getY());
    }
}
