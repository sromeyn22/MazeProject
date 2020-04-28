/**
 * The FrontPageGraphics class generates a graphical user interface that acts as a main menu and
 * allows the user to select the options they want for the maze (2D vs 3D, enemies, dimensions of
 * the maze, random key)
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FrontPageGraphics {
    /**
     * The number of dimensions for the maze that the user inputted (bounded from 2-30 with
     * a default value of 15)
     */
    int dimensions;
    /**
     * An integer that stores what type of maze the user wants, either holds the value 2 for
     * a 2D maze or 3 for a 3D maze
     */
    int typeOfMaze;
    /**
     * An user inputted seed to generate a random maze
     */
    int key;
    /**
     * The frame that is displayed that serves as the GUI
     */
    JFrame frame;
    /**
     * A boolean to keep track if the start button is pressed to continue the program
     */
    boolean check;
    /**
     * If the user wants enemies or not
     */
    boolean ifEnemies;
    /**
     * Holds the maximum dimensions the maze can have
     */
    int maxDimensions;

    /**
     * Default constructor that creates the main menu
     */
    public FrontPageGraphics() {
        frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Using a BufferedImage, tries to set the background of the frame to the maze.jpeg file
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("maze.jpeg"));
        } catch (IOException e) {
            System.out.println("Error: 'maze.jpeg' background file could not be found.");
            frame.setBackground(Color.BLACK);
        }
        frame.setContentPane(new ImagePanel(background));

        // Sets the intial number of dimensions to 15 and and the max number of dimensions at 30
        dimensions = 15;
        maxDimensions = 30;

        // A label that shows the user the dimensions of the maze
        JLabel dimensionsLabel = new JLabel(Integer.toString(dimensions) + " x " + Integer.toString(dimensions), SwingConstants.CENTER);
        dimensionsLabel.setForeground(Color.BLACK);
        dimensionsLabel.setBackground(Color.WHITE);
        dimensionsLabel.setOpaque(true);
        dimensionsLabel.setBounds(225, 303, 70, 30);
        dimensionsLabel.setFont(dimensionsLabel.getFont().deriveFont(dimensionsLabel.getFont().getStyle() | Font.BOLD));

        // A button that tracks if the user wants a 2D or 3D maze
        JButton typeOfMazeButton = new JButton("2D");
        typeOfMazeButton.addActionListener(new ActionListener() {
            @Override
            // if the button is pressed, the type of maze is switched
            public void actionPerformed(ActionEvent e) {
                if (typeOfMazeButton.getText().equals("2D"))
                    typeOfMazeButton.setText("3D");
                else
                    typeOfMazeButton.setText("2D");
            }
        });
        typeOfMazeButton.setBounds(700, 250, 100, 50);

        // A button that adds one to the dimensions of the maze
        JButton dimensionsAdd1 = new JButton("Add 1 to the dimensions");
        dimensionsAdd1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(dimensions == maxDimensions)) {
                    dimensions++;
                    dimensionsLabel.setText(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
                }
            }
        });
        dimensionsAdd1.setBounds(150, 250, 220, 40);

        // A button that adds five to the dimensions of the maze
        JButton dimensionsAdd5 = new JButton("Add 5 to the dimensions");
        dimensionsAdd5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimensions+=5;
                if(dimensions > maxDimensions)
                    dimensions = maxDimensions;
                dimensionsLabel.setText(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
            }
        });
        dimensionsAdd5.setBounds(150, 200, 220, 40);

        // A button that subtracts one from the dimensions of the maze
        JButton dimensionsMinus1 = new JButton("Subtract 1 to the dimensions");
        dimensionsMinus1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(dimensions == 2)) {
                    dimensions--;
                    dimensionsLabel.setText(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
                }
            }
        });
        dimensionsMinus1.setBounds(150, 350, 220, 40);

        // A button that subtracts five from the dimensions of the maze
        JButton dimensionsMinus5 = new JButton("Subtract 5 to the dimensions");
        dimensionsMinus5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimensions-=5;
                if(dimensions < 2)
                    dimensions = 2;
                dimensionsLabel.setText(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
            }
        });
        dimensionsMinus5.setBounds(150, 400, 220, 40);

        // A text box where the user can enter the key/seed that they want for the maze
        JTextField keyText = new JTextField();
        keyText.setBounds(220, 600, 100, 40);

        // A label that lets the user know the text box is for the key
        JLabel keyLabel = new JLabel();
        keyLabel.setForeground(Color.WHITE);
        keyLabel.setText("Key:");
        keyLabel.setBounds(170, 600, 100, 40);
        keyLabel.setFont(keyLabel.getFont().deriveFont(keyLabel.getFont().getStyle() | Font.BOLD));

        // A label for the type of maze button
        JLabel typeOfMazeLabel = new JLabel();
        typeOfMazeLabel.setForeground(Color.WHITE);
        typeOfMazeLabel.setText("Choose what type of maze you want");
        typeOfMazeLabel.setBounds(640, 200, 250, 50);
        typeOfMazeLabel.setFont(typeOfMazeLabel.getFont().deriveFont(typeOfMazeLabel.getFont().getStyle() | Font.BOLD));

        // An invisible label that displays itself if the user types a non-integer in the key
        JLabel errorMessage = new JLabel();
        errorMessage.setForeground(Color.WHITE);
        errorMessage.setText("Error: enter an integer for the key");
        errorMessage.setBounds(160, 650, 300,50);
        errorMessage.setVisible(false);

        // A button that tracks if the user wants enemies
        JButton enemiesButton = new JButton("Yes");
        enemiesButton.addActionListener(new ActionListener() {
            @Override
            // If the button is pressed it switches its status
            public void actionPerformed(ActionEvent e) {
                if (enemiesButton.getText().equals("Yes"))
                    enemiesButton.setText("No");
                else
                    enemiesButton.setText("Yes");
            }
        });
        enemiesButton.setBounds(700, 350, 100, 50);

        // The start button
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            //  When the button is clicked, it sets all the fields to the options the user inputted
            public void actionPerformed(ActionEvent e) {
                check = false;
                if (typeOfMazeButton.getText().equals("2D"))
                    typeOfMaze = 2;
                else
                    typeOfMaze = 3;

                if (enemiesButton.getText().equals("Yes"))
                    ifEnemies = true;
                else
                    ifEnemies = false;

                // if there is no key inputted, a random seed will be generated
                if (keyText.getText().equals("")) {
                    check = true;
                    Random rand = new Random();
                    key = rand.nextInt(10000);
                } else {
                    // check if the key inputted was an integer
                    try {
                        key = Integer.parseInt(keyText.getText());
                        check = true;
                    } catch (NumberFormatException e2) {
                        // if the key was not an integer, the error message is displayed and the code does not move forward
                        errorMessage.setVisible(true);
                    }
                }

                // if everything checks out then the frame is deleted
                if(check){
                    frame.dispose();
                }
            }
        });
        start.setBounds(700, 600, 100, 50);

        // A label for the enemies button
        JLabel enemiesLabel = new JLabel();
        enemiesLabel.setForeground(Color.WHITE);
        enemiesLabel.setText("Do you want enemies in the maze");
        enemiesLabel.setBounds(640, 300, 250, 50);
        enemiesLabel.setFont(enemiesLabel.getFont().deriveFont(enemiesLabel.getFont().getStyle() | Font.BOLD));

        // Adds all the components to the frame
        frame.add(start);
        frame.add(errorMessage);
        frame.add(dimensionsAdd1);
        frame.add(dimensionsAdd5);
        frame.add(dimensionsMinus1);
        frame.add(dimensionsMinus5);
        frame.add(keyLabel);
        frame.add(keyText);
        frame.add(dimensionsLabel);
        frame.add(typeOfMazeButton);
        frame.add(typeOfMazeLabel);
        frame.add(enemiesLabel);
        frame.add(enemiesButton);
        frame.setSize(1024, 768);
        frame.setResizable(false);
    }
}

/**
 * A class that stores an image and acts as a JComponent so the main menu can set the background to an image
 * (Code from https://stackoverflow.com/questions/18127581/how-do-i-display-an-image-on-a-frame-using-paintcomponent)
 */
class ImagePanel extends JComponent {
    /**
     * The image for the background of the main menu
     */
    private Image image;

    /**
     * A constructor that initializes the value of the image
     * @param image the image passed in
     */
    public ImagePanel(Image image) {
        this.image = image;
    }

    /**
     * Displays the image on the main menu
     * @param g Graphics object for the paintComponent method
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}