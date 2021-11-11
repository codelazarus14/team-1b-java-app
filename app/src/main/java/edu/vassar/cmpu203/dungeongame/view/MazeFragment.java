package edu.vassar.cmpu203.dungeongame.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.databinding.FragmentMazeBinding;

public class MazeFragment extends Fragment implements IMazeView {

    private FragmentMazeBinding binding;
    private String mazeText;
    private Listener listener;

    public MazeFragment(Listener listener, String mazeText) {
        // Required empty public constructor
        this.mazeText = mazeText;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentMazeBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateMaze(mazeText);

        this.binding.upArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('u', MazeFragment.this));
        this.binding.downArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('d', MazeFragment.this));
        this.binding.leftArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('l', MazeFragment.this));
        this.binding.rightArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('r', MazeFragment.this));
    }

    @Override
    public void updateMaze(String mazeText) {
        Log.i("DungeonGame", "updating maze view");
        this.binding.mazeView.setText(mazeText);
    }
}