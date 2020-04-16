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
    int dimensions;
    int typeOfMaze; //2 for 2D, 3 for 3D
    int key;
    JFrame frame;
    boolean check;
    boolean ifEnemies;
    int maxDimensions;

    public FrontPageGraphics() {
        frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("maze.jpeg"));
        } catch (IOException e) {
            System.out.println("Error: 'maze.jpeg' background file could not be found.");
            frame.setBackground(Color.BLACK);
        }
        frame.setContentPane(new ImagePanel(background));

        dimensions = 15;
        maxDimensions = 30;
        JLabel dimensionsLabel = new JLabel(Integer.toString(dimensions) + " x " + Integer.toString(dimensions), SwingConstants.CENTER);
        dimensionsLabel.setForeground(Color.BLACK);
        dimensionsLabel.setBackground(Color.WHITE);
        dimensionsLabel.setOpaque(true);
        dimensionsLabel.setBounds(225, 303, 70, 30);
        dimensionsLabel.setFont(dimensionsLabel.getFont().deriveFont(dimensionsLabel.getFont().getStyle() | Font.BOLD));

        JButton typeOfMazeButton = new JButton("2D");
        typeOfMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typeOfMazeButton.getText().equals("2D"))
                    typeOfMazeButton.setText("3D");
                else
                    typeOfMazeButton.setText("2D");
            }
        });
        typeOfMazeButton.setBounds(700, 250, 100, 50);

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

        JTextField keyText = new JTextField();
        keyText.setBounds(220, 600, 100, 40);

        JLabel keyLabel = new JLabel();
        keyLabel.setForeground(Color.WHITE);
        keyLabel.setText("Key:");
        keyLabel.setBounds(170, 600, 100, 40);
        keyLabel.setFont(keyLabel.getFont().deriveFont(keyLabel.getFont().getStyle() | Font.BOLD));

        JLabel typeOfMazeLabel = new JLabel();
        typeOfMazeLabel.setForeground(Color.WHITE);
        typeOfMazeLabel.setText("Choose what type of maze you want");
        typeOfMazeLabel.setBounds(640, 200, 250, 50);
        typeOfMazeLabel.setFont(typeOfMazeLabel.getFont().deriveFont(typeOfMazeLabel.getFont().getStyle() | Font.BOLD));

        JLabel errorMessage = new JLabel();
        errorMessage.setForeground(Color.WHITE);
        errorMessage.setText("Error: enter a number for the key");
        errorMessage.setBounds(160, 650, 300,50);
        errorMessage.setVisible(false);

        JButton enemiesButton = new JButton("Yes");
        enemiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enemiesButton.getText().equals("Yes"))
                    enemiesButton.setText("No");
                else
                    enemiesButton.setText("Yes");
            }
        });
        enemiesButton.setBounds(700, 350, 100, 50);

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
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

                if (keyText.getText().equals("")) {
                    check = true;
                    Random rand = new Random();
                    key = rand.nextInt(10000);
                } else {
                    try {
                        key = Integer.parseInt(keyText.getText());
                        check = true;
                    } catch (NumberFormatException e2) {
                        errorMessage.setVisible(true);
                    }
                }

                if(check){
                    frame.dispose();
                }
            }
        });
        start.setBounds(700, 600, 100, 50);

        JLabel enemiesLabel = new JLabel();
        enemiesLabel.setForeground(Color.WHITE);
        enemiesLabel.setText("Do you want enemies in the maze");
        enemiesLabel.setBounds(640, 300, 250, 50);
        enemiesLabel.setFont(enemiesLabel.getFont().deriveFont(enemiesLabel.getFont().getStyle() | Font.BOLD));

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
    }
}

class ImagePanel extends JComponent { // code from https://stackoverflow.com/questions/18127581/how-do-i-display-an-image-on-a-frame-using-paintcomponent
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}