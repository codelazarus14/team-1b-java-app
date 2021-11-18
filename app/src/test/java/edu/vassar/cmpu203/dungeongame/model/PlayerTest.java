package edu.vassar.cmpu203.dungeongame.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void getPos() {
        Player testPlayer = new Player();
        int Pos[] = new int[2];
        Pos[0] = 0;
        Pos[1] = 0;
        assertEquals(Pos ,testPlayer.getPos(), "Player Position Error!");
    }

    @Test
    void updatePos() {
    }
}