# Domain Model

```plantuml
@startuml
hide circle
hide empty methods

'classes
class Player{
    position
}
class Maze{
    size
    layout
}
class Node{
    barriers
    position
}
class Room{
    position
    size
    shape
}
class Inventory{
}
class Loot{
}
class Chest{
}
class Game{
}

'associations 
Game "1" -- "1" Player : Contains\t\t
Game "1" -- "1" Maze : Contains
Player "1" - "1" Maze : \tTraverses\t\t
Maze "1" -- "1..*" Room : Contains
Maze "1" - "1..*" Node : \tContains\t\t
Player "1" -- "1" Inventory : Accesses\t\t
Inventory "1" -- "*" Loot : Stores\t
Chest "1" -- "1..*" Loot : Contains\t\t
Room "1" - "*" Chest : \tContains\t\t
@enduml
```

# Sequence Diagrams

## Player Movement

```plantuml
@startuml
hide footbox
actor "Player" as player
participant ": Maze" as maze

player -> player : move(dir)
activate player

player -> maze: checkValid(newPos)
activate maze
alt isValidPos
  maze -> player : updatePos(newPos)
else !isValidPos
  maze -> player : updatePos(Player.getPos())
end

@enduml
```

## Maze Generation

```plantuml
@startuml
hide footbox
participant ": Game" as game
participant ": Maze" as maze

game -->> maze **: create
activate game

maze -> maze : generateLevel
activate maze

ref over maze
GenerateMaze
end ref

@enduml
```
```plantuml
@startuml
hide footbox
mainframe sd GenerateMaze

participant ": Maze" as maze
participant "mazeGrid[n][n] : Node" as nodes
participant "newNode : Node" as node

maze -->> nodes** : create
activate maze
maze -> nodes : createTable()
activate nodes
loop i in 0..mazeGrid.size-1
  nodes -->> node** : create
  node -> nodes : addToTable(newNode)
end

@enduml
```

# Class Diagram

```plantuml
@startuml

skinparam classAttributeIconSize 0 

class DisplayMazeConsole {
    {static} public main(String[] args) : void
}
class Player{
    pos : int[] = {int, int}
--
    getPos() : int[]
    updatePos (int dir) : void
}

class Maze{
    +size : int
--
    checkValid(pos) : void
    generateMaze(size) : void
}

class Node{
    barrier : boolean
    index : int {range=[0-n^2]}
--
    getIndex() : int
}

Maze *- "(size^2) \nmazeGrid \n <ordered, Node[][]>" Node : \t\t\t\t
DisplayMazeConsole ->  "(1)\nPlayer\n" Player : \t\t
DisplayMazeConsole -> "(1)Maze\n" Maze : \t\t
@enduml
'''
