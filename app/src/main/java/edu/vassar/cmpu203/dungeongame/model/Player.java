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
    int[] inventory = new int[chestRef.loot.length];
    int notes = 0; //stores the amount of notes found by the player

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
        if (m.mazeArray[pos[1]][pos[0]].nodeContents.accessed == false) {
            if (m.mazeArray[pos[1]][pos[0]].nodeContents instanceof Chest) {
                inventory[((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).itemType] +=
                        ((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).itemQuantity;
                ((Chest) m.mazeArray[pos[1]][pos[0]].nodeContents).itemQuantity = 0;
                m.mazeArray[pos[1]][pos[0]].nodeContents.accessed = true;
            }
            if (m.mazeArray[pos[1]][pos[0]].nodeContents instanceof Note) {
                notes += 1;
                m.mazeArray[pos[1]][pos[0]].nodeContents.accessed = true;
            }
            if (m.mazeArray[pos[1]][pos[0]].nodeContents instanceof Mimic) {
                int randomItem = (int) (Math.random() * inventory.length);
                while (inventory[randomItem] < 2){
                    randomItem = (int) (Math.random() * inventory.length);
                }
                int randomQuantity = (int) (Math.random() * inventory[randomItem]);
                inventory[randomItem] -= randomQuantity;
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