# Loot
## Scope
Dungeon Game
## Level
Subfunction
## Primary Actor
Player
## Stakeholders
Player
## Preconditions
Player has opened chest or defeated an enemy while [Playing game](./play-game.md)
## Postconditions
Loot has been added to player inventory
## Main Success Scenario
1. Player encounters chest and interacts to open chest, or is being rewarded for defeating an enemy
2. Game displays reward as a a dialogue with a particular artifact with icon and value displayed beneath
3. Player confirms the reward and the loot is added to their inventory, total value of player inventory <br />
now updated
## Extensions
2a. Player acquires duplicate artifact - game adds subscript to icon indicating the number of items of this type <br />
in player inventory and updates total value respectively

## Frequency of Occurrence
Less frequent - only occurs randomly after [Fight](./fight.md) or when opening chest

```plantuml
actor "Player" as player

package "Dungeon Game" {
  usecase "Loot" as loot
}

player --> loot
```
