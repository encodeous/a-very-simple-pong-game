package ca.encodeous.pong.rendering;

import ca.encodeous.pong.Constants;
import ca.encodeous.pong.gui.InputHandler;
import ca.encodeous.pong.gui.MainMenu;
import ca.encodeous.pong.physics.GameEntity;
import ca.encodeous.pong.system.GameSystemEvents;
import ru.krlvm.swingdpi.SwingDPI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

/**
 * Responsible for handling the GUI properties of the main pong game.
 */
public class PongWindow extends JFrame implements GameSystemEvents {
    public static final float S_FACTOR = SwingDPI.getScaleFactor();

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    private final InputHandler inputHandler;
    private final HashMap<UUID, ScreenObject> renderObjects = new HashMap<>();
    private final JPanel gamePanel;
    private JLabel scoreLabel;

    public PongWindow() {
        gamePanel = new JPanel();
        gamePanel.setSize((int) (Constants.WORLD_WIDTH * S_FACTOR), (int) (Constants.WORLD_HEIGHT * S_FACTOR));
        Dimension gameDim = new Dimension((int) (Constants.WORLD_WIDTH * S_FACTOR), (int) (Constants.WORLD_HEIGHT * S_FACTOR));
        gamePanel.setPreferredSize(gameDim);
        gamePanel.setLayout(null);
        add(gamePanel);

        scoreLabel = new JLabel("Score: 0 - 0");
        scoreLabel.setSize(gamePanel.getWidth(), gamePanel.getHeight());
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setVerticalAlignment(SwingConstants.TOP);
        scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        gamePanel.add(scoreLabel);

        pack();
        setResizable(false);
        setTitle("Pong? Perhaps... - Adam Chen");
        inputHandler = new InputHandler();
        inputHandler.addTo(this);
        setLocationRelativeTo(MainMenu.instance);
    }

    public GameEntity getEntityById(UUID id){
        return renderObjects.get(id).getLastEntity();
    }

    /**
     * Called before the main game tick
     */
    @Override
    public void preTick() {
        // read input first
        inputHandler.preTick();
    }

    /**
     * Called after all the objects are updated
     */
    @Override
    public void postTick() {
        // render to the screen
        repaint();
    }

    /**
     * Called when a player wins.
     */
    @Override
    public void win(int playerId) {
        JOptionPane informationOptionPane = new JOptionPane();
        informationOptionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        informationOptionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
        informationOptionPane.setMessage("The game is over! Player " + (playerId + 1) + " has won!\n" + scoreLabel.getText() +"\nRematch in 3 seconds...");
        JDialog dialog = informationOptionPane.createDialog("Pong Game - GAME OVER");
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dialog.setVisible(false);
            dialog.dispose();
        }).start();
        new Thread(()->{
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }).start();
    }

    @Override
    public void scoreUpdate(int[] playerScores) {
        scoreLabel.setText(String.format("Score: %d - %d", playerScores[0], playerScores[1]));
    }

    @Override
    public void updated(GameEntity object) {
        if(renderObjects.get(object.getId()) == null) return;
        renderObjects.get(object.getId()).sync(object);
        renderObjects.get(object.getId()).repaint();
    }

    @Override
    public void created(GameEntity object) {
        renderObjects.put(object.getId(), new ScreenObject());
        gamePanel.add(renderObjects.get(object.getId()));
    }

    @Override
    public void removed(GameEntity object) {
        gamePanel.remove(renderObjects.get(object.getId()));
        renderObjects.remove(object.getId());
    }
}
