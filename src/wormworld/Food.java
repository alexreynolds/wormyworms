/*
 * Alex Reynolds
 * CMPU 203
 * 
 * MIDTERM PROJECT
 * 
 */
package wormworld;

import java.util.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.lang.Math.*;

public class Food {
    
    Posn loc;
    Posn drugsloc = new Posn(0,0);
    IColor col;
    int radius = 10;
    int diam = 20;
    int inc = 25;
    Random randy = new Random();
    boolean candrugs = false;
    
    // Constructor
    public Food (Posn loc) {
        super ();
        this.loc = loc;
        this.col = col;
        this.radius = radius;
    }
    
    /**
     * Returns a boolean indicating if the worm is close enough to the food
     * to eat it.
     */
    public boolean eatable(Posn wormLoc, String dir) {
        
        int xdist = Math.abs(wormLoc.x - loc.x);
        int ydist = Math.abs(wormLoc.y - loc.y);
        
        double dist = Math.sqrt((xdist ^ 2) + (ydist ^2));
        
        if (dist <= 5)
            return true;
        else
            return false;
    }
    
    /**
     * Returns a boolean indicating if the worm is close enough to the drugs to
     * eat them.
     */
    public boolean druggable(Posn wormLoc, String dir) {
        
        int xdist = Math.abs(wormLoc.x - drugsloc.x);
        int ydist = Math.abs(wormLoc.y - drugsloc.y);
        
        double dist = Math.sqrt((xdist ^ 2) + (ydist ^2));
        
        if ((dist <= 5) && candrugs)
            return true;
        else
            return false;
    }
    
    /**
     * Creates a new Posn for the next food/drug particle, and checks to ensure that
     * it isn't the same Posn as the last.
     */
    public Posn next() {
        
        // Assumes a 500 x 500 canvas, food radius = worm segment radius
        
        int randomx = randy.nextInt(inc - 2) + 2;
        int randomy = randy.nextInt(inc - 2) + 2;
        
        Posn next = new Posn(randomx * diam, randomy * diam);
        
        if ((!this.loc.equals(next)) || (!this.drugsloc.equals(next))) {
            return next;
        } else {
            return next();
        }
    }
    
    /**
     * Returns a food image to put in the worm world.
     */
    public WorldImage foodImage() {
        
        WorldImage noms = new DiskImage(this.loc, 10, new Green());
        
        return noms;
    }
    
    /**
     * Returns a drug image to put in the worm world.
     */
    public WorldImage badFoodImage() {
        
        WorldImage noms = new FromFileImage(this.drugsloc, "Images/fooddrugs.png");
        
        return noms;
    }
}
