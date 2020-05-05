/**
 * This class creates the GUI in which the 2D maze is displayed. In the maze, the player can control their
 * character using the arrow keys and the goal is to reach the bottom right corner of the maze. Enemies or health
 * in the 3D maze are represented by a blue circle in the cell. The player can also display the solution to the maze
 * by pressing '1' on the keyboard.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicInterface2D extends JPanel implements KeyListener {
    /**
     * A representation of the randomly generated maze as a 2D array of cells
     */
    Cell[][] mazeGrid;
    /**
     * The pixel size of each cell in the maze to determine the frame size with
     */
    int boxSize;
    /**
     * The x position of the player in the maze
     */
    int xPosition;
    /**
     * The y position of the player in the maze
     */
    int yPosition;
    /**
     * If the solution should be displayed
     */
    boolean solution;
    /**
     * The JFrame where the 2D maze is displayed
     */
    JFrame frame2D;

    /**
     * The default constructor initializes the member fields and the JFrame that displays the maze
     * @param grid the maze represented as a 2D array of cells
     */
    public GraphicInterface2D(Cell[][] grid) {
        // initialize fields
        mazeGrid = grid;
        boxSize = 20;
        xPosition = 0;
        yPosition = 0;
        solution = false;

        // initialize the frame
        frame2D = new JFrame("2D Maze");
        frame2D.setSize(boxSize*mazeGrid.length, boxSize*mazeGrid.length+20);
        frame2D.addKeyListener(this);
        frame2D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2D.setResizable(false);
        frame2D.setContentPane(this);
    }

    /**
     * This method draws the maze and the player
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {

        // makes the background of the frame white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, mazeGrid.length*boxSize, mazeGrid.length*boxSize);
        // draws the borders of the frame
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, mazeGrid.length*boxSize, 3);
        g.fillRect(mazeGrid.length*boxSize - 3, 0, 3, mazeGrid.length*boxSize);
        g.fillRect(0, mazeGrid.length*boxSize - 3, mazeGrid.length*boxSize, 3);
        g.fillRect(0, 0, 3, mazeGrid.length*boxSize);


        // If the player wants to see the solution
        if (solution == true) {
            // Iterate through each cell in the maze
            for (int column = 0; column < mazeGrid.length; column += 1) {
                for (int row = 0; row < mazeGrid.length; row += 1) {

                    // The coordinates of this cell.
                    int initx = column * boxSize;
                    int inity = row * boxSize;

                    // If the cell is part of the solution, make it pink
                    g.setColor(Color.pink);
                    if (mazeGrid[column][row].Path) {
                        g.fillRect(initx, inity, boxSize, boxSize);
                    }

                    // Draw the walls for each cell
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
            // Iterate through each cell in the maze
            for (int column = 0; column < mazeGrid.length; column += 1) {
                for (int row = 0; row < mazeGrid.length; row += 1) {

                    // The coordinates of this cell
                    int initx = column * boxSize;
                    int inity = row * boxSize;

                    // Draw the walls for each cell
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
                    // If there would've been an enemy in the cell (in 3D), make a blue oval in that cell
                    if(mazeGrid[column][row].enemy){
                        g.setColor(Color.cyan);
                        g.fillOval(initx, inity, boxSize, boxSize);
                    }
                }
            }
        }
        // Draw the starting and ending square
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, boxSize, boxSize);
        g.fillRect(mazeGrid.length*boxSize - boxSize, mazeGrid.length*boxSize - boxSize, boxSize, boxSize);

        // Draw the player
        g.setColor(Color.RED);
        g.fillRect(xPosition * boxSize + 2, yPosition * boxSize + 2, boxSize - 4, boxSize - 4);
    }


    /**
     * This method uses the KeyListener to update the player's position when they press one of the arrow keys
     * or displays the solution when they press '1'.
     * @param e tracks which key the user pressed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // allows the player to move right if there is no wall to their right
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
        // redraws the maze with the player's position now updated
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}
