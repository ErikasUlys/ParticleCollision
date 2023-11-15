public class Collision implements Comparable<Collision>{
    private final Particle particle1;
    private final Particle particle2;
    private final double time;
    private int collisionCountP1;
    private int collisionCountP2;
    public Collision(double t, Particle p1, Particle p2){
        particle1 = p1;
        particle2 = p2;
        time = t;

        if(p1 != null)
            collisionCountP1 = p1.collisionCount;
        if(p2 != null)
            collisionCountP2 = p2.collisionCount;
    }

    public double getTime() {return time;}
    public Particle getParticle1() {return particle1;}
    public Particle getParticle2() {return particle2;}
    public boolean validCollision() {
        if(particle1 == null){
            return particle2.collisionCount == collisionCountP2;
        }
        if(particle2 == null){
            return particle1.collisionCount == collisionCountP1;
        }
        else
            return particle2.collisionCount == collisionCountP2 &&
                    particle1.collisionCount == collisionCountP1;
    }

    @Override
    public int compareTo(Collision x) {
        return Double.compare(this.time, x.time);
    }

}
