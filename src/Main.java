import javax.swing.*;
import java.util.LinkedList;

/** ****** Guide ******
 *
 * SPACE - pause and unpause simulation
 * ARROW UP and DOWN - double and half the speed of the particles
 * ARROW LEFT and RIGHT - load new test simulations
 * R - reverse the velocity of the particles
 *
 */


public class Main {
    public static void main(String[] args)
    {
        String[] tests = {
                "billiards2",
                "billiards4",
                "billiards5",
                "brownian",
                "brownian2",
                "diagonal",
                "diagonal2",
                "diffusion",
                "diffusion2",
                "diffusion3",
                "p10",
                "p100-.5K",
                "p100-.125K",
                "p100-2K",
                "p1000-.5K",
                "p1000-2K",
                "p2000",
                "pendulum",
                "squeeze",
                "squeeze2",
                "wallbouncing",
                "wallbouncing2",
                "wallbouncing3"};

        new MyFrame(tests);
    }
}