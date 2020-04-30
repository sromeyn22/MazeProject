/**
 * The GraphicInterface3D class generates an interactive depiction of a 3D maze based on user specifications chosen
 * in FrontPageGraphics (enemies, dimension of maze, random key.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import java.awt.Graphics;

public class GraphicInterface3D extends JPanel implements KeyListener {
    /**
     * Double Cell array with length determined by user inputted dimensions of maze (bounded from 2-30 with
     * a default value of 15).
     */
    Cell[][] mazeGrid;
    /**
     *Number of steps until solution.
     */
    int difficulty;
    /**
     * Direction that the user is facing in the maze.
     */
    int direction; // 0 for north, 1 for east, 2 for south, 3 for west
    /**
     * X-position of user in the maze in array mazeGrid.
     */
    int xPosition;
    /**
     * Y-position of user in the maze in array mazeGrid.
     */
    int yPosition;
    /**
     * Boolean array containing statements for the graphics of different walls within the maze.
     */
    boolean[] displayWalls = new boolean[22];
    /**
     * JFrame for maze.
     */
    JFrame frame3D;
    /**
     * How much life (out of 100) that the user has.
     */
    int life;
    /**
     * Integer array of cells that are in view for the user. Contains statements that determine if there is an enemy, health, or nothing.
     */
    int[] farcells = new int[7];
    /**
     * If the user has completed the maze or not.
     */
    boolean winner;

    /**
     * Default constructor that creates the 3D depiction of the maze.
     * @param grid user inputted dimensions of the maze
     * @param _difficulty number of steps of solution
     */
    public GraphicInterface3D(Cell[][] grid, int _difficulty){
        //Initialize fields.
        mazeGrid = grid;
        difficulty = _difficulty;
        direction = 2;
        xPosition = 0;
        yPosition = 0;
        life = 100;
        winner = false;
        frame3D = new JFrame("3D Maze");
        frame3D.setSize(700, 700);
        frame3D.addKeyListener(this);
        frame3D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3D.setResizable(false);
        frame3D.setContentPane(this);
    }

     /**
     * Draws to screen floor, mist, walls, compass, and enemies or health.
     * @param g object Graphics g.
     */
    public void paintComponent(Graphics g){
        displayWalls = whichWallsToDraw();
        floor(g);
        mist(g);
        walls(g);
        enemy(g);
        compass(g);
        
        //Draws a heart or an enemy in the current position of the user.
        if(mazeGrid[xPosition][yPosition].heart && mazeGrid[xPosition][yPosition].life > 0) {
            drawHeart(g, 270, 350, 430, 350, 500, 530, 650);
            g.setColor(Color.GREEN);
            //Health points remaining in heart.
            g.fillRect(280, 180, 18 * mazeGrid[xPosition][yPosition].life, 10);
        }
        
        if(mazeGrid[xPosition][yPosition].enemy && mazeGrid[xPosition][yPosition].life > 0) {
            enemy(g,240, 220, 380, 267, 236, 350, 378, 172, 345, 350, 464, 433, 275,
                    240, 330, 350, 180, 350, 370, 460, 425, 250, 250, 200, 360, 280,
                    310, 50, 372, 289, 319, 32, 380, 275, 380, 150, 75, 310, 317,
                    13, 400, 315, 422, 25, 20, 360);
            
            //Health level remaining of enemy.
            g.setColor(Color.RED);
            g.fillRect(260 + 18 * mazeGrid[xPosition][yPosition].life, 180, 180 - 18 * mazeGrid[xPosition][yPosition].life, 10);
            g.setColor(Color.GREEN);
            g.fillRect(260, 180, 18*mazeGrid[xPosition][yPosition].life, 10);
        }
        
        //Difficulty level in top right corner. Current health level in bottom left corner.
        g.setColor(Color.RED);
        g.drawString(Integer.toString(difficulty), 640, 25);
        g.fillRect(50+life, 650, 100-life, 10);
        g.setColor(Color.GREEN);
        g.fillRect(50, 650, life, 10);
        
        //Decrease in health levels when enemy continues to be in frame.
        if(mazeGrid[xPosition][yPosition].enemy && mazeGrid[xPosition][yPosition].life > 0 && life > 0){
            life = life - 5;
            try{
                Thread.sleep(250);
            } catch (InterruptedException e){

            }
            repaint();
        }
        //Increase in health levels when collecting from heart.
        if(mazeGrid[xPosition][yPosition].heart && mazeGrid[xPosition][yPosition].life > 0 && life > 0){
        
            if (life < 100) { life = life + 5; }
        }
         //User is out of life points, loses the maze.
        if(life == 0){
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.drawString("YOU LOSE", 260, 200);
        }
    }
    
/**
     * Method that draws enemies.
     * @param g object Graphics g
     * @param ovalXY XY position of enemy outline
     * @param ovalW width of enemy outline
     * @param ovalH height of enemy outline
     * @param xp1 left base x point of first ear outline
     * @param xp2 apex x point of first ear outline
     * @param xp3 right base x point of first ear outline
     * @param yp1 left base y point of first ear outline, right base x point of second ear outline
     * @param yp2 apex y point of first ear outline
     * @param yp3 right base y point of first ear outline, left base x point of second ear outline
     * @param xp4 left base x point of second ear outline
     * @param xp5 apex x point of second ear outline
     * @param xp6 right base x point of second ear outline
     * @param xp7 left base x point of first ear 
     * @param xp8 apex x point of first ear
     * @param xp9 right base x point of first ear
     * @param yp7 left base y point of first ear, right base x point of second ear 
     * @param yp8 apex y point of first ear 
     * @param yp9 right base y point of first ear, left base x point of second ear
     * @param xp10 left base x point of second ear 
     * @param xp11 apex x point of second ear 
     * @param xp12 right base x point of second ear 
     * @param ovalx1 x position of enemy
     * @param ovaly1 y position of enemy
     * @param ovalw1 width of enemy
     * @param ovalh1 height of enemy
     * @param eyeX x position of left eye outline
     * @param eyeY y position of eye outlines
     * @param eyeWH width and height of eye outlines
     * @param eyeX1 x position of right eye outline
     * @param eyeX2 x position of left eye
     * @param eyeY1 y position of eyes
     * @param eyeWH1 width and height of eyes
     * @param eyeX3 x position of right eye
     * @param arcX x position of mouth
     * @param arcY y position of mouth
     * @param arcW mouth width
     * @param arcH mouth height
     * @param eyeX4 x position of left pupil 
     * @param eyeY2 y positions of pupil 
     * @param eyeWH2 width and height of pupil
     * @param eyeX5 x position of right pupil
     * @param tX x position of left tooth
     * @param tY y positions of teeth
     * @param tW tooth width
     * @param tH tooth height
     * @param tX1 x position of right tooth
     */
    public void enemy(Graphics g, int ovalXY, int ovalW, int ovalH, int xp1, int xp2, int xp3, int yp1, int yp2, int yp3, int xp4, int xp5, int xp6,
                      int xp7, int xp8, int xp9, int yp7, int yp8, int yp9, int xp10, int xp11, int xp12, int ovalx1, int ovaly1, int ovalw1, int ovalh1,
                      int eyeX, int eyeY, int eyeWH, int eyeX1, int eyeX2, int eyeY1, int eyeWH1, int eyeX3, int arcX, int arcY, int arcW, int arcH, int eyeX4,
                      int eyeY2, int eyeWH2, int eyeX5, int tX, int tY, int tW, int tH, int tX1) {

        //Enemy outline.
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillOval(ovalXY, ovalXY, ovalW, ovalH);
        int[] xPoints3 = {xp1, xp2, xp3};
        int[] yPoints3 = {yp1, yp2, yp3};
        g2.fillPolygon(xPoints3, yPoints3, 3);
        int[] xPoints2 = {xp4, xp5, xp6};
        int[] yPoints2 = {yp3, yp2, yp1};
        g2.fillPolygon(xPoints2, yPoints2, 3);

        //Ears.
        Color ears = new Color(222, 71, 141);
        g2.setColor(ears);
        int[] xPoints = {xp7, xp8, xp9};
        int[] yPoints = {yp7, yp8, yp9};
        g2.fillPolygon(xPoints, yPoints, 3);
        int[] xPoints1 = {xp10, xp11, xp12};
        int[] yPoints1 = {yp9, yp8, yp7};
        g2.fillPolygon(xPoints1, yPoints1, 3);

        //Body.
        Color body = new Color(227, 126, 5);
        g.setColor(body);
        g.fillOval(ovalx1, ovaly1, ovalw1, ovalh1);

        //Facial features.
        g.setColor(Color.WHITE);
        g.fillOval(eyeX, eyeY, eyeWH, eyeWH);
        g.fillOval(eyeX1, eyeY, eyeWH, eyeWH);
        g.setColor(Color.BLACK);
        g.fillOval(eyeX2, eyeY1, eyeWH1, eyeWH1);
        g.fillOval(eyeX3, eyeY1, eyeWH1, eyeWH1);
        g.fillArc(arcX, arcY, arcW, arcH, 0, -180);
        g.setColor(Color.WHITE);
        g.fillOval(eyeX4, eyeY2, eyeWH2, eyeWH2);
        g.fillOval(eyeX5, eyeY2, eyeWH2, eyeWH2);
        g2.fillRoundRect(tX, tY, tW, tH, 15, 15);
        g2.fillRoundRect(tX1, tY, tW, tH, 15, 15);
    }

  
    public void drawHeart(Graphics g, int x1, int x2, int x3, int y1, int y2, int y3, int y4) {
        Color one = new Color(0, 0, 0);
        Color two = new Color(0, 0, 0);
        Color three = new Color(0, 0, 0);
        Color four = new Color(0, 0, 0);
        if(direction == 0){
            one = new Color(153, 255, 255);
            two = new Color(51, 255, 255);
            three = new Color(0, 255, 255);
            four = new Color(102, 255, 255);
        } else if(direction == 1){
            one = new Color(51, 255, 255);
            two = new Color(0, 204, 204);
            three = new Color(0, 153, 153);
            four = new Color(0, 255, 255);
        } else if(direction == 2){
            one = new Color(0, 204, 204);
            two = new Color(51, 255, 255);
            three = new Color(0, 255, 255);
            four = new Color(0, 153, 153);
        } else if(direction == 3){
            one = new Color(51, 255, 255);
            two = new Color(153, 255, 255);
            three = new Color(102, 255, 255);
            four = new Color(0, 255, 255);
        }
        int[] xleft = {x2, x2, x1};
        int[] yup = {y1, y3, y2};
        int[] xright = {x2, x2, x3};
        int[] ydown = {y4, y3, y2};

        g.setColor(one);
        g.fillPolygon(xright, yup, 3);

        g.setColor(two);
        g.fillPolygon(xleft, yup, 3);

        g.setColor(three);
        g.fillPolygon(xleft, ydown, 3);

        g.setColor(four);
        g.fillPolygon(xright, ydown, 3);

    }
    
  /**
    *Method that draws compass in bottom left corner.
    * @param g object Graphics g
    */
    public void compass(Graphics g) {
        
        double v = 0;
        double u = 0;
        if (xPosition != mazeGrid.length-1 || yPosition != mazeGrid.length-1){
            double hypotenuse;
            if (direction == 1 || direction == 2){
                hypotenuse = (double) (mazeGrid.length-1 - xPosition) / (double) (mazeGrid.length-1 - yPosition);
            } else {
                hypotenuse = (double) (mazeGrid.length-1 - yPosition) / (double) (mazeGrid.length-1 - xPosition);
            }
            double theta = Math.atan(hypotenuse);
            if(direction == 1 || direction == 0){
                v = 50 * Math.cos(theta);
            } else {
                v = 50 * Math.cos(theta+Math.PI/2);
            }
            if (direction == 1 || direction == 2){
                u = -Math.sqrt((1-((v*v)/(50*50)))*30*30);
            } else {
                u = Math.sqrt((1-((v*v)/(50*50)))*30*30);
            }
        }


        Color orange = new Color(217, 135, 5);
        Color grey = new Color(110, 108, 100);
        Color white = new Color(230, 218, 197);

        g.setColor(Color.BLACK);
        g.fillOval(540, 570, 131, 106);

        g.setColor(orange);
        g.fillOval(540, 570, 132, 101);

        g.setColor(grey);
        g.fillOval(544, 570, 128, 88);

        g.setColor(white);
        g.fillOval(553, 575, 112, 67);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        g2.setColor(Color.BLACK);
        g2.draw(new Line2D.Double(609, 608.5, 609+v, 608.5+u));
    }

    private int EnemyorHealth(int Xuse, int Yuse){
        int ToReturn = 0;
        try{
            if (mazeGrid[Xuse][Yuse].enemy && mazeGrid[Xuse][Yuse].life > 0){
                ToReturn = -1;
            } else if(mazeGrid[Xuse][Yuse].heart && mazeGrid[Xuse][Yuse].life > 0){
                ToReturn = 1;
            }
        } catch(ArrayIndexOutOfBoundsException ignored){
        }
        return ToReturn;
    }

    private void walls(Graphics g) {
        if(displayWalls[0]){
            g.setColor(backWalls());
            g.fillRect(80, 260, 180, 180);
            if(xPosition == (mazeGrid.length-1) && yPosition == (mazeGrid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)xPosition + (double)yPosition)/(2*(double)mazeGrid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints = {80, 260, 200};
            int[] yPoints = {440, 440, 500};
            g.fillPolygon(xPoints, yPoints, 3);
        }
        if(farcells[0] == 1){
            drawHeart(g, 141, 170, 199, 350, 405, 416, 460);
        } else if (farcells[0] == -1){
            enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 145, 137, 164, 380,
                    310, 370, 189, 216, 208, 140, 350, 74, 110, 0, 0, 0, 0,
                    155, 375, 15, 185, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0);
        }
        if(displayWalls[1]){
            g.setColor(backWalls());
            g.fillRect(260, 260, 180, 180);
            if(xPosition == (mazeGrid.length-1) && yPosition == (mazeGrid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)xPosition + (double)yPosition)/(2*(double)mazeGrid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints = {260, 200, 500, 440};
            int[] yPoints = {440, 500, 500, 440};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(farcells[1] == 1){
            drawHeart(g, 321, 350, 379, 350, 405, 416, 460);
        } else if (farcells[1] == -1){
            enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 320, 312, 339, 430,
                    360, 420, 359, 386, 378, 320, 390, 60, 90, 0, 0, 0, 0,
                    332, 405, 15, 353, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0);
        }
        if(displayWalls[2]){
            g.setColor(backWalls());
            g.fillRect(440, 260, 180, 180);
            if(xPosition == (mazeGrid.length-1) && yPosition == (mazeGrid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)xPosition + (double)yPosition)/(2*(double)mazeGrid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints = {620, 440, 500};
            int[] yPoints = {440, 440, 500};
            g.fillPolygon(xPoints, yPoints, 3);

        }
        if(farcells[2] == 1){
            drawHeart(g, 501, 530, 559, 350, 405, 416, 460);
        } else if (farcells[2] == -1){
            enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 505, 497, 524,
                    380, 310, 370, 549, 576, 568, 500, 350, 74, 110, 0, 0, 0,
                    0, 515, 375, 15, 545, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0);
        }
        if(displayWalls[3]){
            g.setColor(leftWalls());
            int[] xPoints = {0, 0, 80, 80};
            int[] yPoints = {233, 467, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);
            if(xPosition == (mazeGrid.length-1) && yPosition == (mazeGrid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)xPosition + (double)yPosition)/(2*(double)mazeGrid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints2 = {80, 200, 0, 0};
            int[] yPoints2 = {440, 500, 500, 467};
            g.fillPolygon(xPoints2, yPoints2, 4);
        }
        if(displayWalls[4]){
            g.setColor(leftWalls());
            int[] xPoints = {200, 200, 260, 260};
            int[] yPoints = {200, 500, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        if(displayWalls[5]){
            g.setColor(rightWalls());
            int[] xPoints = {500, 500, 440, 440};
            int[] yPoints = {200, 500, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(displayWalls[6]){
            g.setColor(rightWalls());
            int[] xPoints = {700, 700, 620, 620};
            int[] yPoints = {233, 467, 440, 260};
            g.fillPolygon(xPoints, yPoints, 4);
            if(xPosition == (mazeGrid.length-1) && yPosition == (mazeGrid.length-1)){
                g.setColor(Color.GREEN);
            } else {
                double ground = 255-(255)*(((double)xPosition + (double)yPosition)/(2*(double)mazeGrid.length));
                g.setColor( new Color(255, (int)ground, (int)ground));
            }
            int[] xPoints2 = {620, 500, 700, 700};
            int[] yPoints2 = {440, 500, 500, 467};
            g.fillPolygon(xPoints2, yPoints2, 4);

        }
        if(displayWalls[7]){
            g.setColor(backWalls());
            g.fillRect(0, 200, 200, 300);

        }
        if(farcells[3] == 1){
            drawHeart(g, 3, 50, 97, 350, 438, 456, 525);
        } else if (farcells[3] == -1){
          enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 30, 80, 420,
                    340, 430, 70, 120, 110, 30, 360, 94, 160, 47, 382, 26, 82,
                    50, 385, 20, 85, 48, 420, 60, 35, 0, 0, 0, 0, 0, 0,
                    0, 0, 0);
        }
        if(displayWalls[8]){
            g.setColor(backWalls());
            g.fillRect(200, 200, 300, 300);


        }
        if(farcells[4] == 1){
            drawHeart(g, 303, 350, 397, 350, 438, 456, 525);
        } else if (farcells[4] == -1){
            enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 320, 310, 360, 420,
                    340, 430, 350, 406, 390, 310, 360, 94, 160, 327, 382, 26, 362,
                    330, 385, 20, 365, 328, 420, 60, 35, 0, 0, 0, 0, 0, 0,
                    0, 0, 0);
        }
        if(displayWalls[9]){
            g.setColor(backWalls());
            g.fillRect(500, 200, 200, 300);


        }
        if(farcells[5] == 1){
            drawHeart(g, 603, 650, 697, 350, 438, 456, 525);
        } else if (farcells[5] == -1){
             enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 610, 600, 650, 420,
                    340, 430, 650, 700, 690, 600, 360, 94, 160, 617, 382, 26, 652,
                    620, 385, 20, 655, 618, 420, 60, 35, 0, 0, 0, 0, 0, 0,
                    0, 0, 0);
        
        }
        if(displayWalls[10]){
            g.setColor(leftWalls());
            int[] xPoints = {135, 135, 200, 200};
            int[] yPoints = {135, 565, 500, 200};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        if(displayWalls[11]){
            g.setColor(rightWalls());
            int[] xPoints = {565, 565, 500, 500};
            int[] yPoints = {135, 565, 500, 200};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(displayWalls[12]){
            g.setColor(backWalls());
            g.fillRect(0, 135, 135, 430);

        }
        if(displayWalls[13]){
            g.setColor(backWalls());
            g.fillRect(135, 135, 430, 430);

        }
        if(farcells[6] == 1){
            drawHeart(g, 286, 350, 414, 350, 470, 494, 590);
        } else if (farcells[6] == -1){
            
            enemy(g,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 320, 305, 400, 470,
                    380, 480, 325, 420, 405, 305, 410, 120, 185, 332, 437, 26, 367,
                    335, 440, 20, 370, 325, 470, 75, 45, 0, 0, 0, 0, 345, 495,
                    18, 9, 362);
        }
        if(displayWalls[14]){
            g.setColor(backWalls());
            g.fillRect(565, 135, 135, 430);

        }
        if(displayWalls[15]){
            g.setColor(leftWalls());
            int[] xPoints = {65, 65, 135, 135};
            int[] yPoints = {65, 635, 565, 135};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(displayWalls[16]){
            g.setColor(rightWalls());
            int[] xPoints = {635, 635, 565, 565};
            int[] yPoints = {65, 635, 565, 135};
            g.fillPolygon(xPoints, yPoints, 4);

        }
        if(displayWalls[17]){
            g.setColor(backWalls());
            g.fillRect(0, 65, 65, 570);

        }
        if(displayWalls[18]){
            g.setColor(backWalls());
            g.fillRect(65, 65, 570, 570);
        }
        if(displayWalls[19]){
            g.setColor(backWalls());
            g.fillRect(635, 65, 65, 570);

        }
        if(displayWalls[20]){
            g.setColor(leftWalls());
            int[] xPoints = {0, 0, 65, 65};
            int[] yPoints = {0, 700, 635, 65};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        if(displayWalls[21]){
            g.setColor(rightWalls());
            int[] xPoints = {700, 700, 635, 635};
            int[] yPoints = {0, 700, 635, 65};
            g.fillPolygon(xPoints, yPoints, 4);
        }
    }
    private Color backWalls(){
        Color toReturn = new Color(0,0,0);
        Color darkest = new Color(0, 50, 150);
        Color dark = new Color(0, 70, 209);
        Color light = new Color(54, 121, 255);
        Color lightest = new Color(105, 155, 255);

        if(direction == 0){
            toReturn = lightest;
        } else if(direction == 1){
            toReturn = dark;
        } else if(direction == 2){
            toReturn = darkest;
        } else if(direction == 3){
            toReturn = light;
        }
        return toReturn;
    }
    private Color leftWalls(){
        Color toReturn = new Color(0,0,0);
        Color darkest = new Color(0, 50, 150);
        Color dark = new Color(0, 70, 209);
        Color light = new Color(54, 121, 255);
        Color lightest = new Color(105, 155, 255);

        if(direction == 0){
            toReturn = light;
        } else if(direction == 1){
            toReturn = lightest;
        } else if(direction == 2){
            toReturn = dark;
        } else if(direction == 3){
            toReturn = darkest;
        }
        return toReturn;

    }
    private Color rightWalls(){
        Color toReturn = new Color(0,0,0);
        Color darkest = new Color(0, 50, 150);
        Color dark = new Color(0, 70, 209);
        Color light = new Color(54, 121, 255);
        Color lightest = new Color(105, 155, 255);

        if(direction == 0){
            toReturn = dark;
        } else if(direction == 1){
            toReturn = darkest;
        } else if(direction == 2){
            toReturn = light;
        } else if(direction == 3){
            toReturn = lightest;
        }
        return toReturn;

    }


    private void mist(Graphics g) {
        for (int i = 0; i < 50000; i++){
            int x = (int) (Math.random()*(700+20)-10);
            int y = (int) (Math.random()*(700+20)-10);
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
        if(xPosition == (mazeGrid.length-1) && yPosition == (mazeGrid.length-1)){
            g.setColor(Color.GREEN);
        } else {
            double ground = 255-(255)*(((double)xPosition + (double)yPosition)/(2*(double)mazeGrid.length));
            g.setColor( new Color(255, (int)ground, (int)ground));
        }
        g.fillRect(0, 0, 700, 700);
        g.setColor(Color.BLACK);
        g.drawLine(0, 700, 260, 440);
        g.drawLine(700, 700, 440, 440);
        g.drawLine(0, 500, 700, 500);
        g.drawLine(0, 565, 700, 565);
        g.drawLine(0, 635, 700, 635);
    }


    public boolean[] whichWallsToDraw(){
        boolean[] toReturn = new boolean [22];
        if (direction == 0 ){
            toReturn = up(toReturn);
        } else if (direction == 1 ){
            toReturn = right(toReturn);
        } else
        if (direction == 2 ){
            toReturn = down(toReturn);
        } else
        if (direction == 3 ){
            toReturn = left(toReturn);
        }
        return toReturn;
    }

    private boolean[] left(boolean[] walls) {
        walls[0] = WallOnOff(3,xPosition-3, yPosition+1);
        farcells[0] = EnemyorHealth(xPosition-3, yPosition+1);
        walls[1] = WallOnOff(3,xPosition-3, yPosition);
        farcells[1] = EnemyorHealth(xPosition-3, yPosition);
        walls[2] = WallOnOff(3,xPosition-3, yPosition-1);
        farcells[2] = EnemyorHealth(xPosition-3, yPosition-1);
        walls[3] = WallOnOff(2,xPosition-3, yPosition+1);
        walls[4] = WallOnOff(2,xPosition-3, yPosition);
        walls[5] = WallOnOff(0,xPosition-3, yPosition);
        walls[6] = WallOnOff(0,xPosition-3, yPosition-1);
        walls[7] = WallOnOff(3,xPosition-2, yPosition+1);
        farcells[3] = EnemyorHealth(xPosition-2, yPosition+1);
        walls[8] = WallOnOff(3,xPosition-2, yPosition);
        farcells[4] = EnemyorHealth(xPosition-2, yPosition);
        walls[9] = WallOnOff(3,xPosition-2, yPosition-1);
        farcells[5] = EnemyorHealth(xPosition-2, yPosition-1);
        walls[10] = WallOnOff(2,xPosition-2, yPosition);
        walls[11] = WallOnOff(0,xPosition-2, yPosition);
        walls[12] = WallOnOff(3,xPosition-1, yPosition+1);
        walls[13] = WallOnOff(3,xPosition-1, yPosition);
        farcells[6] = EnemyorHealth(xPosition-1, yPosition);
        walls[14] = WallOnOff(3,xPosition-1, yPosition-1);
        walls[15] = WallOnOff(2,xPosition-1, yPosition);
        walls[16] = WallOnOff(0,xPosition-1, yPosition);
        walls[17] = WallOnOff(3,xPosition, yPosition+1);
        walls[18] = WallOnOff(3,xPosition, yPosition);
        walls[19] = WallOnOff(3,xPosition, yPosition-1);
        walls[20] = WallOnOff(2,xPosition, yPosition);
        walls[21] = WallOnOff(0,xPosition, yPosition);
        return walls;
    }

    private boolean[] down(boolean[] walls) {
        walls[0] = WallOnOff(2, xPosition+1, yPosition+3);
        farcells[0] = EnemyorHealth(xPosition+1, yPosition+3);
        walls[1] = WallOnOff(2, xPosition, yPosition+3);
        farcells[1] = EnemyorHealth(xPosition, yPosition+3);
        walls[2] = WallOnOff(2, xPosition-1, yPosition+3);
        farcells[2] = EnemyorHealth(xPosition-1, yPosition+3);
        walls[3] = WallOnOff(1, xPosition+1, yPosition+3);
        walls[4] = WallOnOff(1, xPosition, yPosition+3);
        walls[5] = WallOnOff(3, xPosition, yPosition+3);
        walls[6] = WallOnOff(3, xPosition-1, yPosition+3);
        walls[7] = WallOnOff(2, xPosition+1, yPosition+2);
        farcells[3] = EnemyorHealth(xPosition+1, yPosition+2);
        walls[8] = WallOnOff(2, xPosition, yPosition+2);
        farcells[4] = EnemyorHealth(xPosition, yPosition+2);
        walls[9] = WallOnOff(2, xPosition-1, yPosition+2);
        farcells[5] = EnemyorHealth(xPosition-1, yPosition+2);
        walls[10] = WallOnOff(1, xPosition, yPosition+2);
        walls[11] = WallOnOff(3, xPosition, yPosition+2);
        walls[12] = WallOnOff(2, xPosition+1, yPosition+1);
        walls[13] = WallOnOff(2, xPosition, yPosition+1);
        farcells[6] = EnemyorHealth(xPosition, yPosition+1);
        walls[14] = WallOnOff(2, xPosition-1, yPosition+1);
        walls[15] = WallOnOff(1, xPosition, yPosition+1);
        walls[16] = WallOnOff(3, xPosition, yPosition+1);
        walls[17] = WallOnOff(2, xPosition+1, yPosition);
        walls[18] = WallOnOff(2, xPosition, yPosition);
        walls[19] = WallOnOff(2, xPosition-1, yPosition);
        walls[20] = WallOnOff(1, xPosition, yPosition);
        walls[21] = WallOnOff(3, xPosition, yPosition);
        return walls;
    }

    private boolean[] right(boolean[] walls) {
        walls[0] = WallOnOff(1,xPosition+3, yPosition-1);
        farcells[0] = EnemyorHealth(xPosition+3, yPosition-1);
        walls[1] = WallOnOff(1,xPosition+3, yPosition);
        farcells[1] = EnemyorHealth(xPosition+3, yPosition);
        walls[2] = WallOnOff(1,xPosition+3, yPosition+1);
        farcells[2] = EnemyorHealth(xPosition+3, yPosition+1);
        walls[3] = WallOnOff(0,xPosition+3, yPosition-1);
        walls[4] = WallOnOff(0,xPosition+3, yPosition);
        walls[5] = WallOnOff(2,xPosition+3, yPosition);
        walls[6] = WallOnOff(2,xPosition+3, yPosition+1);
        walls[7] = WallOnOff(1,xPosition+2, yPosition-1);
        farcells[3] = EnemyorHealth(xPosition+2, yPosition-1);
        walls[8] = WallOnOff(1,xPosition+2, yPosition);
        farcells[4] = EnemyorHealth(xPosition+2, yPosition);
        walls[9] = WallOnOff(1,xPosition+2, yPosition+1);
        farcells[5] = EnemyorHealth(xPosition+2, yPosition+1);
        walls[10] = WallOnOff(0,xPosition+2, yPosition);
        walls[11] = WallOnOff(2,xPosition+2, yPosition);
        walls[12] = WallOnOff(1,xPosition+1, yPosition-1);
        walls[13] = WallOnOff(1,xPosition+1, yPosition);
        farcells[6] = EnemyorHealth(xPosition+1, yPosition);
        walls[14] = WallOnOff(1,xPosition+1, yPosition+1);
        walls[15] = WallOnOff(0,xPosition+1, yPosition);
        walls[16] = WallOnOff(2,xPosition+1, yPosition);
        walls[17] = WallOnOff(1,xPosition, yPosition-1);
        walls[18] = WallOnOff(1,xPosition, yPosition);
        walls[19] = WallOnOff(1,xPosition, yPosition+1);
        walls[20] = WallOnOff(0,xPosition, yPosition);
        walls[21] = WallOnOff(2,xPosition, yPosition);
        return walls;
    }

    private boolean[] up(boolean[] walls) {
        walls[0] = WallOnOff(0, xPosition-1, yPosition-3);
        farcells[0] = EnemyorHealth(xPosition-1, yPosition-3);
        walls[1] = WallOnOff(0, xPosition, yPosition-3);
        farcells[1] = EnemyorHealth(xPosition, yPosition-3);
        walls[2] = WallOnOff(0, xPosition+1, yPosition-3);
        farcells[2] = EnemyorHealth(xPosition+1, yPosition-3);
        walls[3] = WallOnOff(3, xPosition-1, yPosition-3);
        walls[4] = WallOnOff(3, xPosition, yPosition-3);
        walls[5] = WallOnOff(1, xPosition, yPosition-3);
        walls[6] = WallOnOff(1, xPosition+1, yPosition-3);
        walls[7] = WallOnOff(0, xPosition-1, yPosition-2);
        farcells[3] = EnemyorHealth(xPosition-1, yPosition-2);
        walls[8] = WallOnOff(0, xPosition, yPosition-2);
        farcells[4] = EnemyorHealth(xPosition, yPosition-2);
        walls[9] = WallOnOff(0, xPosition+1, yPosition-2);
        farcells[5] = EnemyorHealth(xPosition+1, yPosition-2);
        walls[10] = WallOnOff(3, xPosition, yPosition-2);
        walls[11] = WallOnOff(1, xPosition, yPosition-2);
        walls[12] = WallOnOff(0, xPosition-1, yPosition-1);
        walls[13] = WallOnOff(0, xPosition, yPosition-1);
        farcells[6] = EnemyorHealth(xPosition, yPosition-1);
        walls[14] = WallOnOff(0, xPosition+1, yPosition-1);
        walls[15] = WallOnOff(3, xPosition, yPosition-1);
        walls[16] = WallOnOff(1, xPosition, yPosition-1);
        walls[17] = WallOnOff(0, xPosition-1, yPosition);
        walls[18] = WallOnOff(0, xPosition, yPosition);
        walls[19] = WallOnOff(0, xPosition+1, yPosition);
        walls[20] = WallOnOff(3, xPosition, yPosition);
        walls[21] = WallOnOff(1, xPosition, yPosition);
        return walls;

    }

    private boolean WallOnOff(int wall, int Xuse, int Yuse){
        boolean ToReturn = false;
        try{
            if (wall == 0){
                ToReturn = mazeGrid[Xuse][Yuse].UpWall;
            } else if(wall == 1){
                ToReturn = mazeGrid[Xuse][Yuse].RightWall;
            } else if(wall == 2){
                ToReturn = mazeGrid[Xuse][Yuse].DownWall;
            } else if(wall == 3){
                ToReturn = mazeGrid[Xuse][Yuse].LeftWall;
            }
        } catch(ArrayIndexOutOfBoundsException ignored){
            ToReturn = false;
        }
        return ToReturn;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(life > 0){
            if(e.getKeyCode() == KeyEvent.VK_SPACE && mazeGrid[xPosition][yPosition].life > 0){
                mazeGrid[xPosition][yPosition].life--;
                repaint();
            }
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(life > 0){
            if(mazeGrid[xPosition][yPosition].life > 0 && mazeGrid[xPosition][yPosition].enemy){
                repaint();
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
                if(direction == 3 && !WallOnOff(0, xPosition, yPosition)){
                    yPosition--;
                } else if(direction == 0 && !WallOnOff(1, xPosition, yPosition)){
                    xPosition++;
                } else if(direction == 1 && !WallOnOff(2, xPosition, yPosition)){
                    yPosition++;
                } else if(direction == 2 && !WallOnOff(3, xPosition, yPosition)){
                    xPosition--;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
                if(direction == 1 && !WallOnOff(0, xPosition, yPosition)){
                    yPosition--;
                } else if(direction == 2 && !WallOnOff(1, xPosition, yPosition)){
                    xPosition++;
                } else if(direction == 3 && !WallOnOff(2, xPosition, yPosition)){
                    yPosition++;
                } else if(direction == 0 && !WallOnOff(3, xPosition, yPosition)){
                    xPosition--;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP ) {
                if(direction == 0 && !WallOnOff(0, xPosition, yPosition)){
                    yPosition--;
                } else if(direction == 1 && !WallOnOff(1, xPosition, yPosition)){
                    xPosition++;
                } else if(direction == 2 && !WallOnOff(2, xPosition, yPosition)){
                    yPosition++;
                } else if(direction == 3 && !WallOnOff(3, xPosition, yPosition)){
                    xPosition--;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                if(direction == 2 && !WallOnOff(0, xPosition, yPosition)){
                    yPosition--;
                } else if(direction == 3 && !WallOnOff(1, xPosition, yPosition)){
                    xPosition++;
                } else if(direction == 0 && !WallOnOff(2, xPosition, yPosition)){
                    yPosition++;
                } else if(direction == 1 && !WallOnOff(3, xPosition, yPosition)){
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
            if(xPosition == mazeGrid.length-1 && yPosition == mazeGrid.length-1){
                winner = true;
            }
        }

    }
}
