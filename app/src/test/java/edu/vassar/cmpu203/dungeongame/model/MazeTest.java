package edu.vassar.cmpu203.dungeongame.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MazeTest {

    @Test
    void checkValid() {
        Maze testMaze = new Maze(4);
        Player testPlayer = new Player();
        assertEquals(false, testMaze.checkValid(testPlayer.getPos(),'u'), "Move Error!");
        assertEquals(false, testMaze.checkValid(testPlayer.getPos(),'l'), "Move Error!");
        assertEquals(true, testMaze.checkValid(testPlayer.getPos(),'d') || testMaze.checkValid(testPlayer.getPos(),'r'), "Move Error!");
    }

    @Test
    void toObscuredString() {
        Maze testMaze = new Maze(4);
        Player testPlayer = new Player();
        assertEquals(442, testMaze.toObscuredString(testPlayer).length(), "Display Error!");
        for (int i = 0; i < 13; i += 4) {
            for (int j = 0; j < 34; j += 11) {
                assertEquals('+', testMaze.toObscuredString(testPlayer).charAt((i * 34) + j), "Display Error!");
            }
        }
    }
}