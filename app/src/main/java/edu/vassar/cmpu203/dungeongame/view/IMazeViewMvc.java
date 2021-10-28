package edu.vassar.cmpu203.dungeongame.view;

import android.view.View;

public interface IMazeViewMvc {

    public interface Listener{
        void onPlayerMoveInput(char dir);
    }

    public View getRootView();

}