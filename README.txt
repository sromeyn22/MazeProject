The code can be compiled and run by using:
javac *.java
java Main

In the first screen that appears, the user is able to choose the options for the maze such as the dimensions of the maze, the type of maze (2D/3D), a seed to generate a specific maze (each seed maps to one randomly generate maze), or if they want enemies or not.
In the 2D maze, the player can move around with the arrow keys and can press '1' to display the solution to the maze. 
In the 3D maze, the player can move around with the arrow keys and turn with 'a' or 'd'. In this maze, the player will also encounter both monsters and diamonds to heal with. To attack the monsters or heal, press the space bar. The player's health bar will be in the bottom left corner of the screen and will keep decreasing while the monster is still alive. 
The goal is to get from the top left square in the maze to the bottom right square in the shortest amount of time possible without dying. The compass in the bottom right corner will indicate the direction of the bottom right square. The floor also gets progressively darker red as the user gets closer to finishing the maze and the floor will turn green when the user completes the maze.
After the player finishes the 3D maze, a leaderboard will pop up with the best times done by other people. The player can then choose whether or not to submit their own time to the leaderboard.
