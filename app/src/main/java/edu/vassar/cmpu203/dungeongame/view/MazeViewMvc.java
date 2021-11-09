package edu.vassar.cmpu203.dungeongame.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import edu.vassar.cmpu203.dungeongame.databinding.ActivityMainBinding;
import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.model.Player;

public class MazeViewMvc implements IMazeViewMvc{
    private ActivityMainBinding binding;
    private Listener listener;

    public MazeViewMvc(Context context, Listener listener, String mazeText) {
        this.listener = listener;
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));
        this.updateMaze(mazeText);

        //add listeners to controls widgets (swiping/arrows)
        this.binding.upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayerMoveInput('u');
            }
        });
        this.binding.downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayerMoveInput('d');
            }
        });
        this.binding.leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayerMoveInput('l');
            }
        });
        this.binding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayerMoveInput('r');
            }
        });

    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }

    @Override
    public void updateMaze(String mazeText){
        Log.i("DungeonGame", "updating maze view");
        this.binding.mazeView.setText(mazeText);

    }

    // will want to create new updateMaze method that takes in a position,
    // renders player on top of maze instead of copying entire maze + player
}
