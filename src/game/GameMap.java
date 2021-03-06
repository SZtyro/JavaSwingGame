/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import addons.FastNoiseLite;
import java.io.BufferedInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author fabix
 */
public class GameMap {

    BufferedImage texture;

    Window parent;

    ArrayList<MapTile> tiles = new ArrayList();

    FastNoiseLite noise = new FastNoiseLite();

    float[] factors = new float[]{
        0.5f, 0.5f, 0.5f, 1.0f
    };
    float[] offsets = new float[]{
        0.0f, 0.0f, 0.0f, 0.0f
    };
    
    java.io.InputStream wavInputStream;

    GameMap(Window parent) {
        noise.SetSeed((int) (Math.random() * 100000));
        noise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
        noise.SetFrequency(0.3f);
        noise.SetFractalOctaves(4);

        noise.SetFractalGain(0.5f);
        this.parent = parent;

        try {
            texture = javax.imageio.ImageIO.read(this.getClass().getResource("resources/textures.png"));
        } catch (IOException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }

        
       

    }

    public void paintMap(Graphics graph) {
        Graphics graphTemp = graph.create();
        Graphics2D graph2d = (Graphics2D) graphTemp;
        generateMap(graph);
    }

    public void generateMap(Graphics graph) {

        for (int x = -10; x <= 10; x++) {
            for (int y = -10; y <= 10; y++) {

                Point worldCord = new Point(x + parent.character.worldPositionX, y + parent.character.worldPositionY);
                Point displayCord = new Point(x + 10, y + 10);
                if (!getTile(worldCord).isPresent()) {

                    float chance = noise.GetNoise(worldCord.x, worldCord.y);
                    MapTile m;
                    if (chance > 0.1) {

                        double oreChance = Math.random();

                        int textX = 3;
                        int textY = 5;

                        if (oreChance > 0.99) {
                            //Diament
                            textX = 2;
                            textY = 6;
                        } else if (oreChance < 0.98 && oreChance > 0.97) {
                            //Zloto
                            textX = 2;
                            textY = 9;
                        } else if (oreChance < 0.97 && oreChance > 0.93) {
                            //Wegiel
                            textX = 2;
                            textY = 5;
                        }

                        m = new MapTile(this, textX, textY, worldCord, displayCord, false);

                    } else {
                        m = new MapTile(this, 11, 8, worldCord, displayCord, true);
                    }

                    m.paintObject(graph);
                    tiles.add(m);
                } else {
                    MapTile m = getTile(worldCord).get();
                    m.setDisplayCord(displayCord);
                    m.paintObject(graph);

                }

            }
        }

    }

    public Optional<MapTile> getTile(final Point cord) {
        return tiles.stream().filter(o -> o.cord.equals(cord)).findFirst();
    }

    public Optional<MapTile> getTileByDisplay(final Point cord) {
        return tiles.stream().filter(o -> o.getDisplayCord().equals(cord)).findFirst();
    }

    public void destroyTile(Point cord) {

        Optional<MapTile> t = getTileByDisplay(cord);
        if (t.isPresent()) {
            t.get().setCanStep(true);

            try {
                if(Math.random() > 0.5){
                    wavInputStream = parent.getClass().getResourceAsStream("resources/rock1.wav");
                }else{
                    wavInputStream = parent.getClass().getResourceAsStream("resources/rock2.wav");
                }
                 
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(wavInputStream));
                javax.sound.sampled.Clip acAudioSample;

                acAudioSample = AudioSystem.getClip();
                acAudioSample.open(audioInputStream);
                acAudioSample.start();

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(parent.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
