import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class CollisionSystem extends JPanel implements ActionListener{
    final int PANEL_HEIGHT = 900;
    final int delay = 1;
    Timer timer;
    int time= 0;
    boolean stopped = false;
    boolean newSim;
    PriorityQueue<Collision> collisions = new PriorityQueue<>();
    LinkedList<Particle> particles;


    CollisionSystem(LinkedList<Particle> particles){
        this.setBounds(0, 0, PANEL_HEIGHT, PANEL_HEIGHT);
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        this.setLayout(null);

        this.particles = particles;
        getAllCollisions();

        timer = new Timer(delay, this);
        timer.start();
        newSim = true;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(Particle p : particles){
            g.setColor(p.color);
            double width = PANEL_HEIGHT*p.radius*2;
            double height = width;
            Ellipse2D.Double shape = new Ellipse2D.Double((p.rx-p.radius)*PANEL_HEIGHT, (p.ry-p.radius)*PANEL_HEIGHT, width, height);
            g.fill(shape);
            //g.draw(shape);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!collisions.isEmpty()) {
            while (collisions.peek().getTime() <= time+((double)delay/2)) {
                Collision collision = collisions.poll();
                if(collision.validCollision()) {
                    if (collision.getParticle2() == null) {    //collision with vertical wall
                        Particle p = collision.getParticle1();
                        p.bounceX();
                        getCollisions(p);
                    } else if (collision.getParticle1() == null) { //collision with horizontal wall
                        Particle p = collision.getParticle2();
                        p.bounceY();
                        getCollisions(p);
                    } else {
                        Particle p1 = collision.getParticle1();  //collision between particles
                        Particle p2 = collision.getParticle2();
                        p1.bounce(p2);
                        getCollisions(p1);
                        getCollisions(p2);
                    }
                }
            }
        }

        for(Particle p : particles){
            p.move();
        }

        time=time+delay;
        repaint();

        if(newSim){
            newSim = false;
            stop();
        }
    }

    public void getCollisions(Particle p){
        for (Particle p2 : particles) {
            if (!p.equals(p2)) {
                double timeCollides = p.collides(p2);
                if (timeCollides != Double.POSITIVE_INFINITY)
                    collisions.add(new Collision(timeCollides * delay + time, p, p2));
            }
        }
        double timeX = p.collidesX();
        if(timeX != Double.POSITIVE_INFINITY)
            collisions.add(new Collision(timeX*delay+time, p, null));

        double timeY = p.collidesY();
        if(timeY != Double.POSITIVE_INFINITY)
            collisions.add(new Collision(timeY*delay+time, null, p));
    }
    private void getAllCollisions(){
        collisions.clear();
        for(Particle p : particles)
        {
            getCollisions(p);
        }
    }

    public void adjustSpeed(double speed){
        for(Particle p : particles){
            p.correctSpeed(speed);
        }
        collisions.clear();
        getAllCollisions();
    }
    public void stop(){
        stopped = !stopped;
        if(stopped)
            timer.stop();
        else
            timer.start();
    }

}

