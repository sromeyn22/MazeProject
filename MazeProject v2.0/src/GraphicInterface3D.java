import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicInterface3D extends JFrame implements KeyListener {
    Cell[][] mazeGrid;
    int direction; // 0 for north, 1 for east, 2 for south, 3 for west
    int xPosition;
    int yPosition;

    public GraphicInterface3D(Cell[][] grid){
        mazeGrid = grid;
        direction = 0;
        xPosition = 0;
        yPosition = 0;
    }

    public boolean[] whichWallsToDraw(){

        return new boolean[0];
    }

    public void display(boolean[] wallsToDraw){

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

    }
}
