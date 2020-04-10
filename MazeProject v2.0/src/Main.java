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
        System.out.println(frontPage.key);
        Maze maze = new Maze(frontPage.dimensions, frontPage.key);
        maze.createMaze();

    }
}
