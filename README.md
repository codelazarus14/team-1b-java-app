# Team 1B
# ***Iteration 2***
### Prototype Functionality
1. Random Maze Generation
   1. 2D array of Nodes generated, each node has random wall placement
   2. Maze is represented as a string on a TextView
2. Player Movement
   1. Player placed in maze and updates position with updatePos() and will <br />
   move on whether or not checkValid() in Maze.java determines there is a wall obstructing movement
3. Text-based Graphics
   1. Player is represented as a unique character []
   2. Wall represented by |
   3. Intersection between walls represented by +
   4. Obscured space (behind wall or out of bounds) represented by #
   5. End of maze represented by E characters on all 4 corners of end node

### Prototype Limitations
1. One maze generated per session, completing a maze displays a custom dialog and allows <br />
the user to generate a new maze
2. Maze will not end properly with arrow keys/WASD and must be completed by using the <br />
arrow controls in the phone emulator
3. Fails rotation test, no bundling arguments/overriding fragment factory yet<br />

### Prototype Functionality
1. Run app and press Start on main menu
2. Tap visible arrow buttons or (on desktop) use arrow keys/WASD to traverse the maze
3. After maze is complete, tap outside dialog to close and use Reset to generate another
