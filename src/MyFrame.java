import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.*;

public class MyFrame extends JFrame{

    CollisionSystem collisionPanel;
    private int currentData = 0;
    Action speedUp;
    Action slowDown;
    Action stop;
    Action reverse;
    Action next;
    Action previous;
    private final String[] dataSet;
    MyFrame(String[] data){
        dataSet = data;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(916, 939);

        nextPanel();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        actions();
    }

    private void actions(){
        speedUp = new speedUp();
        this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("UP"), "speedUp");
        this.getRootPane().getActionMap().put("speedUp", speedUp);
        slowDown = new slowDown();
        this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "slowDown");
        this.getRootPane().getActionMap().put("slowDown", slowDown);
        stop = new stop();
        this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "stop");
        this.getRootPane().getActionMap().put("stop", stop);
        reverse = new reverse();
        this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("R"), "reverse");
        this.getRootPane().getActionMap().put("reverse", reverse);

        next = new next();
        this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "next");
        this.getRootPane().getActionMap().put("next", next);
        previous = new previous();
        this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "previous");
        this.getRootPane().getActionMap().put("previous", previous);
    }
    private void nextPanel(){
        if(collisionPanel != null)
            this.remove(collisionPanel);

        LinkedList<Particle> particles = InOut.readFile("testData/" + dataSet[currentData] + ".txt");
        collisionPanel = new CollisionSystem(particles);
        this.add(collisionPanel);
        actions();
    }

    private class speedUp extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {collisionPanel.adjustSpeed(2);
        }
    }
    private class slowDown extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {collisionPanel.adjustSpeed(0.5);
        }
    }
    private class stop extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            collisionPanel.stop();
        }
    }
    private class reverse extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            collisionPanel.adjustSpeed(-1);
        }
    }
    private class next extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            currentData++;
            if(currentData >= dataSet.length)
                currentData=0;
            nextPanel();

        }
    }
    private class previous extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            currentData--;
            if(currentData < 0)
                currentData=dataSet.length-1;
            nextPanel();
        }
    }
}
