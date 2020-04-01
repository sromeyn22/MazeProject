

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
// =============================================================================



// =============================================================================
public class GraphicInterface extends JPanel implements UserInterface{
// =============================================================================

    public GraphicInterface (Cells[][] grid, Commands commands, boolean[] walls, JFrame frame) {

        _walls = walls;
        _commands = commands;
        _grid   = grid;
        if(_commands.type == 0){
            _width  = grid.length * _boxSize;
            _height = grid.length * _boxSize;
        }else if(_commands.type == 1){
            _width  = 700;
            _height = 700;
        }


        setPreferredSize(new Dimension(_width, _height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);

    } // GraphicInterface ()
    // =========================================================================



    // =========================================================================
    public void display (Graphics g) {
        if(_commands.type == 0){
            // Draw the background
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


        } else if(_commands.type == 1){
            draw3d(g);
        }
    } // display ()
    // =========================================================================

    public void draw3d(Graphics g){
        floor(g);
        mist(g);
        walls(g);
    }

    private void walls(Graphics g) {
        if(_walls[0]){
            g.setColor(Color.BLUE);
            g.fillRect(80, 260, 180, 180);
            if(_commands.xPosition == (_grid.length-1) && _commands.yPosition == (_grid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)_commands.xPosition + (double)_commands.yPosition)/(2*(double)_grid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints = {80, 260, 200};
            int[] yPoints = {440, 440, 500};
            g.fillPolygon(xPoints, yPoints, 3);
        }
        if(_walls[1]){
            g.setColor(Color.BLUE);
            g.fillRect(260, 260, 180, 180);
            if(_commands.xPosition == (_grid.length-1) && _commands.yPosition == (_grid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)_commands.xPosition + (double)_commands.yPosition)/(2*(double)_grid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints = {260, 200, 500, 440};
            int[] yPoints = {440, 500, 500, 440};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(_walls[2]){
            g.setColor(Color.BLUE);
            g.fillRect(440, 260, 180, 180);
            if(_commands.xPosition == (_grid.length-1) && _commands.yPosition == (_grid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)_commands.xPosition + (double)_commands.yPosition)/(2*(double)_grid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints = {620, 440, 500};
            int[] yPoints = {440, 440, 500};
            g.fillPolygon(xPoints, yPoints, 3);

        }
        if(_walls[3]){
            g.setColor(Color.BLUE);
            int[] xPoints = {0, 0, 80, 80};
            int[] yPoints = {233, 467, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);
            if(_commands.xPosition == (_grid.length-1) && _commands.yPosition == (_grid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)_commands.xPosition + (double)_commands.yPosition)/(2*(double)_grid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints2 = {80, 200, 0, 0};
            int[] yPoints2 = {440, 500, 500, 467};
            g.fillPolygon(xPoints2, yPoints2, 4);
        }
        if(_walls[4]){
            g.setColor(Color.BLUE);
            int[] xPoints = {200, 200, 260, 260};
            int[] yPoints = {200, 500, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        if(_walls[5]){
            g.setColor(Color.BLUE);
            int[] xPoints = {500, 500, 440, 440};
            int[] yPoints = {200, 500, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(_walls[6]){
            g.setColor(Color.BLUE);
            int[] xPoints = {700, 700, 620, 620};
            int[] yPoints = {233, 467, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);
            if(_commands.xPosition == (_grid.length-1) && _commands.yPosition == (_grid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)_commands.xPosition + (double)_commands.yPosition)/(2*(double)_grid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints2 = {620, 500, 700, 700};
            int[] yPoints2 = {440, 500, 500, 467};
            g.fillPolygon(xPoints2, yPoints2, 4);

        }
        if(_walls[7]){
            g.setColor(Color.BLUE);
            g.fillRect(0, 200, 200, 300);

        }
        if(_walls[8]){
            g.setColor(Color.BLUE);
            g.fillRect(200, 200, 300, 300);


        }
        if(_walls[9]){
            g.setColor(Color.BLUE);
            g.fillRect(500, 200, 200, 300);


        }
        if(_walls[10]){
            g.setColor(Color.BLUE);
            int[] xPoints = {135, 135, 200, 200};
            int[] yPoints = {135, 565, 500, 200};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        if(_walls[11]){
            g.setColor(Color.BLUE);
            int[] xPoints = {565, 565, 500, 500};
            int[] yPoints = {135, 565, 500, 200};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(_walls[12]){
            g.setColor(Color.BLUE);
            g.fillRect(0, 135, 135, 430);

        }
        if(_walls[13]){
            g.setColor(Color.BLUE);
            g.fillRect(135, 135, 430, 430);

        }
        if(_walls[14]){
            g.setColor(Color.BLUE);
            g.fillRect(565, 135, 135, 430);

        }
        if(_walls[15]){
            g.setColor(Color.BLUE);
            int[] xPoints = {65, 65, 135, 135};
            int[] yPoints = {65, 635, 565, 135};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(_walls[16]){
            g.setColor(Color.BLUE);
            int[] xPoints = {635, 635, 565, 565};
            int[] yPoints = {65, 635, 565, 135};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(_walls[17]){
            g.setColor(Color.BLUE);
            g.fillRect(0, 65, 65, 570);

        }
        if(_walls[18]){
            g.setColor(Color.BLUE);
            g.fillRect(65, 65, 570, 570);
        }
        if(_walls[19]){
            g.setColor(Color.BLUE);
            g.fillRect(635, 65, 65, 570);

        }
        if(_walls[20]){
            g.setColor(Color.BLUE);
            int[] xPoints = {0, 0, 65, 65};
            int[] yPoints = {0, 700, 635, 65};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        if(_walls[21]){
            g.setColor(Color.BLUE);
            int[] xPoints = {700, 700, 635, 635};
            int[] yPoints = {0, 700, 635, 65};
            g.fillPolygon(xPoints, yPoints, 4);
        }
    }


    private void mist(Graphics g) {
        for (int i = 0; i < 50000; i++){
            int x = (int) (Math.random()*(_width+20)-10);
            int y = (int) (Math.random()*(_width+20)-10);
            float q = ((float)x-350)*((float)x-350)/2500+425;
            if(y > q){
                i--;
            } else {
                int color = (int) (Math.random()*75 + 75);
                g.setColor( new Color(color, color, color));
                int size = (int) (Math.random()*30 + 1);
                g.fillOval(x, y, size, size);
            }
        }
    }

    private void floor(Graphics g) {
        if(_commands.xPosition == (_grid.length-1) && _commands.yPosition == (_grid.length-1)){
            g.setColor(Color.GREEN);
        } else {
            double ground = 255-(255)*(((double)_commands.xPosition + (double)_commands.yPosition)/(2*(double)_grid.length));
            g.setColor( new Color(255, (int)ground, (int)ground));
        }
        g.fillRect(0, 0, _width, _height);
        g.setColor(Color.BLACK);
        g.drawLine(0, 700, 260, 440);
        g.drawLine(700, 700, 440, 440);
        g.drawLine(0, 500, 700, 500);
        g.drawLine(0, 565, 700, 565);
        g.drawLine(0, 635, 700, 635);
    }


    // =========================================================================
    /**
     * Display the state of the <code>Cell</code>s in the <code>Grid</code>.
     */
    public void display () {
        repaint();

    } // display ()
    // =========================================================================



    // =========================================================================
    public void paintComponent (Graphics g) {

        super.paintComponent(g);
        display(g);

    } // paintComponent ()
    // =========================================================================



    // =========================================================================
    /**
     * Keep control of the program until it is time to advance the state of the
     * <code>Game</code>.
     */
    public void triggerMove () {

        // Pause for a fixed interval.
        try {
            Thread.sleep(0);
        } catch(InterruptedException e) {}

    } // triggerMove ()
    // =========================================================================



    // =========================================================================
    // DATA MEMBERS

    /** The window width. */
    private int _width;

    /** The default window height. */
    private int _height;

    /** The size of the boxes for each cell. */
    private final static int _boxSize = 20;

    /** The delay between generations, in ms. */
    //private final static int _wait   = 100;

    /** The <code>Game</code> that this interface is controlling. */
    private Cells[][] _grid;

    private Commands _commands;

    private boolean[] _walls;
    // =========================================================================



// =============================================================================
} // class GraphicInterface
// =============================================================================
