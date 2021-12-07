package edu.vassar.cmpu203.dungeongame.model;

import java.io.Serializable;

public class Player implements Serializable {
    //position array, has x and y values for position
    int[] pos = new int[2];
    Chest chestRef = new Chest();

    /**
     * The Chest stores the possible different kinds of loot in an array and the positions of those
     * things in that array will directly correspond to the position in the inventory array
     * which will store the quantity of said items in the corresponding position
     */
    public int[] inventory = new int[chestRef.loot.length];
    public int notes = 0; //stores the amount of notes found by the player

    //default constructor
    public Player() {}

    //constructor
    public Player(int a, int b) {
        this.pos[0] = a;
        this.pos[1] = b;
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = 0;
        }
    }

    public void openObject(Maze m){
        /**
         * Case 1: The item is a chest. Adds the quantity to the appropriate slot in the inventory
         * and then empties the chest.
         * Case 2: The item is a note. Adds the quantity to the notes variable and marks it as having
         * been read so that you cannot spam
         * Case 3: It's a Mimic (ayo) and a random amount of a random item is taken by the little bastard
         *
         * In each case, after the Interactable has been interacted with, it will no longer be
         * interactable-able.
         */
        if (!m.mazeArray[pos[1]][pos[0]].nodeContents.accessed) {
            if (m.mazeArray[pos[1]][pos[0]].nodeContents instanceof Chest) {
                ((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).accessed = true;
                inventory[((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).itemType] +=
                        ((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).itemQuantity;
                ((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).itemQuantity = 0;

            }
            if (m.mazeArray[pos[1]][pos[0]].nodeContents instanceof Note) {
                notes += 1;
                m.mazeArray[pos[1]][pos[0]].nodeContents.accessed = true;
            }
            if (m.mazeArray[pos[1]][pos[0]].nodeContents instanceof Mimic) {
                int check = -1;
                for (int item: inventory) {
                    if (item < 2) continue;
                    check = 0;
                }
                if (check == -1) return;
                int randomItem = (int) (Math.random() * inventory.length);
                while (inventory[randomItem] < 2){
                    randomItem = (int) (Math.random() * inventory.length);
                }
                int randomQuantity = (int) (Math.random() * inventory[randomItem]);
                inventory[randomItem] -= randomQuantity;
                ((Mimic) m.mazeArray[pos[1]][pos[0]].nodeContents).bodyText =
                        "You lost: " + chestRef.loot[randomItem] + " x" + Integer.toString(randomQuantity);
                m.mazeArray[pos[1]][pos[0]].nodeContents.accessed = true;
            }
        }
    }

    //accessor method
    public int[] getPos(){
        return this.pos;
    }

    public void updatePos(char dir, Maze m) {
        try {
            switch (dir) {
                case 'r':
                    if (m.checkValid(pos, dir)) {
                        pos[0]++;
                    }
                    break;
                case 'l':
                    if (m.checkValid(pos, dir)) {
                        pos[0]--;
                    }
                    break;
                case 'd':
                    if (m.checkValid(pos, dir)) {
                        pos[1]++;
                    }
                    break;
                case 'u':
                    if (m.checkValid(pos, dir)) {
                        pos[1]--;
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}