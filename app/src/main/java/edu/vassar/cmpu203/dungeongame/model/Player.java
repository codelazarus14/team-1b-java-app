package edu.vassar.cmpu203.dungeongame.model;

import java.io.Serializable;

public class Player implements Serializable {
    //position array, has x and y values for position
    int[] pos = new int[2];

    //default constructor
    public Player() {}

    //constructor
    public Player(int a, int b) {
        this.pos[0] = a;
        this.pos[1] = b;
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