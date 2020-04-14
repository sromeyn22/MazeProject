import java.util.Random;

public class Maze {
    Cell[][] mazeGrid;
    int difficulty;
    static int key;
    static Random rand;
    static int dimensions;
    static int xPos;
    static int yPos;
    static int xStart;
    static int yStart;
    static BacktrackerDS ds = new BacktrackerDS();


    public Maze (int _dimensions, int _key){
        dimensions = _dimensions;
        mazeGrid = new Cell[dimensions][dimensions];
        for (int column = 0; column < dimensions; column++) {
            for (int row = 0; row < dimensions; row++) {
                mazeGrid[column][row] = new Cell();
            }
        }
        key = _key;
    }

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
        difficulty = ds.getLength();
        while(xPos != 0 || yPos != 0){
            mazeGrid[xPos][yPos].Path = true;
            ds.pop();
            xPos = ds.returnx(xPos);
            yPos = ds.returny(yPos);
        }
        for (int i = 0; i < mazeGrid.length; i++){
            int w = rand.nextInt(dimensions);
            int z = rand.nextInt(dimensions);
            mazeGrid[w][z].enemy = true;
            mazeGrid[w][z].life = rand.nextInt(5)+5;
        }
        for (int i = 0; i < 2*mazeGrid.length; i++){
            int w = rand.nextInt(dimensions);
            int z = rand.nextInt(dimensions);
            if(!mazeGrid[w][z].enemy){
                mazeGrid[w][z].health = true;
            }
        }
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
