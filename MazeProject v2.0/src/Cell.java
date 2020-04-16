class Cell{
    boolean UnVisited;
    boolean Path;
    boolean UpWall;
    boolean RightWall;
    boolean DownWall;
    boolean LeftWall;
    boolean enemy;
    boolean heart;
    int life;

    public Cell(){
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
