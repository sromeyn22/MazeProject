public class Main {
    public static void main (String[] args){

        FrontPageGraphics frontPage = new FrontPageGraphics();
        frontPage.frame.setVisible(true);
        while(true){
            try{
                Thread.sleep(1000/60);
            } catch (InterruptedException e){

            }
            if(frontPage.check)
                break;
        }
        Maze maze = new Maze(frontPage.dimensions, frontPage.key);
        maze.createMaze();
        if (frontPage.typeOfMaze == 2){
            GraphicInterface2D twoD = new GraphicInterface2D(maze.mazeGrid, maze.difficulty);
            twoD.frame2D.setVisible(true);
        } else if (frontPage.typeOfMaze == 3){
            GraphicInterface3D threeD = new GraphicInterface3D(maze.mazeGrid, maze.difficulty);
            threeD.frame3D.setVisible(true);
        }

    }
}
