package edu.vassar.cmpu203.dungeongame.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.databinding.FragmentMazeBinding;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment implements IMenuView {

    private IMenuView.Listener listener;
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
        this.binding.startButton.setOnClickListener( (clickedView) -> {
            this.listener.onMenuInput();
            }
        );
        }


    }

