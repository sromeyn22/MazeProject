import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FrontPageGraphics {
    int dimensions;
    int typeOfMaze; //2 for 2D, 3 for 3D
    int key;
    JFrame frame;
    boolean check;

    public FrontPageGraphics() {
        frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("maze.jpeg"));
        } catch (IOException e) {
        }
        frame.setContentPane(new ImagePanel(background));

        dimensions = 15;
        JLabel dimensionsLabel = new JLabel(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
        dimensionsLabel.setForeground(Color.WHITE);
        dimensionsLabel.setBounds(200, 300, 100, 50);

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
        typeOfMazeButton.setBounds(700, 300, 100, 50);

        JButton dimensionsAdd1 = new JButton("Add 1 to the dimensions");
        dimensionsAdd1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(dimensions == 30)) {
                    dimensions++;
                    dimensionsLabel.setText(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
                }
            }
        });
        dimensionsAdd1.setBounds(150, 250, 300, 50);

        JButton dimensionsAdd5 = new JButton("Add 5 to the dimensions");
        dimensionsAdd5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimensions+=5;
                if(dimensions > 30)
                    dimensions = 30;
                dimensionsLabel.setText(Integer.toString(dimensions) + " x " + Integer.toString(dimensions));
            }
        });
        dimensionsAdd5.setBounds(200, 100, 100, 50);

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
        dimensionsMinus1.setBounds(200, 400, 100, 50);

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
        dimensionsMinus5.setBounds(200, 500, 100, 50);

        JTextField keyText = new JTextField();
        keyText.setBounds(200, 600, 100, 50);

        JLabel keyLabel = new JLabel();
        keyLabel.setForeground(Color.WHITE);
        keyLabel.setText("Key:");
        keyLabel.setBounds(100, 600, 100, 50);

        JLabel typeOfMazeLabel = new JLabel();
        typeOfMazeLabel.setForeground(Color.WHITE);
        typeOfMazeLabel.setText("Choose what type of maze you want");
        typeOfMazeLabel.setBounds(640, 250, 300, 50);

        JLabel errorMessage = new JLabel();
        errorMessage.setForeground(Color.WHITE);
        errorMessage.setText("Error: enter a number for the key");
        errorMessage.setBounds(160, 650, 300,50);
        errorMessage.setVisible(false);

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check = false;
                if(typeOfMazeButton.getText().equals("2D"))
                    typeOfMaze = 2;
                else
                    typeOfMaze = 3;

                try {
                    key = Integer.parseInt(keyText.getText());
                    check = true;
                } catch (NumberFormatException e2) {
                    errorMessage.setVisible(true);
                }

                if(check){
                    frame.dispose();
//                    Maze maze = new Maze(dimensions, key);
//                    if(typeOfMaze == 2){
//                        GraphicInterface2D 2d = new GraphicInterface2D();
//                    } else
//                        GraphicInterface3D 3d = new GraphicInterface3D()
                }
            }
        });
        start.setBounds(800, 600, 100, 50);

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
        frame.setSize(1024, 768);
    }
}

class ImagePanel extends JComponent {
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