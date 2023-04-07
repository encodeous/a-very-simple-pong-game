package ca.encodeous.pong; /**
 * MIT License
 *
 * Copyright (c) 2023 Adam Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import ca.encodeous.pong.gui.MainMenu;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;

public class Pong {
    /**
     * This code has been provided for you, and should not be modified
     */
    public static void main(String[] args) {
//        PongWindow window = new PongWindow();
//        Controller controller = new BuilderTheBob();
//        PlayerController playerControl = new PlayerController(window.getInputHandler());
//        PongSystem system = new PongSystem(window, controller, playerControl);
//        system.startGame();
//        playerControl.finishSubscription(system.getRightPaddle());
//        window.setVisible(true);
        FlatDarculaLaf.setup();
        FlatInterFont.install();
        FlatLaf.setPreferredFontFamily( FlatInterFont.FAMILY );
        MainMenu menu = new MainMenu();
        menu.setVisible(true);
//        Pong p = new Pong();
//        p.setVisible(true);
//        p.initComponents();
    }
}