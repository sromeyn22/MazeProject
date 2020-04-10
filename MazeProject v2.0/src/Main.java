import javax.swing.*;
import java.rmi.UnexpectedException;

public class Main {
    public static void main (String[] args){

        FrontPageGraphics frontPage = new FrontPageGraphics();
        frontPage.frame.setVisible(true);
        while(true){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){

            }
            if(frontPage.check)
                break;
        }
        Maze maze = new Maze(frontPage.dimensions, frontPage.key);
        maze.createMaze();
        if (frontPage.typeOfMaze == 2){
            GraphicInterface2D twoD = new GraphicInterface2D(maze.mazeGrid);
        } else if (frontPage.typeOfMaze == 3){
            GraphicInterface3D threeD = new GraphicInterface3D(maze.mazeGrid);
        }

    }
}
