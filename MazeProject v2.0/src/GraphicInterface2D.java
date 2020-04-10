import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicInterface2D extends JPanel implements KeyListener {
    Cell[][] mazeGrid;
    int xPosition;
    int yPosition;

    public GraphicInterface2D (Cell[][] grid){
        mazeGrid = grid;
    }

    public void paintComponent(Graphics g){

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (!mazeGrid[xPosition][yPosition].RightWall)
                xPosition++;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (!mazeGrid[xPosition][yPosition].LeftWall)
                xPosition--;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!mazeGrid[xPosition][yPosition].UpWall)
                yPosition--;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!mazeGrid[xPosition][yPosition].DownWall)
                yPosition++;
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            solution = !solution;
        }
        repaint();
    }
}
