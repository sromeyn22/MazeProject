# maze-project
ignore the commit message about opening the .idea folder to edit, open the MazeProject folder instead

also i think we should all use javadoc commenting (http://www.drjava.org/docs/user/ch10.html)

intellij autocompletes javadoc formatting for you (https://www.jetbrains.com/help/idea/working-with-code-documentation.html)

public static void main(String[] args) {
        JFrame frame = new JFrame("Physics!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        KeyboardSpheres mainInstance = new KeyboardSpheres();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }
