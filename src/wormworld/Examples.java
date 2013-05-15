/*
 * Alex Reynolds
 * CMPU 203
 * 
 * MIDTERM PROJECT
 * 
 */
package wormworld;

import tester.*;
import java.awt.Color;
import javalib.colors.*;
import javalib.worldimages.*;

public class Examples {
    
    // Examples of data for the Worm class
    
    Worm w1 = new Worm(new Posn(100,100), 10);
    Worm w1left = new Worm(new Posn(80, 100), 10);
    Worm w1right = new Worm(new Posn(120,100), 10);
    Worm w1up = new Worm(new Posn(100,80), 10);
    Worm w1down = new Worm(new Posn(100,120), 10);
    Worm wbig = new Worm(new Posn(200,200), 20);
    Worm wsmall = new Worm(new Posn(100,100), 5);
    
    // Examples of data for the Food class
    
    Food fuds1 = new Food(new Posn(250,250));
    Food fuds2 = new Food(new Posn(100,100));
    Food fuds3 = new Food(new Posn(400,400));
    Food fuds4 = new Food(new Posn(350,350));
    Food fuds5 = new Food(new Posn(100,400));
    
    // Examples of data for the WormWorld class
    
    WormWorld world1 = new WormWorld(w1, fuds1);
    WormWorld world1left = new WormWorld(w1left, fuds1);
    WormWorld world1right = new WormWorld(w1right, fuds1);
    WormWorld world1up = new WormWorld(w1up, fuds1);
    WormWorld world1down = new WormWorld(w1down, fuds1);
    WormWorld world2 = new WormWorld(wbig, fuds2);
    WormWorld world3 = new WormWorld(wsmall, fuds3);
    WormWorld wOutOfBounds = new WormWorld(new Worm(new Posn(-1,-1), 10), fuds1);
    
    // Sample Posns
    
    Posn loc1 = new Posn(80,80);
    Posn loc2 = new Posn(100,100);
    
    
    void reset() {
        
    // Examples of data for the Worm class
    
    this.w1 = new Worm(new Posn(100,100), 10);
    this.w1left = new Worm(new Posn(80, 100), 10);
    this.w1right = new Worm(new Posn(120,100), 10);
    this.w1up = new Worm(new Posn(100,80), 10);
    this.w1down = new Worm(new Posn(100,120), 10);
    this.wbig = new Worm(new Posn(200,200), 20);
    this.wsmall = new Worm(new Posn(100,100), 5);
    
    // Examples of data for the Food class
    
    this.fuds1 = new Food(new Posn(250,250));
    this.fuds2 = new Food(new Posn(100,100));
    this.fuds3 = new Food(new Posn(400,400));
    this.fuds4 = new Food(new Posn(350,350));
    this.fuds5 = new Food(new Posn(100,400));
    
    // Examples of data for the WormWorld class
    
    this.world1 = new WormWorld(w1, fuds1);
    this.world1left = new WormWorld(w1left, fuds1);
    this.world1right = new WormWorld(w1right, fuds1);
    this.world1up = new WormWorld(w1up, fuds1);
    this.world1down = new WormWorld(w1down, fuds1);
    this.world2 = new WormWorld(wbig, fuds2);
    this.world3 = new WormWorld(wsmall, fuds3);
    this.wOutOfBounds = new WormWorld(new Worm(new Posn(-1,-1), 10), fuds1);
    
    }
    
    // Tests for move()
    
    void testMove(Tester t) {
        
        this.reset();
        this.w1.move("left");
        t.checkExpect(this.w1, this.w1left, "Move test - left");
        
        this.reset();
        this.w1.move("right");
        t.checkExpect(this.w1, this.w1right, "Move test - right");
        
        this.reset();
        this.w1.move("down");
        t.checkExpect(this.w1, this.w1down, "Move test - down");
        
        this.reset();
        this.w1.move("up");
        t.checkExpect(this.w1, this.w1up, "Move test - up");
        
    }
    
    // Tests for OnKeyEvent()
    
    void testOnKeyEvent(Tester t) {
        
        this.reset();
        this.world1.onKeyEvent("up");
        t.checkExpect(this.world1, this.world1, "Key event test - up");
        
        this.reset();
        this.world1.onKeyEvent("down");
        t.checkExpect(this.world1, this.world1, "Key event test - down");
        
        this.reset();
        this.world1.onKeyEvent("left");
        t.checkExpect(this.world1, this.world1, "Key event test - left");
        
        this.reset();
        this.world1.onKeyEvent("right");
        t.checkExpect(this.world1, this.world1, "Key event test - right");
        
    }
    
    // Tests for outsideBounds()
    
    void testOutsideBounds(Tester t) {
        
        t.checkExpect(this.w1.outsideBounds(200, 200), false, "Outside bounds test 1");
        t.checkExpect(this.w1.outsideBounds(80, 80), true, "Outside bounds test 2");
        t.checkExpect(this.wbig.outsideBounds(0,0), true, "Outside bounds test 3");
        t.checkExpect(this.wsmall.outsideBounds(20, 20), true, "Outside bounds test 4");
        t.checkExpect(this.wsmall.outsideBounds(500,500), false, "Outside bounds test 5");
                
    }
    
    // Tests for worldEnds()
    
    void testWorldEnds(Tester t) {
        
        this.reset();
        t.checkExpect(this.world1.worldEnds(),
                new WorldEnd(false, this.world1.makeImage()));
        
        this.reset();
        t.checkExpect(this.wOutOfBounds.worldEnds(),
                new WorldEnd(true,
                    new OverlayImages(this.wOutOfBounds.makeImage(),
                    new TextImage(new Posn(250, 400),
                    "Your worm has escaped your controlling clutches!",
                    Color.white))));
    }
    
    // Tests for eatable()
    
    void testEatable(Tester t) {
        t.checkExpect(this.fuds1.eatable(loc1, "up"), false, "Eatable test 1");
        t.checkExpect(this.fuds2.eatable(loc1, "right"), false, "Eatable test 2");
        t.checkExpect(this.fuds2.eatable(loc2, "up"), true, "Eatable test 3");
        t.checkExpect(this.fuds3.eatable(loc1, "right"), false, "Eatable test 4");
        t.checkExpect(this.fuds4.eatable(loc2, "down"), false, "Eatable test 5");
    }
    
    
    
    
 // Creates the world and runs the test methods
    
public static void main(String[] args) {
    
   Examples e = new Examples();
   Tester.runReport(e, false, false); 
 
    // run the game
    WormWorld w;
    
    w = new WormWorld(new Worm(new Posn(250,250), 10), new Food(new Posn(100,100)));
    w.bigBang(500, 500, 0.3);   
    
    }   

}
