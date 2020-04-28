/**
 * The Leaderboard class generates the GUI for the leaderboard and manages the text files where the leaderboard
 * is stored. There is a separate leaderboard for every size of 3D maze and they are stored in text files in
 * the Leaderboards folder. The leaderboard stores a player's name and their time.
 * (Code for reading and writing to a text file from https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java)
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Leaderboard {
    /**
     * The dimensions of the maze
     */
    int dimensions;
    /**
     * An ArrayList that stores the information of the leaderboard with player objects
     */
    ArrayList<Player> leaderboard;
    /**
     * A boolean to keep track if the player submitted their name and time to the leaderboard
     */
    boolean submitted;
    /**
     * A string for the file path to the leaderboard text file given some dimensions
     */
    String leaderboardPath;

    /**
     * A constructor that reads the information in the leaderboard text file for those dimensions and displays them
     * in a GUI. If the user decides to submit their time to the leaderboard, their data is written to the leaderboard
     * text file.
     * @param dimensions the dimensions of the maze
     * @param time the player's time
     */
    public Leaderboard(int dimensions, double time){
        // Initialize fields
        this.dimensions = dimensions;
        leaderboard = new ArrayList<Player>();
        submitted = false;
        leaderboardPath = "Leaderboards/" + dimensions + "Leaderboard.txt";

        // If the leaderboard text file for those dimensions doesn't exist, then create a new one
        try {
            File leaderboardFile = new File(leaderboardPath);
            leaderboardFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reads the information in the leaderboard text file
        try {
            FileReader reader = new FileReader(leaderboardPath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                /* Reads two lines in the text file, the first one for the name and the second one for their time.
                   Then this information is turned into a new Player object and added to the leaderboard ArrayList. */
                String name = line;
                line = bufferedReader.readLine();
                double otherTimes = Double.parseDouble(line);
                leaderboard.add(new Player(name, otherTimes));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the frame to hold the leaderboard
        JFrame window = new JFrame(dimensions + "x" + dimensions + " Leaderboard");
        window.setLayout(new GridBagLayout());

        JPanel leaderboardPanel = new JPanel();
        /* Initializes the columns and data for the leaderboard that will be stored in a JTable
           Code for the table from https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple */
        String[] columns = {"Rank", "Name", "Time"};
        Object[][] data = new Object[leaderboard.size()][3];
        for(int i = 0; i < leaderboard.size(); i++){
            data[i][0] = i+1;
            data[i][1] = leaderboard.get(i).name;
            data[i][2] = convertTime(leaderboard.get(i).time);
        }
        JTable table = new JTable();
        /* Make it so the table cannot be edited
           Code from https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable */
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // set all cells to false
                return false;
            }
        };
        table.setModel(tableModel);

        // Set the width of the columns in the table
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Add the table to a scrollPane and add that scrollPane to the leaderboardPanel
        table.setPreferredScrollableViewportSize(new Dimension(300, 250));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        leaderboardPanel.add(scrollPane);

        // A label to tell the player to add their time
        JPanel addScorePanel = new JPanel();
        JLabel addScore = new JLabel("Add your time to the leaderboard! Your time: " + convertTime(time));
        addScorePanel.add(addScore);

        // A textfield for the player to enter their name
        JPanel textPanel = new JPanel();
        JTextField nameInput = new JTextField("Enter your name here");
        textPanel.add(nameInput);

        // A button for the player to submit their time
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            /* When the button is pressed, add the player and their time to the leaderboard ArrayList and
               place them in the correct spot depending on their time. Then delete the frame. */
            public void actionPerformed(ActionEvent e) {
                addPlayer(nameInput.getText(), time);
                submitted = true;
                window.dispose();
            }
        });

        // A cancel button if the player does not want to enter their time into the leaderboard
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            // When the button is pressed, the frame is deleted
            public void actionPerformed(ActionEvent e) {
                submitted = true;
                window.dispose();
            }
        });

        // The submit and cancel button are added to the JPanel storing the buttons
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        /* Using GridBagConstraints, the frame can be split into multiple panels that each take up a section of the frame.
           Here the frame is divided vertically and each panel is added with their size (weighty) and their position (gridy).
           Code from https://stackoverflow.com/questions/28425321/java-divide-the-screen */
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

        // display the frame
        window.setSize(new Dimension(400, 420));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }

    /**
     * This method adds the player to the leaderboard ArrayList when they submit their score and
     * updates the leaderboard text file with their time.
     * @param name the player's name
     * @param time the player's time
     */
    private void addPlayer(String name, double time){
        // Create a new player object with their name and time
        Player toAdd = new Player(name, time);

        // If the leaderboard is empty, then add the player
        if(leaderboard.size() == 0)
            leaderboard.add(toAdd);
        // If not, then compare the player's times to determine where the player should be added in the leaderboard
        else {
            for (int i = 0; i < leaderboard.size(); i++) {
                if (toAdd.time < leaderboard.get(i).time) {
                    leaderboard.add(i, toAdd);
                    break;
                }
            }
        }

        try {
            // clearWriter clears the leaderboard text file
            PrintWriter clearWriter = new PrintWriter(leaderboardPath);
            clearWriter.print("");
            clearWriter.close();

            /* The new leaderboard ArrayList with the player's name and time are then put into the leaderboard text file
               with each player's name on one line and their time on the next. */
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

    /**
     * This method converts the player's time in milliseconds to the form of minutes:seconds:deciseconds
     * to be displayed on the leaderboard.
     * @param time the player's time
     * @return the player's time in a string in the form of minutes:seconds:deciseconds
     */
    private String convertTime(double time) {
        // Find the number of minutes
        int minutes = (int) (time / 60000);
        time -= minutes * 60000;

        // Find the number of seconds
        int seconds = (int) (time / 1000);
        time -= seconds * 1000;
        String secondsString = "";
        // If seconds is one digit, add a 0 in front of it
        if (String.valueOf(seconds).length() == 1) {
            secondsString = "0" + seconds;
        } else {
            secondsString = Integer.toString(seconds);
        }

        // Find the number of deciseconds
        int deciseconds = (int) (time/10);
        String decisecondsString = "";
        // If the deciseconds is one digit, add a 0 in front of it
        if (String.valueOf(deciseconds).length() == 1) {
            decisecondsString = "0" + deciseconds;
        } else {
            decisecondsString = Integer.toString(deciseconds);
        }

        // If the player's time is less than one minute, don't display the minutes
        if (minutes == 0) {
            return (secondsString + "." + decisecondsString);
        } else {
            return (minutes + ":" + secondsString + "." + decisecondsString);
        }
    }
}

/**
 * The Player class stores one player's name and time and it stored in the leaderboard ArrayList
 */
class Player {
    /**
     * The player's name
     */
    String name;
    /**
     * The player's time
     */
    double time;

    /**
     * A constructor that initializes the player's name and time
     * @param name the player's name
     * @param time the player's time
     */
    public Player(String name, double time){
        this.time = time;
        this.name = name;
    }
}