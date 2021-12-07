package edu.vassar.cmpu203.dungeongame.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.dungeongame.R;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentLeaderboardBinding;
import edu.vassar.cmpu203.dungeongame.databinding.FragmentMazeBinding;

public class LeaderboardFragment extends Fragment implements ILeaderboardView{

    private FragmentLeaderboardBinding binding;
    private Listener listener;

    public LeaderboardFragment(Listener listener) {
        this.listener = listener;
    }

    public static Bundle makeArgsBundle(String mazeText) {
        Bundle args = new Bundle();
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        //if (args != null)

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

    }
}