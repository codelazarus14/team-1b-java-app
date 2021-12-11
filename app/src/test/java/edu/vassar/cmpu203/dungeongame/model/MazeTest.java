package edu.vassar.cmpu203.dungeongame.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MazeTest {

    @Test
    void checkValid() {
        Maze testMaze = new Maze(4);
        Player testPlayer = new Player();
        assertFalse(testMaze.checkValid(testPlayer.getPos(),'u'), "CheckValid Error!");
        assertFalse(testMaze.checkValid(testPlayer.getPos(),'l'), "CheckValid Error!");
        assertTrue(testMaze.checkValid(testPlayer.getPos(),'d') || testMaze.checkValid(testPlayer.getPos(),'r'), "CheckValid Error!");
    }

/*    @Test
    void toString() {
        Maze testMaze = new Maze(4);
        assertEquals(189, testMaze.toString().length(), "toString Error");
        for (int i = 0; i < 9; i+=2) {
            for (int j = 0; j < 21; j+=5) {
                assertEquals('+', testMaze.toString().charAt((i*21)+j), "toStringError");
            }
        }
    }*/

    @Test
    void toObscuredString() {
        Maze testMaze = new Maze(4);
        Player testPlayer = new Player();
        assertEquals(455, testMaze.toObscuredString(testPlayer).length(), "Display Error!");
    }

    @Test
    void isEnd() {
        Maze testMaze = new Maze(4);
        Player testPlayer = new Player();
        assertFalse(testMaze.isEnd(testPlayer), "Maze Ending Error");
    }
}