package edu.vassar.cmpu203.dungeongame.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DisjointSetsTest {

    @Test
    void addPair() {
        Pair testPair = new Pair(0, 1);
        DisjointSets testDJS = new DisjointSets();
        assertTrue(testDJS.addPair(testPair), "Add Pair Error!");
    }
}