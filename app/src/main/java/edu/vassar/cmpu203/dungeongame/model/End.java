package edu.vassar.cmpu203.dungeongame.model;

/**
 * This is the interactable which will be placed at the "end" of the maze, for the
 * player to interact with and then either end the game or proceed further
 */

public class End extends Interactable {
    public End() {
        super.accessed = false;
        super.id = "End";
    }
}
