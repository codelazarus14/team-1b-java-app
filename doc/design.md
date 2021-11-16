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
actor "User" as user
participant ": ControllerActivity" as controller
participant ": Player" as player
participant ": Maze" as maze

user -> controller : "onPlayerInput(dir)"

controller -> player : move(dir)
activate controller

player -> player : move(dir)
activate player

player -> maze: checkValid(Player.getPos(), dir)
activate maze
alt isValidPos
  maze -> player : true
else !isValidPos
  maze -> player : false
end

player -> player : updatePos(dir)

@enduml
```

## Maze Generation

```plantuml
@startuml
hide footbox
participant ": ControllerActivity" as game
participant ": Maze" as maze
participant "mazeArray[n][n] : Node" as nodes
participant "newNode : Node" as node

game -->> maze **: create
activate game

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
    mazeArray : Node[][]
    mazeTable : ArrayList<Pair>
    djsTable : DisjointSets
    adjTable : ArrayList<Pair>
--
    checkValid(pos) : boolean
}

class Node{
    rbarrier : boolean
    dbarrier : booleam
    index : int {range=[0, n^2]}
--
    getIndex() : int
}

class Pair{
    int node1;
    int node2;   
}

class DisjointSets{
    ArrayList<ArrayList<Integer>> disjointSets;
--
    addPair(Pair n) : boolean
}
 
Maze *- "(size^2) \mazeArray \n <ordered, Node[][]>" Node : \t\t\t\t
DisplayMazeConsole ->  "(1)\nPlayer\n" Player : \t\t
DisplayMazeConsole -> "(1)Maze\n" Maze : \t\t
DisjointSets --> "(1)djsTable" Maze : \t\t\t
Pair -> "(2*(size*(size - 1)))\nMaze" Maze : \t\t
Pair -> "(size - 1)\ndisjointSets" DisjointSets : \t\t\t
@enduml
'''
