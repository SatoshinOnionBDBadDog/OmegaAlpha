package ch.supsi.omega.exploration.motion;

/*************************************************************************
 *  Compilation:  javac BrownianParticle.java
 *  Execution:    java BrownianParticle N
 *  Dependencies: StdDraw.java
 *
 *  Simulate N particles undergoing Brownian motion, starting
 *  at the origin. Draws a disc giving the average distance
 *  from the origin after each time step.
 *
 *************************************************************************/

import java.awt.Color;

public class BrownianParticle {
    private Color color;    // color
    private double x, y;    // position
    private double radius;  // radius
    private double sigma;   // standard deviation of increments

    public BrownianParticle(double x, double y, double sigma) {
        this.x = x;
        this.y = y;
        this.sigma = sigma;
        this.color = Color.getHSBColor((float) Math.random(), .7f, .7f);
        this.radius = 0.006;
    }


    // distance from origin
    public double distance() {
        return Math.sqrt(x*x + y*y);
    }

    // draw the particle
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x, y, radius);
    }

    // move the particle and draw line connecting old position and new
    public void move() {
        double z1 = StdRandom.gaussian();
        double z2 = StdRandom.gaussian();
        x += sigma * z1;
        y += sigma * z2;
    }

    public static void main(String[] args) { 
        int N = 1000; //Integer.parseInt(args[0]);
        StdDraw.setXscale(-1, +1);
        StdDraw.setYscale(-1, +1);
        StdDraw.show(0);              // turn on animation mode

        // create N random particles
        BrownianParticle[] particles = new BrownianParticle[N];
        for (int i = 0; i < N; i++)
            particles[i] = new BrownianParticle(0.0, 0.0, 0.005);
      
        // simulate them
        double avgdist = 0.0;
        for (@SuppressWarnings("unused")
		int steps = 0; true; steps++) {
            StdDraw.clear(StdDraw.LIGHT_GRAY);
            avgdist = 0.0;
            for (int i = 0; i < N; i++) {
                particles[i].draw();
                particles[i].move();
                avgdist += particles[i].distance() / N;
            }
            Color transparentGray = new Color(50, 50, 50, 100);
            StdDraw.setPenColor(transparentGray);
            StdDraw.filledCircle(0, 0, avgdist);
            StdDraw.show(50);
        }
    }
}