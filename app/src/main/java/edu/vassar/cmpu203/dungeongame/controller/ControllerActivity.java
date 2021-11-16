package edu.vassar.cmpu203.dungeongame.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.model.Player;
import edu.vassar.cmpu203.dungeongame.view.IMainView;
import edu.vassar.cmpu203.dungeongame.view.IMazeView;
import edu.vassar.cmpu203.dungeongame.view.MainView;
import edu.vassar.cmpu203.dungeongame.view.MazeFragment;

public class ControllerActivity extends AppCompatActivity implements IMazeView.Listener {

    private Maze maze;
    private IMainView mainView;
    private Player p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.maze = new Maze(8);
        this.p = new Player(0,0);
        this.mainView = new MainView((this));

        setContentView(this.mainView.getRootView());

        this.mainView.displayFragment(new MazeFragment(this, maze.toObscuredString(p)));
    }

    @Override
    public void onPlayerMoveInput(char dir, IMazeView mazeView) {
        Log.i("DungeonGame", "controller received player move, handling: " + dir);
        p.updatePos(dir, maze);
        int[] playerPos = p.getPos();
        Log.i("DungeonGame", "new player position is " + playerPos[0] + "," + playerPos[1]);
        mazeView.updateMaze(this.maze.toObscuredString(p));
    }
}
