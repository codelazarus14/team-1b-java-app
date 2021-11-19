package edu.vassar.cmpu203.dungeongame.view;

public interface IMazeView {
    interface Listener {
        void onPlayerMoveInput(char dir, IMazeView mazeView);

        void onResetMaze(IMazeView mazeView);
    }

    void updateMaze(String mazeText);

    void setMazeSuccessConfiguration();
}
