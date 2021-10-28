package edu.vassar.cmpu203.dungeongame.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import edu.vassar.cmpu203.dungeongame.databinding.ActivityMainBinding;

public class MazeViewMvc implements IMazeViewMvc{
    private ActivityMainBinding binding;
    private Listener listener;

    public MazeViewMvc(Context context, Listener listener) {
        this.listener = listener;
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));

        //add listeners to controls widgets (swiping/arrows)
        this.binding.upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlayerMoveInput('u');
            }
        });

    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }
}
