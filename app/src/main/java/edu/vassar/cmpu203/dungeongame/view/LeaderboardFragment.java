package edu.vassar.cmpu203.dungeongame.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.dungeongame.R;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentLeaderboardBinding;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentMazeBinding;

public class LeaderboardFragment extends Fragment implements ILeaderboardView {

    private FragmentLeaderboardBinding binding;
    private Listener listener;

    private static final String LEADERBOARD_TEXT = "lbText";
    private String lbText;

    public LeaderboardFragment(Listener listener) {
        this.listener = listener;
    }

    public static Bundle makeArgsBundle(String lbText) {
        Bundle args = new Bundle();
        args.putString(LEADERBOARD_TEXT, lbText);
        return args;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentLeaderboardBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.binding.nameConfirmButton.setOnClickListener(clickedView -> {
            Editable nameEditable = binding.nameEditText.getText();
            String name = nameEditable.toString();

            nameEditable.clear();
            listener.onPlayerNameInput(name,LeaderboardFragment.this);
        });

        this.binding.returnToMenuButton.setOnClickListener(clickedView ->
                listener.onReturnToMenu(LeaderboardFragment.this));
    }

    @Override
    public void updateLeaderboardView(String lbText) {
        String newlbText =  this.binding.leaderboardView.getText().toString() + "\n" + lbText;
        this.binding.leaderboardView.setText(newlbText);
    }
}