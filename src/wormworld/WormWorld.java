
/*
 * Alex Reynolds
 * CMPU 203
 * 
 * MIDTERM PROJECT
 * 
 */
package wormworld;

import java.awt.Color;
import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import java.util.*;



public class WormWorld extends World {

    int width = 500;
    int height = 500;
    Worm wormy;
    Food fuds;
    String direction = "left";
    int score = 0;
    int time = 1;
    int offertime = 0;
    int drugtime = 0;
    boolean offerDrugs = false;
    boolean onDrugs = false;
    Random randy = new Random();
    int n = 1;
    
    // Constructor
    public WormWorld(Worm wormy, Food fuds) {
        super();
        this.wormy = wormy;
        this.fuds = fuds;
    }
    
    
    /**
     * Changes direction of worm based on the input keystroke.
     */
    public void onKeyEvent(String ke) {
        if (ke.equals("up") || ke.equals("down") || ke.equals("right") || ke.equals("left")) {
            if (dirChange(ke))
                if (onDrugs)
                    direction = oppDir(ke);
                else
                    direction = ke;
        }
    }
    
    /**
     * Determines if the worm should change direction when a key is pressed.
     * Direction will not change if it is already the worm's current direction or the 
     * worm is going in the opposite direction (as the worm cannot go backwards
     * onto itself).
     */
    public boolean dirChange(String ke) {
        if (ke.equals(direction))
            return false;
        else if (ke.equals("up") && direction.equals("down"))
            return false;
        else if (ke.equals("left") && direction.equals("right"))
            return false;
        else if (ke.equals("down") && direction.equals("up"))
            return false;
        else if (ke.equals("right") && direction.equals("left"))
            return false;
        else
            return true;
    }
    
    /**
     * Returns the opposite direction of the given direction.
     */
    public String oppDir(String ke) {
        if (ke.equals("left"))
            return "right";
        else if (ke.equals("right"))
            return "left";
        else if (ke.equals("down"))
            return "up";
        else
            return "down";
    }

    /**
     * Called at every tick of the game clock. Moves the worm based on a given
     * direction, keeps track of time, places special food at certain time
     * intervals, and determines what state the game is in.
     */
    public void onTick() {
        
        // When the worm encounters food
        if (this.fuds.eatable(this.wormy.body.get(0), direction)) 
        {
            this.wormy.grow(this.fuds.loc, direction);
            this.fuds.loc = this.fuds.next();
            score++;
        }
        
        // Every 40 ticks, drugs will be placed in the world for the worm
        if (time % 40 == 0) {
            this.fuds.drugsloc = this.fuds.next();
            offerDrugs = true;
            this.fuds.candrugs = true;
        }
        
        // Drugs will only appear for 20 clock ticks
        if (offertime >=20) {
            offerDrugs = false;
            offertime = 0;
            this.fuds.candrugs = false;
        }
            
        
        // Determines if the worm is close enough to eat the drugs
        if (this.fuds.druggable(this.wormy.body.get(0), direction)) 
        {
            this.wormy.grow(this.fuds.drugsloc, direction);
            offerDrugs = false;
            this.fuds.candrugs = false;
            onDrugs = true;
            score = score + 10;
        }
        
        // Keeps track of the time that drugs are offered/the worm is on drugs
        if (offerDrugs) {
            offertime++;
        } else if (onDrugs) {
            drugtime++;
            this.fuds.candrugs = false;
        }
        
        // Moves the worm in its current direction
        this.wormy.move(direction);
        
        // If the worm has been on drugs for 20 ticks, stop
        if (drugtime >= 20) {
            onDrugs = false;
            drugtime = 0;
        }
        
        // Keeps track of ticks passed
        time++;
        
    }
    
    /**
     * Creates the background image for the world.
     */
    
    public WorldImage wormDirt =
        new FromFileImage(new Posn(250, 250), "Images/dirt.jpg");
    
    /**
     * 
     * Creates a strobing image for the world background.
     */
    public WorldImage strobeDirt() {
        if (n < 6)
           n++;
        else 
            n = 1;
        
        int num = n;           
        
        String imgloc = "Images/dirt" + num + ".jpg";
        
        return new FromFileImage(new Posn(250,250), imgloc);
    }
    
    /** 
     * Draws the world image, food, and worm onto the canvas.
     */
    public WorldImage makeImage() {
        String myscore = "Score: " + score;
        
        if (onDrugs){
            return new OverlayImages(this.strobeDirt(), 
                    new OverlayImages(this.fuds.foodImage(), 
                        new OverlayImages(this.wormy.wormImage(),
                            new TextImage(new Posn(250, 420), myscore, Color.white))));
        } else if (offerDrugs) {
            return new OverlayImages(this.wormDirt,
                   new OverlayImages(this.fuds.foodImage(),
                    new OverlayImages(this.fuds.badFoodImage(),
                      new OverlayImages(new TextImage(new Posn(250, 420), myscore, Color.white),
                        this.wormy.wormImage()))));
        } else {
        return new OverlayImages(this.wormDirt, 
                 new OverlayImages(this.fuds.foodImage(), 
                    new OverlayImages(new TextImage(new Posn(250, 420), myscore, Color.white),
                        this.wormy.wormImage())));
        }
    }
    
    /**
     * 
     * Handles the end of the game.
     * 
     * The game ends when the worm is either out of bounds or eats itself.
     */
    public WorldEnd worldEnds() {
        String escape = "Your worm has escaped your controlling clutches!";
        String eaten = "Your worm has become a cannibal.";
        String myscore = "Your score: " + score;
        
        if (this.wormy.outsideBounds(this.width, this.height)) {
            return new WorldEnd(true,
                    new OverlayImages(this.makeImage(), new TextImage(new Posn(250, 400), escape,
                    Color.white)));
        } else if (this.wormy.cannibalism()) {
            return new WorldEnd(true,
                    new OverlayImages(this.makeImage(), 
                    new TextImage(new Posn(250,400), eaten,
                    Color.white)));
        } else {
            // The worm is still in play
            return new WorldEnd(false, this.makeImage());
        }
    }
    
}
