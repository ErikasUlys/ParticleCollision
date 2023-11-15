import java.awt.*;

public class Particle{
    protected double rx;
    protected double ry;
    private double vx;
    private double vy;

    private final double mass;
    protected final double radius;

    protected final Color color;
    protected int collisionCount;

    public Particle(double rx, double ry, double vx, double vy, double mass, double radius, Color color){
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        collisionCount = 0;
    }

    public double collidesX(){
        if(vx > 0)
            return (1-radius-rx)/vx;
        if(vx < 0)
            return (radius-rx)/vx;
        return Double.POSITIVE_INFINITY;
    }
    public double collidesY(){
        if(vy > 0)
            return (1-radius-ry)/vy;
        if(vy < 0)
            return (radius-ry)/vy;
        return Double.POSITIVE_INFINITY;
    }
    public double collides(Particle b){
        double vr = (b.rx-rx)*(b.vx-vx)+(b.ry-ry)*(b.vy-vy);;
        double v = (Math.pow((b.vx-vx),2)+Math.pow((b.vy-vy),2));
        double r = (Math.pow((b.rx-rx),2)+Math.pow((b.ry-ry),2)-Math.pow((radius+b.radius),2));
        double d = Math.pow(vr,2) - v*r;
        if(vr>=0 || d < 0)
            return Double.POSITIVE_INFINITY;
        else return -(vr+Math.sqrt(d))/v;
    }

    public void bounceY() {
        vy *= -1;
        collisionCount++;
    }
    public void bounceX() {
        vx *= -1;
        collisionCount++;
    }
    public void bounce(Particle b){
        adjustPosition(b);

        double angle = Math.atan2((b.ry-ry),(b.rx-rx));
        double[][] rotation ={{Math.cos(angle), Math.sin(angle)},
                            {-Math.sin(angle), Math.cos(angle)}};
        double[][] rotationback ={{Math.cos(angle), -Math.sin(angle)},
                                {Math.sin(angle), Math.cos(angle)}};

        double[][] aParticle = rotate(vx, vy, rotation);      //[0][0] - x
        double[][] bParticle = rotate(b.vx, b.vy, rotation); //[1][0] - y

        vx = ((mass-b.mass)*aParticle[0][0]+2*b.mass*bParticle[0][0])/(b.mass+mass);
        b.vx = ((b.mass-mass)*bParticle[0][0]+2*mass*aParticle[0][0])/(b.mass+mass);

        double[][] aRez = rotate(vx, aParticle[1][0], rotationback);
        double[][] bRez = rotate(b.vx, bParticle[1][0], rotationback);

        //mess
        if(Math.abs(aRez[0][0])<1.E-10)
            aRez[0][0] = 0;
        if(Math.abs(aRez[1][0])<1.E-10)
            aRez[1][0] = 0;
        if(Math.abs(bRez[0][0])<1.E-10)
            bRez[0][0] = 0;
        if(Math.abs(bRez[1][0])<1.E-10)
            bRez[1][0] = 0;

        vx = aRez[0][0];
        vy = aRez[1][0];

        b.vx = bRez[0][0];
        b.vy = bRez[1][0];

        b.collisionCount++;
        collisionCount++;
    }

    private double[][] rotate(double a, double b, double[][] rotation){
        double[][] tmp ={{a},{b}};
        double[][] rez = new double[2][1];

        for(int i=0;i<2;i++){
            for(int j=0;j<1;j++){
                rez[i][j]=0;
                for(int k=0;k<2;k++)
                {
                    rez[i][j]+=rotation[i][k]*tmp[k][j];
                }
            }
        }
        return rez;
    }

    public void move(){
        rx = (rx + vx);
        ry = (ry + vy);
    }

    private void adjustPosition(Particle b){
        double time = collides(b);
        if(time != Double.POSITIVE_INFINITY){
            rx = (rx+vx*time);
            ry = (ry+vy*time);
            b.rx = (b.rx+b.vx*time);
            b.ry = (b.ry+b.vy*time);
        }
    }

    public void correctSpeed(double speed){
        vx = vx * speed;
        vy = vy * speed;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Particle){
            Particle p = (Particle)o;
            return this.mass==p.mass && this.color == p.color &&
                    this.rx == p.rx && this.ry == p.ry &&
                    this.vx == p.vx && this.vy == p.vy;
        }
        return false;
    }
}
