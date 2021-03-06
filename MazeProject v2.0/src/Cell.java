/**
 * The cell class represents one square in the maze and contains information about what kind of entity the cell contains
 * and what walls surround it.
 */

class Cell{
    /**
     * If the cell has been visited while making the maze
     */
    boolean UnVisited;
    /**
     * If the cell is part of the path to the solution
     */
    boolean Path;
    /**
     * If the cell has a wall above it
     */
    boolean UpWall;
    /**
     * If the cell has a wall to its right
     */
    boolean RightWall;
    /**
     * If the cell has a wall below it
     */
    boolean DownWall;
    /**
     * If the cell has a wall to its left
     */
    boolean LeftWall;
    /**
     * If the cell has an enemy in it
     */
    boolean enemy;
    /**
     * If the cell has a heart in it
     */
    boolean heart;
    /**
     * how much life the heart or enemy has
     */
    int life;

    public Cell(){
        // initialize the fields to their default value
        Path = false;
        UnVisited = true;
        UpWall = true;
        RightWall = true;
        DownWall = true;
        LeftWall = true;
        enemy = false;
        heart = false;
        life = 0;
    }
}
