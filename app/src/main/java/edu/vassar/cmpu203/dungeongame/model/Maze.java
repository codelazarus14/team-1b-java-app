package edu.vassar.cmpu203.dungeongame.model;
import java.lang.Math;
import java.util.ArrayList;

/*When I made this my brain was on something and I thought that we could be putting in the
 * X and Y coordinates of the Maze. I can remove that if we want but otherwise just consider
 * the thing to be futureproofed */

public class Maze {
    /* The Maze class is long and has a lot of sub bits but the end will ignore 99% of it. It is mostly for creating traversable mazes
    At the end it will output a 2D array which contains the Nodes, which contain the Wall data. This is what the game will look at. */
    int yaxis;
    int xaxis;
    Node[][] mazeArray; //This will store every node in the maze
    ArrayList<Pair> mazeTable; //This stores every single possible pair of mazeArray
    DisjointSets djsTable; //This will be used to connect the mazeArray into a single chain
    ArrayList<Pair> adjTable; //This will record the final set of pairs which is used in djsTable's chain
    /* The mazeTable consists of a ton of pairs, and as a whole consists of
    All the adjacencies between given Nodes (grid blocks) */
    public Maze(int size) {
        this.yaxis = size; //y axis value
        this.xaxis = size; //x axis value
        this.mazeArray = new Node[xaxis][yaxis];
        this.mazeTable = new ArrayList<>();
        int nodeCounter = 0;
        for (int i = 0; i < yaxis; i++) {
            //At this phase in implementation, a Maze is basically a 2D array which goes [x][y]
            for (int j = 0; j < xaxis; j++) {
                Pair rightPair = new Pair(nodeCounter, nodeCounter + 1);
                Pair downPair = new Pair(nodeCounter, nodeCounter + xaxis);
                /* With the same logic as the right and downwalls, the adjacencies are
                only made for the right and down of a given Node as the left and up
                are already covered by their neighbors and therefore would only create
                redundant adjacencies. Redundancy bad. Redundancy real bad. */
                this.mazeArray[i][j] = new Node(xaxis, yaxis, nodeCounter);
                nodeCounter += 1; //the nodeCounter is the id of a node
                if (((j + 1) == xaxis) && ((i + 1) == yaxis)) {
                /*Case A: The node in question is the corner node and
                therefore has no adjacencies to its down or right.
                There is nothing inside the body because I want it to skip here*/ }
                else if ((j + 1) == xaxis) {
                    /* Case B: The Node is at the far right of the maze and so
                    all of its right adjacencies would only take it off of the map */
                    mazeTable.add(downPair);}
                else if ((i + 1) == yaxis) {
                    /* Case C: The same as B, but with the bottom edge of the maze */
                    mazeTable.add(rightPair); }
                else {
                    /* Case D: This is the default case, where the Node in question
                    is at neither edge of the maze */
                    mazeTable.add(rightPair);
                    mazeTable.add(downPair); } }
        }
    }
    public void buildMaze() {
        /* This method creates the actual maze. At total random, pairs will be picked from the mazeTable created in the Maze class
         *  until all the viable disjoints have been consumed and the rest are all discarded. The viable ones are all stored in the adjacency table
         * Then the method goes through the adjacency table and breaks down the barriers between the paired mazeArray in the table */
        int totalSize = (xaxis*(yaxis -1)) + (yaxis * (xaxis - 1)); //Mathematically, this is the amount of Pairs as should have been generated in mazeTable
        djsTable = new DisjointSets(); //this creates the path of the maze and determines if a given pair is a valid addition
        adjTable = new ArrayList<>(); //if a pair is a valid addition, it will be added to this, which is the final maze path
        while (totalSize > 0) {
            // This is what pulls the random pairs out of the mazeTable and then puts them into the adjTable
            int randomPos = (int) (Math.random() * totalSize);
            Pair randomPair = mazeTable.get(randomPos);
            mazeTable.remove(randomPos);
            totalSize -= 1;
            if (djsTable.addPair(randomPair)) {
                adjTable.add(randomPair);
                //a Pair is (node1 and node2), I will refer to node2 as the "neighbor" node that the wall is blocking
                int nodeYPos = randomPair.node1 % xaxis;  //this gets the y coordinate in the mazeArray[x][y]
                int nodeXPos = (randomPair.node1 - nodeYPos) / xaxis; //this gets the x coordinate in the mazeArray[x][y]
                //Case 1: The neighbor is to the right
                if (randomPair.node2 == (randomPair.node1 + 1)) {
                    mazeArray[nodeXPos][nodeYPos].rbarrier = false; }
                //Case 2: The neighbor is to the left
                else if (randomPair.node2 == (randomPair.node1 - 1)) {
                    mazeArray[nodeXPos][nodeYPos - 1].rbarrier = false; }
                //Case 3: The neighbor is below
                else if (randomPair.node2 == (randomPair.node1 + xaxis)) {
                    mazeArray[nodeXPos][nodeYPos].dbarrier = false; }
                //Case 4: The neighbor is above
                else if (randomPair.node2 == (randomPair.node1 - xaxis)) {
                    mazeArray[nodeXPos - 1][nodeYPos].dbarrier = false; }
            }
        }
    }
    public boolean checkValid(int[] pos, char dir) {
        //This method checks if a move in a given direction will be valid or not
        //"valid" being if there is a wall in the way or not
        /* Up and Left have subcases because Node do not carry left or up wall information in them
        So checkValid calculates the validity. If x == 0, it is at the top edge. if y % yaxis == 0, it is at the left edge  */
        int x = pos[0];
        int y = pos[1];
        boolean validity = true;
        switch (dir) {
            case 'u':
                validity = y != 0 && !mazeArray[y - 1][x].dbarrier;
                break;
            case 'd':
                validity = !mazeArray[y][x].dbarrier;
                break;
            case 'l':
                validity = x != 0 && !mazeArray[y][x - 1].rbarrier;
                break;
            case 'r':
                validity = !mazeArray[y][x].rbarrier;
                break;
            default:
                System.out.println("Error: invalid direction in checkValid");
                break;
        }
        return validity;
    }
    public static void main(String[] args) {
        //Currently this is just diagnostic stuff. I wanted to be able to see how if the mazeArray and adjacency tables were being correctly spat out.
        int size = 5;
        Maze testMaze = new Maze(size);
        testMaze.buildMaze();
        for (int i = 0; i < testMaze.adjTable.size(); i++) { //this is rigged up to print out all the valid pairs which ended up in the adjTable
            System.out.println("{"+(testMaze.adjTable.get(i).node1)+" , "+(testMaze.adjTable.get(i).node2) + "}" ); }
        System.out.println((testMaze.adjTable.size()));
        for (int j = 0; j < size; j++) {
            for (int g = 0; g < size; g++) { //this will print out the wall status of each node in the whole table
                System.out.println(testMaze.mazeArray[j][g].rbarrier + " "+testMaze.mazeArray[j][g].dbarrier +" "+testMaze.mazeArray[j][g].id); } }
    }
}