/**
 * The main class is the one that contains the main method, is run on the command line, and creates
 * all the necessary objects to start and play the game
 */

public class Main {
    public static void main(String[] args) {

        // Creates a new instance of the front page graphics (main menu) and displays it
        FrontPageGraphics frontPage = new FrontPageGraphics();
        frontPage.frame.setVisible(true);

        // Once the start button in the main menu is pressed, check is set to true and the code progresses
        while (!frontPage.check) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
            }
        }
        // Create a new maze with the parameters specified in the main menu
        Maze maze = new Maze(frontPage.dimensions, frontPage.key, frontPage.ifEnemies);
        maze.createMaze();
        // If the user wants a 2d or 3d maze
        if (frontPage.typeOfMaze == 2) {
            GraphicInterface2D twoD = new GraphicInterface2D(maze.mazeGrid, maze.difficulty);
            twoD.frame2D.setVisible(true);
        } else if (frontPage.typeOfMaze == 3) {
            // Code used from https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
            double startTime = System.currentTimeMillis();
            GraphicInterface3D threeD = new GraphicInterface3D(maze.mazeGrid, maze.difficulty);
            threeD.frame3D.setVisible(true);
            // Holds the code until the user finishes the maze
            while (!threeD.winner) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {

                }
            }
            // Record how much time it took the user to complete the maze and display the leaderboard
            double finishTime = System.currentTimeMillis();
            double time = finishTime - startTime;
            Leaderboard leaderboard = new Leaderboard(frontPage.dimensions, time);
//            while (!leaderboard.submitted) {
//                try {
//                    Thread.sleep(1000 / 60);
//                } catch (InterruptedException e) {
//                }
//            }
        }
    }
}
