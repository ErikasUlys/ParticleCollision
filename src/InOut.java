import java.awt.*;
import java.io.*;
import java.util.*;

public class InOut {

    public static LinkedList<Particle> readFile(String fileName){

        LinkedList<Particle> particles = new LinkedList<>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            int n = Integer.parseInt(reader.nextLine());
            for(int i = 0; i < n; i++){
                String[] data = reader.nextLine().trim().split(" ");

                double rx = Double.parseDouble(data[0]);
                double ry = Double.parseDouble(data[1]);

                double vx = Double.parseDouble(data[2])/2;
                double vy = Double.parseDouble(data[3])/2;

                double radius = Double.parseDouble(data[4]);
                double mass = Double.parseDouble(data[5]);

                Color color = new Color(Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]));

                particles.add(new Particle(rx, ry, vx, vy, mass, radius, color));
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return particles;

    }

}
