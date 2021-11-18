package edu.vassar.cmpu203.dungeongame.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.dungeongame.databinding.FragmentMazeBinding;
import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.model.Player;
import edu.vassar.cmpu203.dungeongame.view.IMainView;
import edu.vassar.cmpu203.dungeongame.view.IMazeView;
import edu.vassar.cmpu203.dungeongame.view.IMenuView;
import edu.vassar.cmpu203.dungeongame.view.MainView;
import edu.vassar.cmpu203.dungeongame.view.MazeFragment;
import edu.vassar.cmpu203.dungeongame.view.MenuFragment;

public class ControllerActivity extends AppCompatActivity implements IMazeView.Listener, IMenuView.Listener {

    private Maze maze;
    private IMainView mainView;
    private Player p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainView = new MainView((this));

        setContentView(this.mainView.getRootView());

        this.mainView.displayFragment(new MenuFragment(this));
    }

    @Override
    public void onPlayerMoveInput(char dir, IMazeView mazeView) {
        Log.i("DungeonGame", "controller received player move, handling: " + dir);
        p.updatePos(dir, maze);
        int[] playerPos = p.getPos();
        Log.i("DungeonGame", "new player position is " + playerPos[0] + "," + playerPos[1]);
        mazeView.updateMaze(this.maze.toObscuredString(p));
    }

    //:[
    public void onPlayerMoveInput(char dir) {
        Log.i("DungeonGame", "controller received player move, handling: " + dir);
        p.updatePos(dir, maze);
        int[] playerPos = p.getPos();
        Log.i("DungeonGame", "new player position is " + playerPos[0] + "," + playerPos[1]);
        this.mainView.displayFragment(new MazeFragment(this, maze.toObscuredString(p)));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
            case KeyEvent.KEYCODE_I:
            case KeyEvent.KEYCODE_DPAD_UP:
                this.onPlayerMoveInput('u');
                break;
            case KeyEvent.KEYCODE_A:
            case KeyEvent.KEYCODE_J:
            case KeyEvent.KEYCODE_DPAD_LEFT:
                this.onPlayerMoveInput('l');
                break;
            case KeyEvent.KEYCODE_S:
            case KeyEvent.KEYCODE_K:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                this.onPlayerMoveInput('d');
                break;
            case KeyEvent.KEYCODE_D:
            case KeyEvent.KEYCODE_L:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                this.onPlayerMoveInput('r');
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStartGame() {
        //TODO - bundle arguments and pass to MazeFragment
        Log.i("DungeonGame", "controller onStartGame()");

        this.maze = new Maze(8);
        this.p = new Player(0,0);
        Fragment f = new MazeFragment(this, maze.toObscuredString(p));
        this.mainView.displayFragment(f);
    }
}
