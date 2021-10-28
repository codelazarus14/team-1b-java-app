package edu.vassar.cmpu203.dungeongame.model;

public class GameController {
    public GameController() {}

    public void handleMovement(Player p, Maze m, char dir) {
        p.updatePos(dir, m);
    }
}
