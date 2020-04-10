
public class BacktrackerDS extends OrderedCollection{
    Node end;
    public BacktrackerDS(){
        end = null;
    }

    public void append(Boolean ToAppend, int x, int y){
        Node toAdd = new Node(ToAppend, x, y);
        toAdd.next = end;
        end = toAdd;
    }
    public void pop(){
        if (end == null){
            return;
        }
        end = end.next;

    }
    public int returnx(int X){
        if(end == null){
            return X;
        } else {
            return end.x;
        }
    }
    public int returny(int Y){
        if(end == null){
            return Y;
        } else {
            return end.y;
        }
    }

    class Node{
        boolean visited;
        int x;
        int y;
        Node next;
        public Node(boolean visited, int x, int y){
            this.visited = visited;
            this.x = x;
            this.y = y;
        }
    }
}
