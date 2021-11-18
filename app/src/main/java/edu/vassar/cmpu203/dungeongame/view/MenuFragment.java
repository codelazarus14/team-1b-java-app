package edu.vassar.cmpu203.dungeongame.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.R;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment implements IMenuView {

    private Listener listener;
    private FragmentMenuBinding binding;

    //default constructor
    public MenuFragment(IMenuView.Listener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentMenuBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Spinner spinner = this.binding.difficultySpinner;
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.difficulties, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        //TODO : have working spinner to capture player input
        spinner.setVisibility(View.INVISIBLE);

        this.binding.startButton.setOnClickListener( (clickedView) -> this.listener.onStartGame());
    }
}

