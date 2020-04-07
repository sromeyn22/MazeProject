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

    public FrontPageGraphics() {
        frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("maze.jpeg"));
        } catch (IOException e){
        }
        frame.setContentPane(new ImagePanel(background));

        JButton typeOfMazeButton;
        JButton start;
        JButton dimensionsAdd1;
        JButton dimensionsAdd5;
        JButton dimensionsMinus1;
        JButton dimensionsMinus5;

        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setText("label test");
        label.setBounds(100, 100, 200, 50);
        final JTextField tf=new JTextField();
        tf.setBounds(200,200, 150,20);
        JButton b = new JButton("change label");
        b.setBounds(200, 300, 200, 50);
        JButton addButton = new JButton( new AbstractAction("add") { @Override public void actionPerformed( ActionEvent e ) {tf.setText("added"); } });
        addButton.setBounds(200, 400, 200, 50);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(b.getText());
            }
        });
        frame.add(b);
        frame.add(addButton);
        frame.add(tf);
        frame.add(label);
        frame.setSize(1024, 768);
        frame.setVisible(true);
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