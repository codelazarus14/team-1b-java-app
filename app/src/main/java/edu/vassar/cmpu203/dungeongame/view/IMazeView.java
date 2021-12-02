package edu.vassar.cmpu203.dungeongame.view;

import edu.vassar.cmpu203.dungeongame.model.Interactable;

public interface IMazeView {
    interface Listener {
        void onPlayerMoveInput(char dir, IMazeView mazeView);
        void onPlayerInteract(IMazeView mazeView);
        void onResetMaze(IMazeView mazeView);
    }

    void updateMaze(String mazeText);

    void setMazeSuccessConfiguration();

    void onInteraction(Interactable interactable);
}
