import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    static int x;
    static int y;
    static int PathCount;
    static Cells[][] grid = new Cells[20][20];
    static Commands commands = new Commands();
    static boolean[] drawingwalls = new boolean[22];
    static JFrame frame = new JFrame("Maze");

    static UserInterface ui;

    public static void main(String[] args){
        if(commands.type == 0 || commands.type == 1){
        } else{
            commands.type = question("What type of maze would you like? (type 0 for 2d and 1 for 3d)");
        }
        ui =  new GraphicInterface(grid, commands, drawingwalls, frame);
        for (int column = 0; column < grid.length; column += 1) {
            for (int row = 0; row < grid.length; row += 1) {
                grid[column][row] = new Cells();
            }
        }
        PathDS ds = new PathDS();
        if(commands.type == 0){
            Run(ds, ui);
            ui.display();
            System.out.println("If you want to see the solution press 1");
            commands.direction = 0;
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    System.out.println(e.getKeyCode());
                    KEYBOI(e);
                    if(x==grid.length-1 && y==grid.length-1){
                        System.out.println("YOU WON!!!!!!");
                    }

                }

                @Override
                public void keyReleased(KeyEvent e) {


                }
            });


        } else if (commands.type == 1){
            Run(ds, ui);
            if(commands.direction == 0){
                drawingwalls = up(drawingwalls);
            } else if(commands.direction == 1){
                drawingwalls = right(drawingwalls);
            } else if(commands.direction == 2){
                drawingwalls = down(drawingwalls);
            } else if(commands.direction == 3){
                drawingwalls = left(drawingwalls);
            }
            ui.display();
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {


                }

                @Override
                public void keyReleased(KeyEvent e) {
                    System.out.println(e.getKeyCode());
                    KEYBOI(e);
                    if(x==grid.length-1 && y==grid.length-1){
                        System.out.println("YOU WON!!!!!!");
                    }

                }
            });

        }
    }

    public static void KEYBOI(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
            moveRight();

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_UP ) {
            moveForward();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            moveBack();
        }else if (e.getKeyCode() == KeyEvent.VK_A && commands.type == 1) {
            turnLeft();
        }else if (e.getKeyCode() == KeyEvent.VK_D  && commands.type == 1) {
            turnRight();
        } else if (e.getKeyCode() == KeyEvent.VK_1  && commands.type == 0) {
            commands.solution = !commands.solution;
        }
        if(commands.direction == 0){
            drawingwalls = up(drawingwalls);
        } else if(commands.direction == 1){
            drawingwalls = right(drawingwalls);
        } else if(commands.direction == 2){
            drawingwalls = down(drawingwalls);
        } else if(commands.direction == 3){
            drawingwalls = left(drawingwalls);
        }
        commands.xPosition = x;
        commands.yPosition = y;
        ui.display();
        System.out.println("x = " + x + ", y = " + y);


    }

    private static boolean[] left(boolean[] walls) {
        walls[0] = WallOnOff(3,x-3, y+1);
        walls[1] = WallOnOff(3,x-3, y);
        walls[2] = WallOnOff(3,x-3, y-1);
        walls[3] = WallOnOff(2,x-3, y+1);
        walls[4] = WallOnOff(2,x-3, y);
        walls[5] = WallOnOff(0,x-3, y);
        walls[6] = WallOnOff(0,x-3, y-1);
        walls[7] = WallOnOff(3,x-2, y+1);
        walls[8] = WallOnOff(3,x-2, y);
        walls[9] = WallOnOff(3,x-2, y-1);
        walls[10] = WallOnOff(2,x-2, y);
        walls[11] = WallOnOff(0,x-2, y);
        walls[12] = WallOnOff(3,x-1, y+1);
        walls[13] = WallOnOff(3,x-1, y);
        walls[14] = WallOnOff(3,x-1, y-1);
        walls[15] = WallOnOff(2,x-1, y);
        walls[16] = WallOnOff(0,x-1, y);
        walls[17] = WallOnOff(3,x, y+1);
        walls[18] = WallOnOff(3,x, y);
        walls[19] = WallOnOff(3,x, y-1);
        walls[20] = WallOnOff(2,x, y);
        walls[21] = WallOnOff(0,x, y);
        return walls;
    }

    private static boolean[] down(boolean[] walls) {
        walls[0] = WallOnOff(2, x+1, y+3);
        walls[1] = WallOnOff(2, x, y+3);
        walls[2] = WallOnOff(2, x-1, y+3);
        walls[3] = WallOnOff(1, x+1, y+3);
        walls[4] = WallOnOff(1, x, y+3);
        walls[5] = WallOnOff(3, x, y+3);
        walls[6] = WallOnOff(3, x-1, y+3);
        walls[7] = WallOnOff(2, x+1, y+2);
        walls[8] = WallOnOff(2, x, y+2);
        walls[9] = WallOnOff(2, x-1, y+2);
        walls[10] = WallOnOff(1, x, y+2);
        walls[11] = WallOnOff(3, x, y+2);
        walls[12] = WallOnOff(2, x+1, y+1);
        walls[13] = WallOnOff(2, x, y+1);
        walls[14] = WallOnOff(2, x-1, y+1);
        walls[15] = WallOnOff(1, x, y+1);
        walls[16] = WallOnOff(3, x, y+1);
        walls[17] = WallOnOff(2, x+1, y);
        walls[18] = WallOnOff(2, x, y);
        walls[19] = WallOnOff(2, x-1, y);
        walls[20] = WallOnOff(1, x, y);
        walls[21] = WallOnOff(3, x, y);
        return walls;
    }

    private static boolean[] right(boolean[] walls) {
        walls[0] = WallOnOff(1,x+3, y-1);
        walls[1] = WallOnOff(1,x+3, y);
        walls[2] = WallOnOff(1,x+3, y+1);
        walls[3] = WallOnOff(0,x+3, y-1);
        walls[4] = WallOnOff(0,x+3, y);
        walls[5] = WallOnOff(2,x+3, y);
        walls[6] = WallOnOff(2,x+3, y+1);
        walls[7] = WallOnOff(1,x+2, y-1);
        walls[8] = WallOnOff(1,x+2, y);
        walls[9] = WallOnOff(1,x+2, y+1);
        walls[10] = WallOnOff(0,x+2, y);
        walls[11] = WallOnOff(2,x+2, y);
        walls[12] = WallOnOff(1,x+1, y-1);
        walls[13] = WallOnOff(1,x+1, y);
        walls[14] = WallOnOff(1,x+1, y+1);
        walls[15] = WallOnOff(0,x+1, y);
        walls[16] = WallOnOff(2,x+1, y);
        walls[17] = WallOnOff(1,x, y-1);
        walls[18] = WallOnOff(1,x, y);
        walls[19] = WallOnOff(1,x, y+1);
        walls[20] = WallOnOff(0,x, y);
        walls[21] = WallOnOff(2,x, y);
        return walls;
    }

    private static boolean[] up(boolean[] walls) {
        walls[0] = WallOnOff(0, x-1, y-3);
        walls[1] = WallOnOff(0, x, y-3);
        walls[2] = WallOnOff(0, x+1, y-3);
        walls[3] = WallOnOff(3, x-1, y-3);
        walls[4] = WallOnOff(3, x, y-3);
        walls[5] = WallOnOff(1, x, y-3);
        walls[6] = WallOnOff(1, x+1, y-3);
        walls[7] = WallOnOff(0, x-1, y-2);
        walls[8] = WallOnOff(0, x, y-2);
        walls[9] = WallOnOff(0, x+1, y-2);
        walls[10] = WallOnOff(3, x, y-2);
        walls[11] = WallOnOff(1, x, y-2);
        walls[12] = WallOnOff(0, x-1, y-1);
        walls[13] = WallOnOff(0, x, y-1);
        walls[14] = WallOnOff(0, x+1, y-1);
        walls[15] = WallOnOff(3, x, y-1);
        walls[16] = WallOnOff(1, x, y-1);
        walls[17] = WallOnOff(0, x-1, y);
        walls[18] = WallOnOff(0, x, y);
        walls[19] = WallOnOff(0, x+1, y);
        walls[20] = WallOnOff(3, x, y);
        walls[21] = WallOnOff(1, x, y);
        return walls;

    }

    private static boolean WallOnOff(int wall, int Xuse, int Yuse){
        boolean ToReturn = false;
        try{
            if (wall == 0){
                ToReturn = grid[Xuse][Yuse].UpWall;
            } else if(wall == 1){
                ToReturn = grid[Xuse][Yuse].RightWall;
            } else if(wall == 2){
                ToReturn = grid[Xuse][Yuse].DownWall;
            } else if(wall == 3){
                ToReturn = grid[Xuse][Yuse].LeftWall;
            }
        } catch(ArrayIndexOutOfBoundsException ignored){
            ToReturn = false;
        }
        return ToReturn;
    }

    public static int question(String Question) {
        java.util.Scanner scan = new java.util.Scanner(System.in);
        while (true) {
            try {
                System.out.print(Question);
                return scan.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Bad answer... try again.");
                scan.nextLine();     // to clear the rest of the line
            }
        }

    }

    private static void turnLeft(){
        if(commands.direction == 0){
            commands.direction = 3;
        } else{
            commands.direction--;
        }
    }
    private static void turnRight(){
            if(commands.direction == 3){
                commands.direction = 0;
            } else{
                commands.direction++;
            }
    }
    private static void moveForward() {
        if(commands.direction == 0 && y != 0 && !WallOnOff(0, x, y)){
            y--;
        } else if(commands.direction == 1 && x != grid.length-1 && !WallOnOff(1, x, y)){
            x++;
        } else if(commands.direction == 2 && y != grid.length-1 && !WallOnOff(2, x, y)){
            y++;
        } else if(commands.direction == 3 && x != 0 && !WallOnOff(3, x, y)){
            x--;
        }
    }
    private static void moveLeft() {
        if(commands.direction == 1 && y != 0 && !WallOnOff(0, x, y)){
            y--;
        } else if(commands.direction == 2 && x != grid.length-1 && !WallOnOff(1, x, y)){
            x++;
        } else if(commands.direction == 3 && y != grid.length-1 && !WallOnOff(2, x, y)){
            y++;
        } else if(commands.direction == 0 && x != 0 && !WallOnOff(3, x, y)){
            x--;
        }
    }
    private static void moveBack() {
        if(commands.direction == 2 && y != 0 && !WallOnOff(0, x, y)){
            y--;
        } else if(commands.direction == 3 && x != grid.length-1 && !WallOnOff(1, x, y)){
            x++;
        } else if(commands.direction == 0 && y != grid.length-1 && !WallOnOff(2, x, y)){
            y++;
        } else if(commands.direction == 1 && x != 0 && !WallOnOff(3, x, y)){
            x--;
        }
    }
    private static void moveRight() {
        if(commands.direction == 3 && y != 0 && !WallOnOff(0, x, y)){
            y--;
        } else if(commands.direction == 0 && x != grid.length-1 && !WallOnOff(1, x, y)){
            x++;
        } else if(commands.direction == 1 && y != grid.length-1 && !WallOnOff(2, x, y)){
            y++;
        } else if(commands.direction == 2 && x != 0 && !WallOnOff(3, x, y)){
            x--;
        }
    }


    public static void Run(OrderedCollection ds, UserInterface ui){

        int xStartPos = (int)(Math.random()*grid.length);
        int yStartPos = (int)(Math.random()*grid.length);
        x = xStartPos;
        y = yStartPos;
        PathCount = 0;

        while (true){
            if( x == 0 && y == 0){
                if((PathCount == 0 && grid[x][y].UnVisited) || PathCount < 0){
                    PathCount++;
                }
            } else if( x == grid.length-1 && y == grid.length-1){
                if((PathCount == 0 && grid[x][y].UnVisited) || PathCount > 0){
                    PathCount--;
                }
            }
            //ui.triggerMove();
            grid[x][y].UnVisited = false;
            ds.append(grid[x][y].UnVisited, x, y);
            if (PathCount != 0){
                grid[x][y].Path = !grid[x][y].Path;
            }
            ChooseNeighbor();
            while(x == -1 && y == -1) {
                ds.pop();
                x = ds.returnx(x);
                y = ds.returny(y);
                if(x == xStartPos && y ==yStartPos){
                    break;
                }
                ChooseNeighbor();
            }
            if(x == xStartPos && y ==yStartPos){
                ui.display();
                x = 0;
                y = 0;
                break;
            }
        }
    }

    public static void ChooseNeighbor(){
        boolean a;
        boolean b;
        boolean c;
        boolean d;
        if (y == 0){
            a = false;
        } else {
            a = grid[x][y-1].UnVisited;
        }
        if (x == grid.length-1){
            b = false;
        } else {
            b = grid[x+1][y].UnVisited;
        }
        if (y == grid.length-1){
            c = false;
        } else {
            c = grid[x][y+1].UnVisited;
        }
        if (x == 0){
            d = false;
        } else {
            d = grid[x-1][y].UnVisited;
        }
        if (!a && !b && !c && !d){
            if (PathCount != 0){
                grid[x][y].Path = !grid[x][y].Path;
            }
            x = -1;
            y = -1;
            return;
        }
        while (true){
            int rand = (int)(Math.random()*4);
            if(rand == 0 && a){
                grid[x][y].UpWall = false;
                y--;
                grid[x][y].DownWall = false;
                return;
            } else if (rand == 1 && b){
                grid[x][y].RightWall = false;
                x++;
                grid[x][y].LeftWall = false;
                return;
            } else if (rand == 2 && c){
                grid[x][y].DownWall = false;
                y++;
                grid[x][y].UpWall = false;
                return;
            } else if (rand == 3 && d){
                grid[x][y].LeftWall = false;
                x--;
                grid[x][y].RightWall = false;
                return;
            }
        }
    }
}

class Cells{
     boolean UnVisited;
     boolean Path;
     boolean UpWall;
     boolean RightWall;
     boolean DownWall;
     boolean LeftWall;

    public Cells(){
        Path = false;
        UnVisited = true;
        UpWall = true;
        RightWall = true;
        DownWall = true;
        LeftWall = true;
    }

}

class Commands{
    int type;
    boolean solution;
    int direction;
    int xPosition;
    int yPosition;

    public Commands(){
        direction = 2;
        type = 2;
        solution = false;
        xPosition = 0;
        xPosition = 0;
    }


}
