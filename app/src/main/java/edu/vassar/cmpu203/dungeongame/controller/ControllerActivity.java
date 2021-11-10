package edu.vassar.cmpu203.dungeongame.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.view.IMainView;
import edu.vassar.cmpu203.dungeongame.view.MainView;
import edu.vassar.cmpu203.dungeongame.view.MazeFragment;

public class ControllerActivity extends AppCompatActivity {

    private Maze maze;
    private IMainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new MainView((this));
        setContentView(this.mainView.getRootView());
        this.mainView.displayFragment(new MazeFragment());
    }
}
