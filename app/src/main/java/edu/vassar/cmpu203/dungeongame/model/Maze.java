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
              /*
        Preliminary Room Generator-
        For this implementation, a room is not a special object or anyways different from just hallway
        This essentially creates square/rectangular spaces inside of which the walls are all broken
        Though it does not break or create any new walls along the edge of the room.
        This is a very basic way of getting around the Kruskal method's thing of not creating loops
         */
        int roomCount = xaxis / 4;
        /* I have this being an arbitrary number because I didn't want it being random and just accidentally
        overwhelming the entire maze with empty space */
        while (roomCount >= 0) {
            int roomHeight; //this is the vertical size of a given room
            int roomWidth; //this is the horizontal size of a given room
            /* The following cases are just accounting for room size compared to overall maze size. The larger
            the Maze gets, the smaller the relative room size needs to be because otherwise it would just
            completely overwhelm the maze with oodles of empty space */
            if (xaxis <= 15) {
                roomHeight = (int) (Math.random() * (xaxis / 2));
                roomWidth = (int) (Math.random() * (yaxis / 2));
                while (roomHeight < 2) {
                    /*Each if case has these- it's to make sure the rooms aren't of size 2, which is just
                    completely indistinguishable from regular maze hallway */
                    roomHeight = (int) (Math.random() * (xaxis / 2));
                    roomWidth = (int) (Math.random() * (yaxis / 2)); } }
            else if (xaxis <= 30){
                roomHeight = (int) (Math.random() * (xaxis / 3));
                roomWidth = (int) (Math.random() * (yaxis / 3));
                while (roomHeight < 2) {
                    roomHeight = (int) (Math.random() * (xaxis / 3));
                    roomWidth = (int) (Math.random() * (yaxis / 3)); } }
            else {
                roomHeight = (int) (Math.random() * (xaxis / 4));
                roomWidth = (int) (Math.random() * (yaxis / 4));
                while (roomHeight < 2) {
                    roomHeight = (int) (Math.random() * (xaxis / 4));
                    roomWidth = (int) (Math.random() * (yaxis / 4)); } }
            /*these are the Y and X positions of the top corner of the room in the maze
            with accounting to make sure that a room never goes off the "edge" of a maze
             */
            int roomYPos = (int)(Math.random() * (xaxis - roomHeight));
            int roomXPos = (int)(Math.random() * (yaxis - roomWidth));
            for (int y = roomYPos; y < roomHeight + roomYPos; y++){
                for (int x = roomXPos; x < roomWidth + roomXPos; x++){
                    if (y == roomHeight && x == roomWidth) {
                        break; }
                    else if (y == roomHeight) {
                        mazeArray[x][y].dbarrier = false; }
                    else if (x == roomWidth) {
                        mazeArray[x][y].rbarrier = false; }
                    else{
                        mazeArray[x][y].dbarrier = false;
                        mazeArray[x][y].rbarrier = false; } } }
            roomCount--; }
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

    public String toString(Player p) {
        String out = "";
        char lSymbol = 8838;
        char rSymbol = 8839;
        String avatar = Character.toString(lSymbol) + Character.toString(rSymbol);
        int size  = xaxis;

        for (int i = 0; i < size; i++) {
            out += "+ -- ";
        }
        out += "+\n";
        for (int i = 0; i < size; i++) {
            out += "|";
            for (int j = 0; j < size; j++) {
                out += " ";
                out += p.getPos()[1] == i && p.getPos()[0] == j ? avatar : "  ";
                out += this.mazeArray[i][j].rbarrier ? " |" : "  ";
            }
            out += "\n";
            for (int j = 0; j < size; j++) {
                out += this.mazeArray[i][j].dbarrier ? "+ -- " : "+    ";
            }
            out += "+\n";
        }

        out += " ";
        return out;
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