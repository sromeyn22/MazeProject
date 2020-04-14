import java.io.*;
import java.util.ArrayList;
// code from https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java

public class Leaderboard {
    int dimensions;
    ArrayList<Player> leaderboard;

    public Leaderboard(int dimensions){
        this.dimensions = dimensions;
        leaderboard = new ArrayList<Player>();

        try {
            FileReader reader = new FileReader(dimensions + "Leaderboard.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String name = line;
                line = bufferedReader.readLine();
                double otherTimes = Double.parseDouble(line);
                leaderboard.add(new Player(name, otherTimes));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(String name, double time){
        Player toAdd = new Player(name, time);

        for(int i = 0; i < leaderboard.size(); i++){
            if(toAdd.time < leaderboard.get(i).time){
                leaderboard.add(i, toAdd);
                break;
            }
        }

        try {
            PrintWriter clearWriter = new PrintWriter(dimensions + "Leaderboard.txt");
            clearWriter.print("");
            clearWriter.close();
            FileWriter writer = new FileWriter(dimensions + "Leaderboard.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for(int i = 0; i < leaderboard.size(); i++){
                bufferedWriter.write(leaderboard.get(i).name);
                bufferedWriter.newLine();
                bufferedWriter.write(Double.toString(leaderboard.get(i).time));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Player {
    String name;
    double time;

    public Player(String name, double time){
        this.time = time;
        this.name = name;
    }
}