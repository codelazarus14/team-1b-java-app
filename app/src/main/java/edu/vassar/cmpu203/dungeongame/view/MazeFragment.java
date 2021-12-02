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
import edu.vassar.cmpu203.dungeongame.model.Interactable;

public class MazeFragment extends Fragment implements IMazeView {

    private FragmentMazeBinding binding;
    private String mazeText;
    private static final String MAZE_TEXT = "mazeText";
    private boolean isComplete = false;
    private static final String LEVEL_COMPLETE = "levelComplete";
    private Listener listener;

    public MazeFragment(Listener listener) {
        this.listener = listener;
    }

    public static Bundle makeArgsBundle(String mazeText) {
        Bundle args = new Bundle();
        args.putString(MAZE_TEXT, mazeText);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        if (args != null) this.mazeText = args.getString(MAZE_TEXT);
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

        this.binding.interactbutton.setOnClickListener(clickedView ->
                listener.onPlayerInteract(MazeFragment.this));
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
        Log.d("DungeonGame","isComplete? " + isComplete);
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
        this.isComplete = true;
    }

    @Override
    public void onInteraction(Interactable interactable) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setMessage(interactable.bodyText)
                .setTitle(interactable.titleText);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LEVEL_COMPLETE, this.isComplete);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) this.isComplete = savedInstanceState.getBoolean(LEVEL_COMPLETE);
        Log.i("DungeonGame","savedInstanceState = " + savedInstanceState);

        if (this.isComplete) this.setMazeSuccessConfiguration();
    }
}