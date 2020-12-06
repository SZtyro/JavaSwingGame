/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabix
 */
public class Character {

    AffineTransform affineTransform;
    BufferedImage bufferedImage;

    Window parent;
    String pressedKey = "idle";
    int keyFrame = 0;

    int worldPositionX = 5;
    int worldPositionY = 5;

    Character(Window parent) {

        this.parent = parent;
        try {
            bufferedImage = javax.imageio.ImageIO.read(getClass().getResource("./resources/character.png"));
        } catch (IOException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void action() {

        int maxFrames;
        try {
            maxFrames = AnimationMap.valueOf(pressedKey).getMaxFrames();
            if (keyFrame < maxFrames) {
                keyFrame++;
            } else {
                keyFrame = 0;
            }

        } catch (Exception e) {

        }

    }

    public void paintObject(Graphics graph) {

        Graphics graphTemp = graph.create();
        Graphics2D graph2d = (Graphics2D) graphTemp;
        graphTemp.setColor(Color.BLACK);
        BufferedImage actualFrame;

        try {
            actualFrame = bufferedImage.getSubimage(keyFrame * 64, AnimationMap.valueOf(pressedKey).getRow() * 64, 64, 64);
        } catch (Exception e) {
            pressedKey = "idle";
            actualFrame = bufferedImage.getSubimage(keyFrame * 64, AnimationMap.valueOf(pressedKey).getRow() * 64, 64, 64);
        }

        Graphics actualFrameGraph = actualFrame.getGraphics();
        //graph2d.drawImage(actualFrame, null, (parent.getBounds().width / 2) + 32, (parent.getBounds().height / 2) + 32);
        graph2d.drawImage(actualFrame, null, 672 - 32, 672 -32);
        //graph2d.drawImage(actualFrame, null, 0, 0);

    }

    public void keyPressed(KeyEvent ke) {
        String key = String.valueOf(ke.getKeyChar());
        switch (key) {
            case "s":
                System.out.println(canStep(worldPositionX + 1, worldPositionY));
                if (canStep(worldPositionX + 1, worldPositionY)) {
                    worldPositionX++;
                }
                break;
            case "w":
                if (canStep(worldPositionX - 1, worldPositionY)) {
                    worldPositionX--;
                }
                break;
            case "a":
                if (canStep(worldPositionX, worldPositionY - 1)) {
                    worldPositionY--;
                }
                break;
            case "d":
                if (canStep(worldPositionX, worldPositionY + 1)) {
                    worldPositionY++;
                }
                break;
            default:
                break;
        }
        if (!pressedKey.equalsIgnoreCase(String.valueOf(ke.getKeyChar()))) {

            keyFrame = 0;
            pressedKey = String.valueOf(ke.getKeyChar());
        } else {

        }
    }

    public void keyReleased(KeyEvent ke) {
        if (ke.isConsumed()) {

        } else {
            keyFrame = 0;
            pressedKey = "idle";
        }
    }

    public boolean canStep(int x, int y) {

        return parent.map.getTile(new Point(x, y)).get().canStep;
    }

}
