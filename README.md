# Team 1B
# ***Final Iteration***
### Final Submission Functionality
1. Maze Generation
   1. 2D array of Nodes generated, Kruskal-based, fully traversable Maze generated
   2. Size-specific amount of empty spaces (rooms) generated within the maze
   3. Size-specific amount of interactables (Note/Chest/Mimic) placed within maze
      1. placement is biased to prefer areas with 2-3 walls
2. Player Movement
   1. Player placed in maze and updates position with updatePos() and will <br />
   move on whether or not checkValid() in Maze.java determines there is a wall obstructing movement
3. Player Buttons
   1. "Arrow"(V) buttons handle Player Movement. When pressed, player will move in indicated direction unless blocked by a wall
   2. Interact button allows player to open Chests, trigger Mimics, read Notes, and trigger next level/game end
   3. Inventory button shows amount and type of Player-collected loot    
4. Text-based Graphics
   1. Player is represented as a unique character []
   2. Wall represented by |
   3. Intersection between walls represented by +
   4. Obscured space (behind wall or out of bounds) represented by #
   5. End of maze represented by E characters on all 4 corners of end node
   5. Chest represented by C characters on all 4 corners of end node
   5. Mimic represented by C characters on all 4 corners of end node
   5. Note represented by N characters on all 4 corners of end node
   6. Obscured Vision: Only a 3x3 around the player are shown at a given time
      1. Player cannot see around corners and squares which are "out of view" are Obscured
5. Gameplay Loop
   1. When Player interacts with end of maze, option is given to proceed to next level or quit
   2. If next level chosen, new maze is generated which is between 1 and 3 larger than previous
      1. Inventory is preserved between mazes
   3. If quit chosen, Player can enter a username and is entered into Firestore database and shown on a leaderboard
      1. Repeat usernames will not override past iterations
      2. Player inventory and # of completed maze processed into a score to be saved with username
      3. Top 10 scores will be shown on a "Leaderboard"
6. Interactables
   1. Chests contain x amount of a random kind of loot
   2. Notes show a random block of Text
   3. Mimics appear as Chests but will steal x amount of a random item from player inventory
   4. Nothing is the default case and nothing will happen when interacted with
   5. End is only at the bottom right cornser and is used to trigger the next level/quit options popup

### Final Submission Limitations
1. No Authentication
2. Very difficult to test the leaderboard view because it is very difficuly to simulate/create a <br />
program which can solve our maze so that we can see its result on the leaderboard

### Final Submission Functionality
1. Run app, select difficutly if desired, and press Start on main menu
2. Tap visible arrow buttons or (on desktop) use arrow keys/WASD to traverse the maze
3. When on an interactable press interact to collect items, read note, or get got by a mimic
4. When at end of maze, press interact button and either proceed to next level or quit game
