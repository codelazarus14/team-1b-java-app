package edu.vassar.cmpu203.dungeongame.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.model.Player;
import edu.vassar.cmpu203.dungeongame.model.Chest;
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
    private Chest c = new Chest();
    private static final String MAZE = "maze";
    private static final String PLAYER = "player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set custom fragment factory
        FragmentFactory fragFactory = new DungeonGameFragFactory(this);
        this.getSupportFragmentManager()
                .setFragmentFactory(fragFactory);

        super.onCreate(savedInstanceState);

        Log.i("DungeonGame", "onCreate activity");

        this.mainView = new MainView((this));

        // check if we're already mid-game to get preserved objects
        if (savedInstanceState == null) {
            this.maze = new Maze(8);
            this.p = new Player(0, 0);
        } else {
            this.maze = (Maze) savedInstanceState.getSerializable(MAZE);
            this.p = (Player) savedInstanceState.getSerializable(PLAYER);
            Log.d("DungeonGame", "Player: " + p);
            assert(this.p != null);
            assert(this.maze != null);
        }

        setContentView(this.mainView.getRootView());

        if (savedInstanceState == null)
            this.mainView.displayFragment(new MenuFragment(this));
    }

    @Override
    public void onPlayerMoveInput(char dir, IMazeView mazeView) {
        Log.i("DungeonGame", "controller received player move, handling: " + dir);
        p.updatePos(dir, maze);
        int[] playerPos = p.getPos();
        Log.i("DungeonGame", "new player position is " + playerPos[0] + "," + playerPos[1]);
        mazeView.updateMaze(this.maze.toObscuredString(p));
        if (maze.isEnd(p)) this.onEnd(mazeView);
    }


    @Override
    public void onPlayerInteract(IMazeView mazeView) {
        Log.i("DungeonGame", "controller received player interaction, handling: ");
        p.openObject(maze);
    }


    @Override
    public void onResetMaze(IMazeView mazeView) {
        //currently this just recreates maze fragment, should be called during maze transition
        //TODO

        //this is here because the maze/player won't reset otherwise
        this.maze = new Maze(8);
        this.p = new Player(0, 0);

        this.onStartGame();
    }

    //:[
    public void onPlayerMoveInput(char dir) {
        Log.i("DungeonGame", "controller received player move, handling: " + dir);
        p.updatePos(dir, maze);
        int[] playerPos = p.getPos();
        Log.i("DungeonGame", "new player position is " + playerPos[0] + "," + playerPos[1]);
        // bundle args and instantiate new fragment
        String mazeText = maze.toObscuredString(p);
        Bundle fragArgs = MazeFragment.makeArgsBundle(mazeText);
        Fragment mazeFrag = new MazeFragment(this);
        mazeFrag.setArguments(fragArgs);
        this.mainView.displayFragment(mazeFrag);

        // I commented this out because this method is missing the view parameter
        // so you can't access it when you need to show the dialog/reset button in the fragment
        //if (maze.isEnd(p)) this.onEnd();
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
        Log.i("DungeonGame", "controller onStartGame()");

        //bundle args and instantiate new fragment
        String mazeText = maze.toObscuredString(p);
        Bundle fragArgs = MazeFragment.makeArgsBundle(mazeText);
        Fragment mazeFrag = new MazeFragment(this);
        mazeFrag.setArguments(fragArgs);

        this.mainView.displayFragment(mazeFrag);
    }
    public void onEnd(IMazeView mazeView) {
        //TODO - trigger method in mazeFragment as below, but then swap to leaderboard
        Log.i("DungeonGame", "congratulations");
        mazeView.setMazeSuccessConfiguration();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MAZE, this.maze);
        outState.putSerializable(PLAYER, this.p);
    }
}
