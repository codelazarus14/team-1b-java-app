package edu.vassar.cmpu203.dungeongame.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.databinding.FragmentLeaderboardBinding;

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
    public void updateEntries(String entryText) {
        String newlbText =  this.binding.leaderboardView.getText().toString() + "\n" + entryText;
        this.binding.leaderboardView.setText(newlbText);
    }

    @Override
    public void setAddedEntryConfiguration() {
        EditText nameEditText = this.binding.nameEditText;
        nameEditText.setVisibility(View.INVISIBLE);
        Button nameConfirmButton = this.binding.nameConfirmButton;
        nameConfirmButton.setVisibility(View.INVISIBLE);
        InputMethodManager mgr = (InputMethodManager) this.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
    }
}