```plantuml
@startuml

skinparam classAttributeIconSize 0 

class Player{
    pos : int[] = {int, int}
--
    getPos() : int[]
    updatePos (char dir) : void
}

class Maze{
    size : int
    mazeArray : Node[][]
--
    checkValid(int x, int y, char m) : boolean
}

class Node{
    wall : boolean
    pos : int[] = {int, int}
    storage : Node(F)
}

Maze *- "(Size^2) \nList of Nodes \n <list>" Node : \t\t

@enduml
'''
