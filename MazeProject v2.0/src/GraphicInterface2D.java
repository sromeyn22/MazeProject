import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicInterface2D extends JPanel implements KeyListener {
    Cell[][] mazeGrid;

    public GraphicInterface2D (Cell[][] grid){
        mazeGrid = grid;
    }

    public void display(){

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
