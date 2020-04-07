import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrontPageGraphics extends JPanel implements KeyListener, MouseListener {
    int dimensions;
    int typeOfMaze; //2 for 2D, 3 for 3D
    int key;
    JFrame frame;
    JButton typeOfMazeButton;
    JButton start;
    JButton dimensionsAdd1;
    JButton dimensionsAdd5;
    JButton dimensionsMinus1;
    JButton dimensionsMinus5;

    public FrontPageGraphics() {
        addKeyListener(this);
        addMouseListener(this);
        this.setPreferredSize(new Dimension(500, 500));
//        Thread mainThread = new Thread(new Runner());
//        mainThread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
