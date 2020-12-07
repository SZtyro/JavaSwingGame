/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author fabix
 */
public class Window extends JComponent implements Serializable, java.awt.event.ActionListener, MouseListener {

    Character character = new Character(this);

    javax.swing.Timer componentTimer;
    BufferedImage bufferedImage;
    GameMap map;

    public Window() {
        addMouseListener(this);
        map = new GameMap(this);

        try {

            componentTimer = new javax.swing.Timer(10, this);
            componentTimer.start();

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        try {
            bufferedImage = javax.imageio.ImageIO.read(getClass().getResource("resources/dark.png"));
        } catch (IOException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {

                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:

                        character.keyPressed(ke);

                        break;

                    case KeyEvent.KEY_RELEASED:

                        character.keyReleased(ke);

                        break;
                }
                return false;

            }

        });

    }

    @Override
    public void paint(Graphics g) {
        map.paintMap(g);
        
        character.paintObject(g);
        g.drawImage(bufferedImage, 0, 0, this);
        super.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        character.action();
        this.repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getPoint());
        Point p = e.getPoint();
        int x = (p.y / 64);
        int y = (p.x / 64);
        map.destroyTile(new Point(x, y));
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
