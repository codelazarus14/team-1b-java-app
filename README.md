# Team 1B
# ***Iteration 1***
### Prototype Functionality
1. Random Maze Generation
   1. 2D array of Nodes generated, each node has random wall placement
   2. Maze is printed to console
2. Player Movement
   1. Player placed in maze and updates position with updatePos() and will <br />
   move on whether or not checkValid() in Maze.java determines there is a wall obstructing movement
3. Text-based Graphics
   1. Player is represented as a unique character ⊆⊇
   2. Wall represented by |
   3. Double wall represented by ==
   4. Intersection between walls represented by +

### Prototype Limitations
1. Maze generation is completely random and traversability is not garuanteed
2. Off Map movement is hardcoded to be prevented however the wall representation may not show this

### Prototype Functionality
1. Run main() method in DisplayMazeConsole.java
2. movement controlled with the WASD method
   1. w = up
   2. s = down
   3. a = left
   4. d = right

### Testing Report
1. Screenshots of Maze before and after inputting movement characters.
   1. (original maze with player -> player enters move -> maze with updated player pos)
