package edu.vassar.cmpu203.dungeongame.view;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import edu.vassar.cmpu203.dungeongame.databinding.MainBinding;

public class MainView implements IMainView{

    private FragmentActivity activity;
    private MainBinding binding;

    public MainView(FragmentActivity activity) {
        this.activity = activity;
        this.binding = MainBinding.inflate(activity.getLayoutInflater());
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment) {

        this.activity.getSupportFragmentManager()
                .beginTransaction()
                    .replace(this.binding.fragmentContainerView.getId(), fragment)
                    .commitNow();
    }
}
