package edu.vassar.cmpu203.dungeongame.view;

import android.view.View;

public interface IMazeViewMvc {

    interface Listener{
        void onPlayerMoveInput(char dir);
    }

    View getRootView();

    void updateMaze(String mazeText);

}