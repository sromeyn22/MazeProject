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
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
            if(direction == 3 && !WallOnOff(0, x, y)){
                yPosition--;
            } else if(direction == 0 && !WallOnOff(1, x, y)){
                xPosition++;
            } else if(direction == 1 && !WallOnOff(2, x, y)){
                yPosition++;
            } else if(direction == 2 && !WallOnOff(3, x, y)){
                xPosition--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            if(direction == 1 && !WallOnOff(0, x, y)){
                yPosition--;
            } else if(direction == 2 && !WallOnOff(1, x, y)){
                xPosition++;
            } else if(direction == 3 && !WallOnOff(2, x, y)){
                yPosition++;
            } else if(direction == 0 && !WallOnOff(3, x, y)){
                xPosition--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP ) {
            if(direction == 1 && !WallOnOff(1, x, y)){
                xPosition++;
            } else if(direction == 2 && !WallOnOff(2, x, y)){
                yPosition++;
            } else if(direction == 3 && !WallOnOff(3, x, y)){
                xPosition--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if(direction == 2 && !WallOnOff(0, x, y)){
                yPosition--;
            } else if(direction == 3 && !WallOnOff(1, x, y)){
                xPosition++;
            } else if(direction == 0 && !WallOnOff(2, x, y)){
                yPosition++;
            } else if(direction == 1 && !WallOnOff(3, x, y)){
                xPosition--;
            }
        }else if (e.getKeyCode() == KeyEvent.VK_A) {
            if(direction == 0){
                direction = 3;
            } else{
                direction--;
            }
        }else if (e.getKeyCode() == KeyEvent.VK_D) {
            if(direction == 3){
                direction = 0;
            } else{
                direction++;
            }
        }
        if(direction == 0){
            displayWalls = up(displayWalls);
        } else if(direction == 1){
            displayWalls = right(displayWalls);
        } else if(direction == 2){
            displayWalls = down(displayWalls);
        } else if(direction == 3){
            displayWalls = left(displayWalls);
        }
        repaint();
    }
}
