package edu.vassar.cmpu203.dungeongame.view;

import androidx.fragment.app.Fragment;

public interface IMenuView {
    interface Listener {
        void menuInput(char menuInput);
    }
    void displayFragment(Fragment fragment);
}
