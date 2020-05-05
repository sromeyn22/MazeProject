/**
 * The maze class generates a random maze given the player's specifications from the main menu (dimensions
 * of the maze, a seed for the maze, and if they want enemies).
 */

import java.util.Random;

public class Maze {
    /**
     * A representation of the randomly generated maze as a 2D array of cells
     */
    Cell[][] mazeGrid;
    /**
     * A measure of the difficulty of the maze (the length of the path from the start until the end)
     */
    int difficulty;
    /**
     * A seed for the maze specified by the player (if there is no seed specified, it will be randomly generated)
     */
    int key;
    /**
     * If the player wants enemies in the maze
     */
    boolean ifEnemies;
    /**
     * A Random object to help generate the maze
     */
    static Random rand;
    /**
     * The number of dimensions in the maze
     */
    static int dimensions;
    /**
     * The x position of the maze generator
     */
    static int xPos;
    /**
     * The y position of the maze generator
     */
    static int yPos;
    /**
     * The random starting location of the maze generator
     */
    static int xStart;
    /**
     * The random y coordinate of the starting location of the maze generator
     */
    static int yStart;
    /**
     *
     */
    static BacktrackerDS ds = new BacktrackerDS();


    /**
     * This constructor initializes the maze given the parameters the player specified
     * @param _dimensions the dimensions of the maze
     * @param _key the seed for the maze
     * @param ifEnemies if the player wants enemies in the maze
     */
    public Maze (int _dimensions, int _key, boolean ifEnemies){
        dimensions = _dimensions;
        this.ifEnemies = ifEnemies;
        mazeGrid = new Cell[dimensions][dimensions];
        // Iterate through each cell in the mazeGrid and initialize each one
        for (int column = 0; column < dimensions; column++) {
            for (int row = 0; row < dimensions; row++) {
                mazeGrid[column][row] = new Cell();
            }
        }
        key = _key;
    }

    /**
     * This method randomly generates the maze
     */
    public void createMaze() {
        rand = new Random(key);
        xStart = rand.nextInt(dimensions);
        yStart = rand.nextInt(dimensions);
        xPos = xStart;
        yPos = yStart;
        while (true){
            //ui.triggerMove();
            if (mazeGrid[xPos][yPos].UnVisited){
                mazeGrid[xPos][yPos].UnVisited = false;
                ds.append(mazeGrid[xPos][yPos].UnVisited, xPos, yPos);
            }

            ChooseNeighbor();

            if(xPos == xStart && yPos ==yStart){
                xPos = 0;
                yPos = 0;
                break;
            }
        }
        for (int column = 0; column < mazeGrid.length; column += 1) {
            for (int row = 0; row < mazeGrid.length; row += 1) {
                mazeGrid[column][row].UnVisited = true;
            }
        }
        while (xPos != mazeGrid.length-1 || yPos != mazeGrid.length-1){
            //ui.triggerMove();
            if (mazeGrid[xPos][yPos].UnVisited){
                mazeGrid[xPos][yPos].UnVisited = false;
                ds.append(mazeGrid[xPos][yPos].UnVisited, xPos, yPos);
            }

            ChooseNeighborSolution();
        }
        xPos = ds.returnx(xPos);
        yPos = ds.returny(yPos);
        //difficulty = ds.getLength();
        while(xPos != 0 || yPos != 0){
            mazeGrid[xPos][yPos].Path = true;
            ds.pop();
            xPos = ds.returnx(xPos);
            yPos = ds.returny(yPos);
        }
        if(ifEnemies) {
            for (int i = 0; i < mazeGrid.length; i++) {
                int w = rand.nextInt(dimensions);
                int z = rand.nextInt(dimensions);
                if(!(w == 0 && z == 0) && !(w == dimensions-1 && z == dimensions-1)) {
                    mazeGrid[w][z].enemy = true;
                    mazeGrid[w][z].life = rand.nextInt(5) + 5;
                }
            }
            for (int i = 0; i < 2 * mazeGrid.length; i++) {
                int w = rand.nextInt(dimensions);
                int z = rand.nextInt(dimensions);
                if(!(w == 0 && z == 0) && !(w == dimensions-1 && z == dimensions-1)) {
                    if (!mazeGrid[w][z].enemy) {
                        mazeGrid[w][z].heart = true;
                        mazeGrid[w][z].life = rand.nextInt(5) + 5;
                    }
                }
            }
        }
//         for (int i = 0; i < mazeGrid.length; i++){
//             int w = rand.nextInt(dimensions);
//             int z = rand.nextInt(dimensions);
//             int y = rand.nextInt(dimensions);
//             int p = rand.nextInt(dimensions);

//             while (w == y && y == p) {
//                  w = rand.nextInt(dimensions);
//                  z = rand.nextInt(dimensions);
//                  y = rand.nextInt(dimensions);
//                  p = rand.nextInt(dimensions);

//             }
//             mazeGrid[w][z].enemy = true;
//             mazeGrid[w][z].life = rand.nextInt(5) + 5;
//             mazeGrid[y][p].heart = true;
//             mazeGrid[y][p].life = rand.nextInt(5) + 5;
//         }
    }


    
    private void ChooseNeighborSolution() {
        if(xPos == mazeGrid.length-1 && yPos == mazeGrid.length-1){
            return;
        }
        boolean[] arrNeighborssol = getNeighborsSolution();
        if (!arrNeighborssol[0] && !arrNeighborssol[1] && !arrNeighborssol[2] && !arrNeighborssol[3]){
            ds.pop();
            xPos = ds.returnx(xPos);
            yPos = ds.returny(yPos);
            return;
        }
        boolean test = false;
        int random = 4;
        while (!test) {
            random = rand.nextInt(4);
            test = arrNeighborssol[random];
        }


        if(random == 0){
            yPos--;
        } else if (random == 1){
            xPos++;
        } else if (random == 2){
            yPos++;
        } else if (random == 3){
            xPos--;
        }

    }

    private boolean[] getNeighborsSolution() {
        boolean a;
        boolean b;
        boolean c;
        boolean d;

        if (yPos == 0){
            a = false;
        } else {
            a = !mazeGrid[xPos][yPos].UpWall && mazeGrid[xPos][yPos-1].UnVisited;
        }

        if (xPos == mazeGrid.length-1){
            b = false;
        } else {
            b = !mazeGrid[xPos][yPos].RightWall && mazeGrid[xPos+1][yPos].UnVisited;
        }

        if (yPos == mazeGrid.length-1){
            c = false;
        } else {
            c = !mazeGrid[xPos][yPos].DownWall && mazeGrid[xPos][yPos+1].UnVisited;
        }

        if (xPos == 0){
            d = false;
        } else {
            d = !mazeGrid[xPos][yPos].LeftWall && mazeGrid[xPos-1][yPos].UnVisited;
        }

        return new boolean[]{a, b, c, d};
    }

    public void ChooseNeighbor(){

        boolean[] arrNeighbors = getNeighbors();
        if (!arrNeighbors[0] && !arrNeighbors[1] && !arrNeighbors[2] && !arrNeighbors[3]){
            ds.pop();
            xPos = ds.returnx(xPos);
            yPos = ds.returny(yPos);
            if(xPos == xStart && yPos ==yStart){
                ds.pop();
            }
            return;
        }

        boolean test = false;
        int random = 4;
        while (!test) {
            random = rand.nextInt(4);
            test = arrNeighbors[random];
        }
        if(random == 0){
            mazeGrid[xPos][yPos].UpWall = false;
            yPos--;
            mazeGrid[xPos][yPos].DownWall = false;
        } else if (random == 1){
            mazeGrid[xPos][yPos].RightWall = false;
            xPos++;
            mazeGrid[xPos][yPos].LeftWall = false;
        } else if (random == 2){
            mazeGrid[xPos][yPos].DownWall = false;
            yPos++;
            mazeGrid[xPos][yPos].UpWall = false;
        } else if (random == 3){
            mazeGrid[xPos][yPos].LeftWall = false;
            xPos--;
            mazeGrid[xPos][yPos].RightWall = false;
        }

    }

    public boolean[] getNeighbors(){
        boolean a;
        boolean b;
        boolean c;
        boolean d;

        if (yPos == 0){
            a = false;
        } else {
            a = mazeGrid[xPos][yPos-1].UnVisited;
        }

        if (xPos == mazeGrid.length-1){
            b = false;
        } else {
            b = mazeGrid[xPos+1][yPos].UnVisited;
        }

        if (yPos == mazeGrid.length-1){
            c = false;
        } else {
            c = mazeGrid[xPos][yPos+1].UnVisited;
        }

        if (xPos == 0){
            d = false;
        } else {
            d = mazeGrid[xPos-1][yPos].UnVisited;
        }
        return new boolean[]{a, b, c, d};
    }
}
