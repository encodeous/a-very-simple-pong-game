package ca.encodeous.pong.gui;

import ca.encodeous.pong.system.Utils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;

public class PButton extends JButton {
    public PButton() {
        this(null, null);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public PButton(Icon icon) {
        this(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    @ConstructorProperties({"text"})
    public PButton(String text) {
        this(text, null);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public PButton(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public PButton(String text, Icon icon) {
        super(text, icon);
        addActionListener((ev-> {
            AudioInputStream audioInputStream;
            Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(Utils.getFileAsIOStream("click.wav"));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            }
            clip.start();
        }));
    }
}
