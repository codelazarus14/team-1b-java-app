package edu.vassar.cmpu203.dungeongame.model;

public class Pair {
    int node1;
    int node2;
    /*This is a homemade Pair class for use in making the adjacency table
    * It just consists of id's of the two mazeArray in an adjacency
     */
    public Pair(int node1, int node2) {
        this.node1 = node1;
        this.node2 = node2; }
}
