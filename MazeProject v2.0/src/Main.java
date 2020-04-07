import javax.swing.*;

public class Main {
    public static void main (String[] args){
//        JFrame frame = new JFrame("Maze");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        FrontPageGraphics mainInstance = new FrontPageGraphics();
//        frame.setContentPane(mainInstance);
//        frame.pack();
//        frame.setVisible(true);

        JButton addButton = new JButton( new AbstractAction("add") { @Override public void actionPerformed( ActionEvent e ) { // add Action } });
            JButton substractButton = new JButton( new AbstractAction("substract") { @Override public void actionPerformed( ActionEvent e ) { // substract Action } });
    }
}
