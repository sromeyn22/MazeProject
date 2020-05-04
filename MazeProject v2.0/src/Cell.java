/**
 * The cell class represents one square in the maze and contains information about what kind of entity the cell contains
 * and what walls surround it.
 */

class Cell{
    /**
     *
     */
    boolean UnVisited;
    /**
     *
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
     *
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
