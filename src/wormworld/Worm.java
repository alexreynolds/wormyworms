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

public class Worm {
    
    int radius;
    ArrayList<Posn> body;
    
    // Constructor
    public Worm(Posn start, int radius) {
        super();
	this.radius = radius;
        this.body = new ArrayList<Posn>();
        this.body.add(start);
        //this.body.add(new Posn(start.x + radius*2, start.y));
        //this.body.add(new Posn(start.x + radius*4, start.y));
    }
    
    
    /**
     * Produces the image of the worm at its current location and color.
     */
    public WorldImage wormImage(){
        
        // Creates the head of the worm body
         //WorldImage wormy = new DiskImage(this.body.get(0), this.radius, this.col);
        WorldImage wormy = new FromFileImage(this.body.get(0), "Images/wormhead.png");
         
        // Draws an image for each body segment
         Iterator itr = body.iterator();
         while (itr.hasNext()) {
            Posn loc = (Posn) itr.next();
            //WorldImage currimage = new DiskImage(loc, this.radius, this.col);
            WorldImage currimage = new FromFileImage(loc, "Images/wormbody.png");
            wormy = new OverlayImages(wormy, currimage);
            
         }
        
         
        return wormy;
        
	}
    
    /**
     * Moves the worm in one direction indicated by keystroke.
     * Does this by removing the last segment of the worm, and inserting a new
     * head segment to the first position in the array.
     */
    
    public void move(String ke){
        
            
            // Calculates the change in position of the worm's head
            Posn head = body.get(0);
            int dx = 0;
            int dy = 0;
            
            if (ke.equals("right")){
                    dx = (this.radius * 2);
		}
		else if (ke.equals("left")){
                    dx = -(this.radius * 2);
		}
		else if (ke.equals("up")){
                    dy = -(this.radius * 2);
		}
		else if (ke.equals("down")){
                    dy = (this.radius * 2);
		}
            
            // Adds the new head segment to the beginning of the body array
            body.add(0, new Posn(head.x + dx, head.y + dy));
            
            
            // Removes the last segment of the worm body
            body.remove(body.size() - 1);
            
    }
    
    /**
     * Increases the worm segment count by one. Method is called when the worm
     * eats food.
     */
    
    public void grow (Posn loc, String dir) {
        
        Posn head = body.get(0);
            int dx = 0;
            int dy = 0;
            
            if (dir.equals("right")){
                    dx = (this.radius * 2);
		}
		else if (dir.equals("left")){
                    dx = -(this.radius * 2);
		}
		else if (dir.equals("up")){
                    dy = -(this.radius * 2);
		}
		else if (dir.equals("down")){
                    dy = (this.radius * 2);
		}
            
            // Adds the new head segment to the beginning of the body array
            body.add(0, new Posn(head.x + dx, head.y + dy));
    }
    
    /**
     * Returns a boolean indicating if the worm head runs into any part of its
     * body while moving.
     */
    
    public boolean cannibalism() {
        
        // Acquires the position of the worm's head
        Posn wormhead = body.get(0);
        int n = 1;
        int max = body.size() - 1;
        
        while (n <= max) {
            Posn temp = body.get(n);
            if ((temp.x == wormhead.x) && (temp.y == wormhead.y)) {
                return true;
            }
            n++;
         }
         
         return false;
    }
    
    /**
     * Returns a boolean indicating if the worm head is outside the bounds of
     * the world canvas.
     */
    
    public boolean outsideBounds(int width, int height) {
		return this.body.get(0).x < 0
		|| this.body.get(0).x > width
		|| this.body.get(0).y < 0 
		|| this.body.get(0).y > height;
	}
}
