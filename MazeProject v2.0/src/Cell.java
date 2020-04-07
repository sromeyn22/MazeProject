class Cell{
    boolean UnVisited;
    boolean Path;
    boolean UpWall;
    boolean RightWall;
    boolean DownWall;
    boolean LeftWall;

    public Cell(){
        Path = false;
        UnVisited = true;
        UpWall = true;
        RightWall = true;
        DownWall = true;
        LeftWall = true;
    }

}