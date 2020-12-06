/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static com.sun.javafx.iio.ImageStorage.ImageType.RGB;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabix
 */
public class MapTile {

    Point cord;
    private Point displayCord;

    int health;

    int textureX;
    int textureY;

    int textureWidth = 32;
    private boolean canStep;

    GameMap parent;

    //dzwiek niszczenia
    MapTile(GameMap parent, int textureX, int textureY, Point cord, Point displayCord, boolean canStep) {
        this.parent = parent;
        this.textureX = textureX;
        this.textureY = textureY;
        this.cord = cord;
        this.displayCord = displayCord;
        this.canStep = canStep;

    }

    public void paintObject(Graphics graph) {

        Graphics graphTemp = graph.create();
        Graphics2D graph2d = (Graphics2D) graphTemp;
        graphTemp.setColor(Color.BLACK);
        BufferedImage actualFrame;
        actualFrame = parent.texture.getSubimage(textureX * textureWidth, textureY * textureWidth, textureWidth, textureWidth);
        if (canStep) {
            
            RescaleOp op = new RescaleOp(parent.factors, parent.offsets, null);
             actualFrame = op.filter(actualFrame, null);
        }

        graph2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graph2d.drawImage(actualFrame, displayCord.y * textureWidth * 2, displayCord.x * textureWidth * 2, 64, 64, null);
        //graph2d.drawString(String.valueOf(displayCord.x) + "  " + String.valueOf(displayCord.y), displayCord.x * 64, displayCord.y * 64);
        //graph2d.drawImage(actualFrame, null, y * textureWidth * 2, x * textureWidth * 2);
    }

    public Point getDisplayCord() {
        return displayCord;
    }

    public void setDisplayCord(Point displayCord) {
        this.displayCord = displayCord;
    }

    public boolean isCanStep() {
        return canStep;
    }

    public void setCanStep(boolean canStep) {
        this.canStep = canStep;
    }

}
