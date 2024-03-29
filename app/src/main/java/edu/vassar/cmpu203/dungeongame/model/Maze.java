package edu.vassar.cmpu203.dungeongame.model;
import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;
import android.util.Log;

/*When I made this my brain was on something and I thought that we could be putting in the
 * X and Y coordinates of the Maze. I can remove that if we want but otherwise just consider
 * the thing to be futureproofed */

public class Maze implements Serializable {
    /* The Maze class is long and has a lot of sub bits but the end will ignore 99% of it. It is mostly for creating traversable mazes
    At the end it will output a 2D array which contains the Nodes, which contain the Wall data. This is what the game will look at. */
    int yaxis;
    int xaxis;
    public Node[][] mazeArray; //This will store every node in the maze
    ArrayList<Pair> mazeTable; //This stores every single possible pair of mazeArray
    DisjointSets djsTable; //This will be used to connect the mazeArray into a single chain
    ArrayList<Pair> adjTable; //This will record the final set of pairs which is used in djsTable's chain
    /* The mazeTable consists of a ton of pairs, and as a whole consists of
    All the adjacencies between given Nodes (grid blocks) */

    /**
     * This is the Big Cahoona- the Maze generation.
     * As we do it, the Maze generation proceeds in four phases:
     * Phase 1: Constructor creates a 2D Array of Nodes, which all have their walls up by default.
     * It's basically a grid.
     * Phase 2: A Kruskal-Based maze generator goes through and uses disjoint sets and equivalency
     * classes to create a fully traversable maze.
     * Phase 3: Constructor creates a size-dependant number of "rooms" which are essentially
     * rectangular areas in which all walls are broken. This allows for open spaces and loops
     * Phase 4: Constructor goes through and places Chests, Notes, and Mimics until a size-dependant
     * number of said objects are placed, preferring Nooks and then Hallways.
     * @param size
     */
    public Maze(int size) {
        this.yaxis = size; //y axis value
        this.xaxis = size; //x axis value
        this.mazeArray = new Node[xaxis][yaxis];
        this.mazeTable = new ArrayList<>();
        int nodeCounter = 0;
        //Phase 1: Maze is a grid
        for (int i = 0; i < yaxis; i++) {
            //At this phase in implementation, a Maze is basically a 2D array which goes [x][y]
            for (int j = 0; j < xaxis; j++) {
                Pair rightPair = new Pair(nodeCounter, nodeCounter + 1);
                Pair downPair = new Pair(nodeCounter, nodeCounter + xaxis);
                /* With the same logic as the right and downwalls, the adjacencies are
                only made for the right and down of a given Node as the left and up
                are already covered by their neighbors and therefore would only create
                redundant adjacencies. Redundancy bad. Redundancy real bad. */
                this.mazeArray[i][j] = new Node(nodeCounter);
                nodeCounter += 1; //the nodeCounter is the id of a node
                if (((j + 1) == xaxis) && ((i + 1) == yaxis)) {
                /*Case A: The node in question is the corner node and
                therefore has no adjacencies to its down or right.
                There is nothing inside the body because I want it to skip here*/
                } else if ((j + 1) == xaxis) {
                    /* Case B: The Node is at the far right of the maze and so
                    all of its right adjacencies would only take it off of the map */
                    mazeTable.add(downPair);
                } else if ((i + 1) == yaxis) {
                    /* Case C: The same as B, but with the bottom edge of the maze */
                    mazeTable.add(rightPair);
                } else {
                    /* Case D: This is the default case, where the Node in question
                    is at neither edge of the maze */
                    mazeTable.add(rightPair);
                    mazeTable.add(downPair);
                }
            }
        }
        /** Phase 2:
         * This section of code creates the actual "maze". At total random, pairs will be picked from the mazeTable created in the Maze class
         * until all the viable disjoints have been consumed and the rest are all discarded. The viable ones are all stored in adjTable
         * Then the method goes through adjTable and breaks down the barriers between the pairs
         * This is all in keeping with a Kruskal-based generation.*/
        int totalSize = (xaxis * (yaxis - 1)) + (yaxis * (xaxis - 1)); //Mathematically, this is the amount of Pairs as should have been generated in mazeTable
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
                    mazeArray[nodeXPos][nodeYPos].rbarrier = false;
                }
                //Case 2: The neighbor is to the left
                else if (randomPair.node2 == (randomPair.node1 - 1)) {
                    mazeArray[nodeXPos][nodeYPos - 1].rbarrier = false;
                }
                //Case 3: The neighbor is below
                else if (randomPair.node2 == (randomPair.node1 + xaxis)) {
                    mazeArray[nodeXPos][nodeYPos].dbarrier = false;
                }
                //Case 4: The neighbor is above
                else if (randomPair.node2 == (randomPair.node1 - xaxis)) {
                    mazeArray[nodeXPos - 1][nodeYPos].dbarrier = false;
                }
            }
        }

        /**
         * Phase 3:
         * Room Generator-
         * For our implementation, a room is not a special object or anyways different from just hallway
         * This essentially creates square/rectangular spaces inside of which the walls are all broken
         * Though it does not break or create any new walls along the edge of the room.
         * This is a very basic way of getting around the Kruskal method's thing of not creating loops
         */
        int roomCount = xaxis / 4;
        while (roomCount >= 0) {
            int roomHeight = (int) (Math.random() * 4); //this is the vertical size of a given room
            int roomWidth = (int) (Math.random() * 4); //this is the horizontal size of a given room
            /*these are the Y and X positions of the top corner of the room in the maze
            with accounting to make sure that a room never goes off the "edge" of a maze */
            int roomYPos = (int) (Math.random() * (xaxis - roomHeight));
            int roomXPos = (int) (Math.random() * (yaxis - roomWidth));
            for (int y = roomYPos; y < roomHeight + roomYPos; y++) {
                for (int x = roomXPos; x < roomWidth + roomXPos; x++) {
                    if (y == roomHeight && x == roomWidth) {
                        break;
                    } else if (y == roomHeight) {
                        mazeArray[x][y].dbarrier = false;
                    } else if (x == roomWidth) {
                        mazeArray[x][y].rbarrier = false;
                    } else {
                        mazeArray[x][y].dbarrier = false;
                        mazeArray[x][y].rbarrier = false;
                    }
                }
            }
            roomCount--;
        }
        /** Phase 4
         * This is the code for interactable objects throughout the maze
         */
        int interactableQuantity = size * size / 12; //This is how many chests there will be
        if (interactableQuantity == 0) { //If the previous gives a no, there will always be at least 1 chest
            interactableQuantity = 1;
        }
        while (interactableQuantity > 0) {
            int randomPlacement = (int) (Math.random() * (size * size - 1)); //This should pick a random node in the maze
            int xCoord = randomPlacement % size;
            int yCoord = (randomPlacement - xCoord) / size;
            int wallCount = 0;
            /**
             * to explain the following code: The chest/note/mimic placement is made as to prefer nodes which have
             * 3 walls. Nooks or ends of Hallways, essentially. Hopefully this means there will be SOMETHING
             * at the end of that hallway. There is also a chance that it will choose nodes with only 2 walls.
             */
            for (char dir: new char[] {'u', 'r','d','l'}) {
                wallCount += checkValid(new int[] {xCoord, yCoord},dir) ? 0 : 1;
            }
            int randomChance = (int) (Math.random() * 3);
            int chestNoteMimic = (int) (Math.random() * 10);
            Interactable interactable;
            switch (chestNoteMimic) {
                case 0 : interactable = new Mimic();
                    break;
                case 9 :
                case 8 :
                case 7 :
                case 6 :
                case 5 :
                case 4 : interactable = new Chest();
                    break;
                case 3 :
                case 2 :
                case 1 : interactable = new Note();
                    break;
                default : interactable = new Chest();
                    break;
            }
            switch (wallCount) {
                case 0: break;
                case 1: break;
                case 2: if (randomChance > 1) {
                    mazeArray[yCoord][xCoord].nodeContents = interactable;
                    interactableQuantity -= 1;
                }
                        break;
               case 3: mazeArray[yCoord][xCoord].nodeContents = interactable;
                   interactableQuantity -= 1;
                   break;
                default:
                    break;
            }
        }
        mazeArray[xaxis - 1][xaxis - 1].nodeContents = new End();
    }


    /**
     * This method checks if a move in a given direction will be valid or not
     * "valid" being if there is a wall in the way or not
     * In the case of moving to the left or moving up, the wall values of the destination
     * Node are checked instead, as our Nodes do not carry Up and Left wall information
     * @param pos
     * @param dir
     * @return boolean
     */
    public boolean checkValid(int[] pos, char dir) {
        int x = pos[0];
        int y = pos[1];
        boolean validity = true;
        if (x < 0 || y < 0 || x > xaxis - 1 || y > xaxis - 1) {
            return false;
        }
        switch (dir) {
            case 'u':
                validity = y > 0 && !mazeArray[y - 1][x].dbarrier;
                break;
            case 'd':
                validity = !mazeArray[y][x].dbarrier;
                break;
            case 'l':
                validity = x > 0 && !mazeArray[y][x - 1].rbarrier;
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

    /** This toString is just used if we want to be able to see the entire maze at once
     * and is not part of the final product
     */
    public String toString() {
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
                String str = "  ";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Note") str = "NN";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Chest") str = "CC";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Mimic") str = "MM";
                str = j == size - 1 && i == size - 1 ? "EE" : str;
                out += this.mazeArray[i][j].rbarrier ? " " + str + " |" : " "+ str + "  ";
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


    /**
     * This method draws out the limited, 3x3 view of the Maze
     * @param p
     * @return String
     */
    public String toObscuredString(Player p) {
        String out = "";
        String avatar;
        avatar = "[]";
        int size = xaxis;

        int iPos = p.getPos()[1];
        int jPos = p.getPos()[0];

        boolean[][] visibleNode;
        visibleNode = new boolean[3][3];
        int[] vPos;
        vPos = new int[2];
        vPos[1] = iPos;
        vPos[0] = jPos;
        visibleNode[1][1] = true;
        visibleNode[0][1] = this.checkValid(vPos,'u'); //up
        visibleNode[1][2] = this.checkValid(vPos,'r'); //right
        visibleNode[2][1] = this.checkValid(vPos,'d'); //down
        visibleNode[1][0] = this.checkValid(vPos,'l'); //left
        visibleNode[0][0] = (visibleNode[0][1] || visibleNode[1][0]); //top left
        visibleNode[0][2] = (visibleNode[0][1] || visibleNode[1][2]); //top right
        visibleNode[2][2] = (visibleNode[2][1] || visibleNode[1][2]); //bottom right
        visibleNode[2][0] = (visibleNode[2][1] || visibleNode[1][0]); //bottom left
        if (visibleNode[0][0]) {
            boolean up = visibleNode[0][1];
            boolean left = visibleNode[1][0];
            if (up) {
                vPos[1] = iPos - 1;
                vPos[0] = jPos;
                up = checkValid(vPos,'l');
            }
            if (left) {
                vPos[1] = iPos;
                vPos[0] = jPos - 1;
                left = checkValid(vPos,'u');
            }
            visibleNode[0][0] = up || left;
        }
        if (visibleNode[0][2]) {
            boolean up = visibleNode[0][1];
            boolean right = visibleNode[1][2];
            if (up) {
                vPos[1] = iPos - 1;
                vPos[0] = jPos;
                up = checkValid(vPos,'r');
            }
            if (right) {
                vPos[1] = iPos;
                vPos[0] = jPos + 1;
                right = checkValid(vPos,'u');
            }
            visibleNode[0][2] = up || right;
        }
        if (visibleNode[2][2]) {
            boolean down = visibleNode[2][1];
            boolean right = visibleNode[1][2];
            if (down) {
                vPos[1] = iPos + 1;
                vPos[0] = jPos;
                down = checkValid(vPos,'r');
            }
            if (right) {
                vPos[1] = iPos;
                vPos[0] = jPos + 1;
                right = checkValid(vPos,'d');
            }
            visibleNode[2][2] = down || right;
        }
        if (visibleNode[2][0]) {
            boolean down = visibleNode[2][1];
            boolean left = visibleNode[1][0];
            if (down) {
                vPos[1] = iPos + 1;
                vPos[0] = jPos;
                down = checkValid(vPos,'l');
            }
            if (left) {
                vPos[1] = iPos;
                vPos[0] = jPos - 1;
                left = checkValid(vPos,'d');
            }
            visibleNode[2][0] = down || left;
        }

        for (int j = jPos - 1; j < jPos + 2; j++) {
            int arrJ = j + 1 - jPos;
            if (!visibleNode[0][arrJ]) {
                out += "+ ######## ";
            } else if (iPos < 2 || j < 0 || iPos > size + 1 || j > size -1) {
                out += "+ -------- ";
            } else {
                out += this.mazeArray[iPos - 2][j].dbarrier ? "+ -------- " : "+          ";
            }
        }
        out += "+\n";
        for (int i = iPos - 1; i < iPos + 2; i++){
            int arrI = i + 1 - iPos;
            if (!visibleNode[arrI][0]) {
                out += "#";
            } else if (i < 0 || jPos < 2 || i > size - 1 || jPos > size + 1) {
                out += "|";
            } else {
                out += this.mazeArray[i][jPos - 2].rbarrier ? "|" : " ";
            }
            for (int j = jPos - 1; j < jPos + 2; j++) {
                int arrJ = j + 1 - jPos;
                String str = " ";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Note") str = "N";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Chest") str = "C";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Mimic") str = "C";

                str = j == size - 1 && i == size - 1 ? "E" : str;
                if (!visibleNode[arrI][arrJ]) {
                    String ob = "#";
                    if (arrJ < 2 && visibleNode[arrI][arrJ + 1]) ob = (j < 0 || i < 0 || i > size - 1 || j > size -1) || this.mazeArray[i][j].rbarrier ? "|" : " ";
                    out += "##########" + ob;
                } else if (i < 0 || j < 0 || i > size - 1|| j > size - 1) {
                    out +=  " " + str + "      " + str + " |";
                } else {
                    out += this.mazeArray[i][j].rbarrier ? " " + str + "      " + str + " |" : " " + str + "      " + str + "  ";
                }
            }
            out += "\n";
            if (!visibleNode[arrI][0]) {
                out += "#";
            } else if (i < 0 || jPos < 2 || i > size - 1 || jPos > size + 1 ) {
                out += "|";
            } else {
                out += this.mazeArray[i][jPos - 2].rbarrier ? "|" : " ";
            }
            for (int j = jPos - 1; j < jPos + 2; j++) {
                int arrJ = j + 1 - jPos;
                String str = i == iPos && j == jPos ? avatar : "  ";
                if (!visibleNode[arrI][arrJ]) {
                    String ob = "#";
                    if (arrJ < 2 && visibleNode[arrI][arrJ + 1]) ob = (j < 0 || i < 0 || i > size - 1 || j > size -1) || this.mazeArray[i][j].rbarrier ? "|" : " ";
                    out += "##########" + ob;
                } else if (i < 0 || j < 0 || i > size - 1 || j > size - 1) {
                    out += "    " + str + "    |";
                } else {
                    out += this.mazeArray[i][j].rbarrier ? "    " + str + "    |" : "    " + str + "     ";
                }
            }
            out += "\n";
            if (!visibleNode[arrI][0]) {
                out += "#";
            } else if (i < 0 || jPos < 2 || i > size - 1 || jPos > size + 1) {
                out += "|";
            } else {
                out += this.mazeArray[i][jPos - 2].rbarrier ? "|" : " ";
            }
            for (int j = jPos - 1; j < jPos + 2; j++) {
                int arrJ = j + 1 - jPos;
                String str = " ";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Note") str = "N";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Chest") str = "C";
                if (i > -1 && j > -1 && i < size && j < size && mazeArray[i][j].nodeContents.id == "Mimic") str = "C";
                str = j == size - 1 && i == size - 1 ? "E" : str;
                if (!visibleNode[arrI][arrJ]) {
                    String ob = "#";
                    if (arrJ < 2 && visibleNode[arrI][arrJ + 1]) ob = (j < 0 || i < 0 || i > size - 1 || j > size -1) || this.mazeArray[i][j].rbarrier ? "|" : " " ;
                    out += "##########" + ob;
                } else if (i < 0 || j < 0 || i > size - 1 || j > size - 1) {
                    out += " " + str + "      " + str + " |";
                } else {
                    out += this.mazeArray[i][j].rbarrier ? " " + str + "      " + str + " |" : " " + str + "      " + str + "  ";
                }
            }
            out += "\n";
            for (int j = jPos - 1; j < jPos + 2; j++) {
                int arrJ = j + 1 - jPos;
                if (!visibleNode[arrI][arrJ]){
                    if (!(arrI < 2 && visibleNode[arrI + 1][arrJ])) {
                        out += "+ ######## ";
                        continue;
                    }
                }
                if (i < 0 || j < 0 || i > size - 1 || j > size - 1) {
                    out += "+ -------- ";
                } else {
                    out += this.mazeArray[i][j].dbarrier ? "+ -------- " : "+          ";
                }
            }
            out += "+\n";
        }

        return out;
    }

    public boolean isEnd(Player p) {
        int size = xaxis;
        int[] arr = {size - 1, size - 1};
        return arr[0] == p.getPos()[0] && arr[1] == p.getPos()[1];
    }

    public int getSize() {
        return yaxis;
    }

    public static void main(String[] args) {
        //Currently this is just diagnostic stuff. I wanted to be able to see how if the mazeArray and adjacency tables were being correctly spat out.
        int size = 5;
        Maze testMaze = new Maze(size);
        for (int i = 0; i < testMaze.adjTable.size(); i++) { //this is rigged up to print out all the valid pairs which ended up in the adjTable
            System.out.println("{"+(testMaze.adjTable.get(i).node1)+" , "+(testMaze.adjTable.get(i).node2) + "}" ); }
        System.out.println((testMaze.adjTable.size()));
        for (int j = 0; j < size; j++) {
            for (int g = 0; g < size; g++) { //this will print out the wall status of each node in the whole table
                System.out.println(testMaze.mazeArray[j][g].rbarrier + " "+testMaze.mazeArray[j][g].dbarrier +" "+testMaze.mazeArray[j][g].id); } }
    }
}
