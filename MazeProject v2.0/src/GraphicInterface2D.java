import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicInterface2D extends JPanel implements KeyListener {
    Cell[][] mazeGrid;
    int boxSize;
    int xPosition;
    int yPosition;
    boolean solution;
    JFrame frame2D;

    public GraphicInterface2D(Cell[][] grid) {
        mazeGrid = grid;
        boxSize = 20;
        xPosition = 0;
        yPosition = 0;
        solution = false;
        frame2D = new JFrame("2D Maze");
        frame2D.setSize(boxSize*mazeGrid.length, boxSize*mazeGrid.length);
        frame2D.addKeyListener(this);
        frame2D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2D.setContentPane(this);
    }


    public void paintComponent(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Maze.dimensions, Maze.dimensions);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Maze.dimensions, 3);
        g.fillRect(Maze.dimensions - 3, 0, 3, Maze.dimensions);
        g.fillRect(0, Maze.dimensions - 3, Maze.dimensions, 3);
        g.fillRect(0, 0, 3, Maze.dimensions);


        // Iterate over all cells
        if (solution) {
            for (int column = 0; column < mazeGrid.length; column += 1) {
                for (int row = 0; row < mazeGrid.length; row += 1) {

                    // The coordinates of this cell.
                    int initx = column * boxSize;
                    int inity = row * boxSize;

                    g.setColor(Color.pink);
                    if (mazeGrid[column][row].Path) {
                        g.fillRect(initx, inity, boxSize, boxSize);

                    }
                    g.setColor(Color.BLACK);
                    if (mazeGrid[column][row].UpWall) {
                        g.fillRect(initx, inity, boxSize, 2);
                    }
                    if (mazeGrid[column][row].RightWall) {
                        g.fillRect(initx + boxSize - 2, inity, 2, boxSize);
                    }
                    if (mazeGrid[column][row].DownWall) {
                        g.fillRect(initx, inity + boxSize - 2, boxSize, 2);
                    }
                    if (mazeGrid[column][row].LeftWall) {
                        g.fillRect(initx, inity, 2, boxSize);
                    }
                }
            }
        } else {
            for (int column = 0; column < mazeGrid.length; column += 1) {
                for (int row = 0; row < mazeGrid.length; row += 1) {


                    // The coordinates of this cell.
                    int initx = column * boxSize;
                    int inity = row * boxSize;

                    g.setColor(Color.BLACK);
                    if (mazeGrid[column][row].UpWall) {
                        g.fillRect(initx, inity, boxSize, 2);
                    }
                    if (mazeGrid[column][row].RightWall) {
                        g.fillRect(initx + boxSize - 2, inity, 2, boxSize);
                    }
                    if (mazeGrid[column][row].DownWall) {
                        g.fillRect(initx, inity + boxSize - 2, boxSize, 2);
                    }
                    if (mazeGrid[column][row].LeftWall) {
                        g.fillRect(initx, inity, 2, boxSize);
                    }
                }
            }
        }
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, boxSize, boxSize);
        g.fillRect(Maze.dimensions - boxSize, Maze.dimensions - boxSize, boxSize, boxSize);
        g.setColor(Color.RED);
        g.fillRect(xPosition * boxSize + 2, yPosition * boxSize + 2, boxSize - 4, boxSize - 4);

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