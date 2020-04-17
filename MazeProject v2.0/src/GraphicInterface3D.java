import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import java.util.Arrays;

public class GraphicInterface3D extends JPanel implements KeyListener {
    Cell[][] mazeGrid;
    int difficulty;
    int direction; // 0 for north, 1 for east, 2 for south, 3 for west
    int xPosition;
    int yPosition;
    boolean[] displayWalls = new boolean[22];
    JFrame frame3D;
    int life;
    int[] farcells = new int[7];
    boolean winner;


    public GraphicInterface3D(Cell[][] grid, int _difficulty){
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
        frame3D.setContentPane(this);
    }

    public void paintComponent(Graphics g){
        displayWalls = whichWallsToDraw();
        floor(g);
        mist(g);
        walls(g);
        enemy(g);
        compass(g);
        if(mazeGrid[xPosition][yPosition].heart && mazeGrid[xPosition][yPosition].life > 0) {
            drawHeart(g, 270, 350, 430, 350, 500, 530, 650);
            g.setColor(Color.GREEN);
            g.fillRect(280, 180, 18 * mazeGrid[xPosition][yPosition].life, 10);
        }
        g.setColor(Color.RED);
        g.drawString(Integer.toString(difficulty), 640, 25);
        g.fillRect(50+life, 650, 100-life, 10);
        g.setColor(Color.GREEN);
        g.fillRect(50, 650, life, 10);
        if(mazeGrid[xPosition][yPosition].enemy && mazeGrid[xPosition][yPosition].life > 0 && life > 0){
            life = life - 5;
            try{
                Thread.sleep(250);
            } catch (InterruptedException e){

            }
            repaint();
        }
        if(mazeGrid[xPosition][yPosition].heart && mazeGrid[xPosition][yPosition].life > 0 && life > 0){

            if (life < 100) { life = life + 5; }
        }
        if(life == 0){
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.drawString("YOU LOSE", 260, 200);
        }
    }

    public void enemy(Graphics g){
        if(mazeGrid[xPosition][yPosition].enemy && mazeGrid[xPosition][yPosition].life > 0){
            Graphics2D g2 = (Graphics2D) g;

            g.setColor(Color.BLACK);
            g.fillOval(240, 240, 220, 380);
            int[] xPoints3 = {267, 346, 236}; //left, right, top
            int[] yPoints3 = {378, 345, 172};
            g2.fillPolygon(xPoints3, yPoints3, 3);
            int[] xPoints2 = {354, 433, 464};
            int[] yPoints2 = {345, 355, 172};
            g2.fillPolygon(xPoints2, yPoints2, 3);

            Color ears = new Color(222, 71, 141);
            g2.setColor(ears);
            int[] xPoints = {275, 330, 240};
            int[] yPoints = {350, 350, 180};
            g2.fillPolygon(xPoints, yPoints, 3);
            int[] xPoints1 = {370, 425, 460};
            int[] yPoints1 = {350, 350, 180};
            g2.fillPolygon(xPoints1, yPoints1, 3);

            Color body = new Color(227, 126, 5);
            g.setColor(body);
            g.fillOval(250, 250, 200, 360);

            g.setColor(Color.WHITE);
            g.fillOval(280, 310, 50, 50);
            g.fillOval(372, 310, 50, 50);
            g.setColor(Color.BLACK);
            g.fillOval(289, 319, 32, 32);
            g.fillOval(380, 319, 32, 32);
            g.fillArc(275, 380, 150, 75, 0, -180);
            g.setColor(Color.WHITE);
            g.fillOval(310, 317, 13, 13);
            g.fillOval(400, 317, 13, 13);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(315, 422, 25, 20, 15, 15);
            g2.fillRoundRect(360, 422, 25, 20, 15, 15);

            g.setColor(Color.RED);
            g.fillRect(260 + 18 * mazeGrid[xPosition][yPosition].life, 180, 180 - 18 * mazeGrid[xPosition][yPosition].life, 10);
            g.setColor(Color.GREEN);
            g.fillRect(260, 180, 18*mazeGrid[xPosition][yPosition].life, 10);
        }
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

    public void compass(Graphics g) {

        double hypotenuse = (double) (mazeGrid.length - yPosition) / (mazeGrid.length - xPosition);
        double theta = Math.atan(hypotenuse);
        double v = 50 * Math.cos(theta) + 609; //x
        double u = 30 * Math.sin(theta) + 608.5;

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

        if (direction == 0) {
            g2.draw(new Line2D.Double(609, 608.5, v, u));

        } else if (direction == 1) {
            g2.draw(new Line2D.Double(609, 608.5, v, u - (2 * (u - 608.5))));

        } else if (direction == 2) {
            g2.draw(new Line2D.Double(609, 608.5, v - (2 * (v - 609)), u - (2 * (u - 608.5))));

        } else if (direction == 3) {
            g2.draw(new Line2D.Double(609, 608.5, v - (2 * (v - 609)), u));

        }
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
            //draw enemy
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
            //draw enemy
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
            //draw enemy
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
            //draw enemy
        }
        if(displayWalls[8]){
            g.setColor(backWalls());
            g.fillRect(200, 200, 300, 300);


        }
        if(farcells[4] == 1){
            drawHeart(g, 303, 350, 397, 350, 438, 456, 525);
        } else if (farcells[4] == -1){
            //draw enemy
        }
        if(displayWalls[9]){
            g.setColor(backWalls());
            g.fillRect(500, 200, 200, 300);


        }
        if(farcells[5] == 1){
            drawHeart(g, 603, 650, 697, 350, 438, 456, 525);
        } else if (farcells[5] == -1){
            //draw enemy
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
            //draw enemy
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
