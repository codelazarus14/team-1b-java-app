package edu.vassar.cmpu203.dungeongame.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

class PlayerTest {

    @Test
    void getPos() {
        Player testPlayer = new Player();
        int pos[] = {0, 0};
        assertEquals(pos[0] ,testPlayer.getPos()[0], "Player Position Error!");
        assertEquals(pos[1], testPlayer.getPos()[1], "Player Position Error!");
    }

    @Test
    void updatePos() {
        Maze testMaze = new Maze(4);
        Player testPlayer = new Player();
        int[] pos = {0, 0};
        if (testMaze.checkValid(testPlayer.getPos(), 'd')) pos[1]++;
        testPlayer.updatePos('d',testMaze);
        assertEquals(pos[0], testPlayer.getPos()[0], "Update Position Error");
        assertEquals(pos[1], testPlayer.getPos()[1], "Update Position Error");
        if (testMaze.checkValid(testPlayer.getPos(), 'u')) pos[1]--;
        testPlayer.updatePos('u',testMaze);
        assertEquals(pos[0], testPlayer.getPos()[0], "Update Position Error");
        assertEquals(pos[1], testPlayer.getPos()[1], "Update Position Error");
        if (testMaze.checkValid(testPlayer.getPos(), 'r')) pos[0]++;
        testPlayer.updatePos('r',testMaze);
        assertEquals(pos[0], testPlayer.getPos()[0], "Update Position Error");
        assertEquals(pos[1], testPlayer.getPos()[1], "Update Position Error");
        if (testMaze.checkValid(testPlayer.getPos(), 'r')) pos[0]--;
        testPlayer.updatePos('l',testMaze);
        assertEquals(pos[0], testPlayer.getPos()[0], "Update Position Error");
        assertEquals(pos[1], testPlayer.getPos()[1], "Update Position Error");
    }
}