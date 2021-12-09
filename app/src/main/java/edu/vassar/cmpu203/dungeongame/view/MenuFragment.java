package edu.vassar.cmpu203.dungeongame.view;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.R;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment implements IMenuView {

    private Listener listener;
    private FragmentMenuBinding binding;

    private static final int EASY_SIZE = 5;
    private static final int MED_SIZE = 8;
    private static final int HARD_SIZE = 12;

    //default constructor
    public MenuFragment(IMenuView.Listener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentMenuBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Spinner spinner = this.binding.difficultySpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this.getContext(), R.array.difficulties, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        this.binding.startButton.setOnClickListener( (clickedView) -> {
            String diffStr = spinner.getSelectedItem().toString();
            String[] difficulties = getResources().getStringArray(R.array.difficulties);
            int startSize;
            if (diffStr.equals(difficulties[0]))
                startSize = EASY_SIZE;
            else if (diffStr.equals(difficulties[1]))
                startSize = MED_SIZE;
            else
                startSize = HARD_SIZE;
            this.listener.onStartGame(startSize);
        });
    }
}

