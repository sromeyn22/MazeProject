/**
 * BacktrackerDS is a modified linked list that is used by the maze generator to keep track of which cells
 * it has already visited. If the maze generator runs into a dead end, it can use the backtrackerDS to backtrack
 * until it reaches a cell that has unvisited neighbors it can continue to.
 */

public class BacktrackerDS {
    /**
     * The last node in the backtrackerDS
     */
    Node end;
    /**
     * The length of the backtrackerDS
     */
    int length;

    /**
     * Default constructor that initializes the length to 0 and the end to a null object
     */
    public BacktrackerDS(){
        length = 0;
        end = null;
    }

    /**
     * This method adds a new node (visited cell) to the backtrackerDS with its coordinates and if it was visited
     * @param ifVisited if the cell was visited
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     */
    public void append(boolean ifVisited, int x, int y){
        Node toAdd = new Node(ifVisited, x, y);
        toAdd.next = end;
        end = toAdd;
        length++;
    }

    /**
     * This method pops the last element (cell) out of the backtrackerDS
     */
    public void pop(){
        if (end == null){
            return;
        }
        end = end.next;
        length--;

    }

    /**
     * This method returns the length of the backtrackerDS
     * @return the length of the data structure
     */
    public int getLength(){
        return length;
    }

    /**
     * Returns the x coordinate of the last visited cell
     * @param X the current x position of the maze generator
     * @return the x coordinate of the last visited cell
     */
    public int returnx(int X){
        if(end == null){
            return X;
        } else {
            return end.x;
        }
    }

    /**
     * Returns the y coordinate of the last visited cell
     * @param Y the current y position of the maze generator
     * @return the y coordinate of the last visited cell
     */
    public int returny(int Y){
        if(end == null){
            return Y;
        } else {
            return end.y;
        }
    }

    /**
     * A node in the backtrackerDS representing an individual cell the maze generator has visited
     */
    class Node{
        /**
         * If the node/cell was visited by the maze generator
         */
        boolean visited;
        /**
         * The x coordinate of the cell
         */
        int x;
        /**
         * The y coordinate of the cell
         */
        int y;
        /**
         * The next node the maze generator visits
         */
        Node next;

        /**
         * Constructor initializes the cell/node
         * @param visited if the cell was visited
         * @param x the x coordinate of the cell
         * @param y the y coordinate of the cell
         */
        public Node(boolean visited, int x, int y){
            this.visited = visited;
            this.x = x;
            this.y = y;
        }
    }
}