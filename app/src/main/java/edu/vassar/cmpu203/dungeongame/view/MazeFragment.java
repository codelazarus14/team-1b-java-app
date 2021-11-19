package edu.vassar.cmpu203.dungeongame.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.R;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = FragmentMazeBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        updateMaze(mazeText);

        this.binding.upArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('u', MazeFragment.this));
        this.binding.downArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('d', MazeFragment.this));
        this.binding.leftArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('l', MazeFragment.this));
        this.binding.rightArrow.setOnClickListener(clickedView ->
                listener.onPlayerMoveInput('r', MazeFragment.this));

        this.binding.resetButton.setOnClickListener(clickedView ->
                listener.onResetMaze(MazeFragment.this));
    }

    @Override
    public void updateMaze(String mazeText) {
        Log.i("DungeonGame", "updating maze view");
        this.binding.mazeView.setText(mazeText);
    }

    @Override
    public void setMazeSuccessConfiguration() {
        //disable controls
        this.binding.downArrow.setEnabled(false);
        this.binding.upArrow.setEnabled(false);
        this.binding.leftArrow.setEnabled(false);
        this.binding.rightArrow.setEnabled(false);
        //create alert dialog and show it
        //TODO - create a proper transition fragment which loads when this dialog is cancelled
        // may also require making a proper fragment for the dialog itself
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setMessage(R.string.maze_complete_text)
                .setTitle(R.string.maze_complete_title);
        AlertDialog dialog = builder.create();
        dialog.show();
        //show reset button
        this.binding.resetButton.setVisibility(View.VISIBLE);
    }
}