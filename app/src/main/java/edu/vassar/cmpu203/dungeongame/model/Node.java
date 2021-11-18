package edu.vassar.cmpu203.dungeongame.model;

public class Node {
    //This class is for the mazeArray of our maze.
    //1 = there is a wall, 0 = no wall
    boolean rbarrier = true;
    boolean dbarrier = true;
    /*There is no leftwall or upwall because if you think about it, the neighbor above it's dbarrier
     * will be this node's upwall. The same idea applies for leftwalls.
     */
    int id; //the id is essentially the node's position in the ArrayList. It's for convenient calling.

    public Node(int id)
    { //row value is xaxis, column value is in yaxis
        this.id = id;
    }
}