# Play Game
## Scope
Dungeon Game
## Level
User goal
## Primary Actor
Player
## Stakeholders
Player
## Preconditions
Game is open on main menu
## Postconditions
Game is over or player has closed the app, performed [Save game](./save-game.md)
## Main Success Scenario
1. After pressing start button, game generates a new maze
2. Player traverses maze with controls
3. Periodically, game is saved temporarily to preserve player progress while switching apps
## Extensions
2a. Player interacts with interactables ([Pick lock](./pick-lock.md), [Fight](./fight.md), [Open note](./use-cases-brief.md))  <br />
2b. After player reaches end of maze, generate a new maze and start over again

## Frequency of Occurrence
Very frequent, for the entire duration of a game

```plantuml
actor "Player" as player

package "Dungeon Game" {
  usecase "Play game" as playGame
}

player --> playGame
```
