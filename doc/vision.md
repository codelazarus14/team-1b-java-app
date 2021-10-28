# Main Basement Dungeon Game Vision
## Introduction:
We envision a top-down dungeon crawler that takes place in the basement of 
Main House. The dungeon will be randomly generated (thus creating an infinite amount of levels)
to be navigated by the player with treasure to find. We are hoping to have a 
loose story based on Vassar lore (specifically Main basement lore) and 
possibly other mini-games such as lock picking and combat.
The maze will be simple rooms that the player must navigate until they find the exit.

## Business Case:
1. Useful to kill time
2. Our game will have infinite replayability
3. Progress can be saved
4. It will have a Vassar story experience
5. Looting satisfaction

## Key Features Summary:
* Will generate a maze that the player can navigate using joystick controls
* Save function that allows the player to pick up at the level they left off on
* Online leaderboard where players can enter their initials and post their score
* Chests with treasure in them
* Lock picking mini-game
* Combat

## Stakeholder goals summary:
* Player: plays game, navigates maze, interacts with leaderboard
* Developers: create functional game
* Leaderboard: displays scores online

```plantuml
@startuml

' human actor
actor "Player" as player

package "Dungeon Game" {
    usecase "Play game" as game
    usecase "Pick lock" as lock
    usecase "Fight" as fight
    usecase "Loot" as loot
}

' relationships
player --> game
player --> lock
player --> fight
player --> loot

@enduml
'''