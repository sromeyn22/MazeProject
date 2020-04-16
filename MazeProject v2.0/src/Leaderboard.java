import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
// code from https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java

public class Leaderboard {
    int dimensions;
    ArrayList<Player> leaderboard;
    boolean submitted;
    String leaderboardPath;

    public Leaderboard(int dimensions, double time){
        this.dimensions = dimensions;
        leaderboard = new ArrayList<Player>();
        submitted = false;
        leaderboardPath = "Leaderboards/" + dimensions + "Leaderboard.txt";

        try {
            File leaderboardFile = new File(leaderboardPath);
            leaderboardFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileReader reader = new FileReader(leaderboardPath);
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

        JFrame window = new JFrame(dimensions + "x" + dimensions + " Leaderboard");
        window.setLayout(new GridBagLayout());

        JPanel leaderboardPanel = new JPanel();
        // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple
        String[] columns = {"Rank", "Name", "Time"};
        Object[][] data = new Object[leaderboard.size()][3];
        for(int i = 0; i < leaderboard.size(); i++){
            data[i][0] = i+1;
            data[i][1] = leaderboard.get(i).name;
            data[i][2] = convertTime(leaderboard.get(i).time);
        }
        JTable table = new JTable();
        // https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        table.setModel(tableModel);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        table.setPreferredScrollableViewportSize(new Dimension(300, 250));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        leaderboardPanel.add(scrollPane);

        JPanel addScorePanel = new JPanel();
        JLabel addScore = new JLabel("Add your score to the leaderboard! Your time: " + convertTime(time));
        addScorePanel.add(addScore);

        JPanel textPanel = new JPanel();
        JTextField nameInput = new JTextField("Enter your name here");
        textPanel.add(nameInput);

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer(nameInput.getText(), time);
                submitted = true;
                window.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitted = true;
                window.dispose();
            }
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        // https://stackoverflow.com/questions/28425321/java-divide-the-screen
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridy = 1;
        window.add(leaderboardPanel, c);
        c.weighty = .1;
        c.gridy = 2;
        window.add(addScorePanel, c);
        c.weighty = .1;
        c.gridy = 3;
        window.add(textPanel, c);
        c.weighty = .1;
        c.gridy = 4;
        window.add(buttonPanel, c);

        window.setSize(new Dimension(400, 420));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void addPlayer(String name, double time){
        Player toAdd = new Player(name, time);

        if(leaderboard.size() == 0)
            leaderboard.add(toAdd);
        else {
            for (int i = 0; i < leaderboard.size(); i++) {
                if (toAdd.time < leaderboard.get(i).time) {
                    leaderboard.add(i, toAdd);
                    break;
                }
            }
        }

        try {
            PrintWriter clearWriter = new PrintWriter(leaderboardPath);
            clearWriter.print("");
            clearWriter.close();
            FileWriter writer = new FileWriter(leaderboardPath, true);
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

    public String convertTime(double time) {
        int minutes = (int) (time / 60000);
        time -= minutes * 60000;
        int seconds = (int) (time / 1000);
        time -= seconds * 1000;
        String secondsString = "";
        if (String.valueOf(seconds).length() == 1) {
            secondsString = "0" + seconds;
        } else {
            secondsString = Integer.toString(seconds);
        }
        int milliseconds = (int) time;
        String millisecondsString = "";
        if (String.valueOf(milliseconds).length() == 1) {
            millisecondsString = "00";
        } else if (String.valueOf(milliseconds).length() == 2) {
            millisecondsString = "0" + milliseconds / 10;
        } else {
            millisecondsString = Integer.toString(milliseconds / 10);
        }
        if (minutes == 0) {
            return (secondsString + "." + millisecondsString);
        } else {
            return (minutes + ":" + secondsString + "." + millisecondsString);
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