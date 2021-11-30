package edu.vassar.cmpu203.dungeongame.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import edu.vassar.cmpu203.dungeongame.view.MazeFragment;
import edu.vassar.cmpu203.dungeongame.view.MenuFragment;

public class DungeonGameFragFactory extends FragmentFactory {

    private ControllerActivity controller;

    public DungeonGameFragFactory(ControllerActivity controller) {
        super();
        this.controller = controller;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);

        Fragment fragment;
        if (fragmentClass == MazeFragment.class)
            fragment = new MazeFragment(controller);
        else if (fragmentClass == MenuFragment.class)
            fragment = new MenuFragment(controller);
        else fragment = super.instantiate(classLoader, className);

        return fragment;
    }
}