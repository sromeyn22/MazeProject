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
        g.setColor(Color.WHITE);
            g.fillRect(0, 0, _width, _height);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, _width, 3);
            g.fillRect(_width - 3, 0, 3, _height);
            g.fillRect(0, _height - 3, _width, 3);
            g.fillRect(0, 0, 3, _height);

            // Iterate over all cells
            if(_commands.solution == true){
                for (int column = 0; column < _grid.length; column += 1) {
                    for (int row = 0; row < _grid.length; row += 1) {

                        // The coordinates of this cell.
                        int initx = column * _boxSize;
                        int inity = row    * _boxSize;

                        g.setColor(Color.pink);
                        if (_grid[column][row].Path){
                            g.fillRect(initx, inity, _boxSize, _boxSize);

                        }
                        g.setColor(Color.BLACK);
                        if(_grid[column][row].UpWall){
                            g.fillRect(initx, inity, _boxSize, 2);
                        }
                        if(_grid[column][row].RightWall){
                            g.fillRect(initx + _boxSize - 2, inity, 2, _boxSize);
                        }
                        if(_grid[column][row].DownWall){
                            g.fillRect(initx, inity + _boxSize - 2, _boxSize, 2);
                        }
                        if(_grid[column][row].LeftWall){
                            g.fillRect(initx, inity, 2, _boxSize);
                        }
                    }
                }
            } else {
                for (int column = 0; column < _grid.length; column += 1) {
                    for (int row = 0; row < _grid.length; row += 1) {


                        // The coordinates of this cell.
                        int initx = column * _boxSize;
                        int inity = row    * _boxSize;

                        g.setColor(Color.BLACK);
                        if(_grid[column][row].UpWall){
                            g.fillRect(initx, inity, _boxSize, 2);
                        }
                        if(_grid[column][row].RightWall){
                            g.fillRect(initx + _boxSize - 2, inity, 2, _boxSize);
                        }
                        if(_grid[column][row].DownWall){
                            g.fillRect(initx, inity + _boxSize - 2, _boxSize, 2);
                        }
                        if(_grid[column][row].LeftWall){
                            g.fillRect(initx, inity, 2, _boxSize);
                        }
                    }
                }
            }
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, _boxSize, _boxSize);
            g.fillRect(_width - _boxSize, _height - _boxSize, _boxSize, _boxSize);
            g.setColor(Color.RED);
            g.fillRect(_commands.xPosition*_boxSize+2, _commands.yPosition*_boxSize+2, _boxSize-4, _boxSize-4);

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
