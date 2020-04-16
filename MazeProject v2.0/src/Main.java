public class Main {
    public static void main(String[] args) {
        FrontPageGraphics frontPage = new FrontPageGraphics();
        frontPage.frame.setVisible(true);

        while (!frontPage.check) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
            }
        }
        Maze maze = new Maze(frontPage.dimensions, frontPage.key, frontPage.ifEnemies);
        maze.createMaze();
        if (frontPage.typeOfMaze == 2) {
            GraphicInterface2D twoD = new GraphicInterface2D(maze.mazeGrid, maze.difficulty);
            twoD.frame2D.setVisible(true);
        } else if (frontPage.typeOfMaze == 3) {
            // https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
            double startTime = System.currentTimeMillis();
            GraphicInterface3D threeD = new GraphicInterface3D(maze.mazeGrid, maze.difficulty);
            threeD.frame3D.setVisible(true);
            while (!threeD.winner) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {

                }
            }
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
