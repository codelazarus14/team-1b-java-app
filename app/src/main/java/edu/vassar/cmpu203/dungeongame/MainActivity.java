package edu.vassar.cmpu203.dungeongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.model.Player;
import edu.vassar.cmpu203.dungeongame.view.IMazeViewMvc;
import edu.vassar.cmpu203.dungeongame.view.MazeViewMvc;



public class MainActivity extends AppCompatActivity implements IMazeViewMvc.Listener {
    private IMazeViewMvc mazeViewMvc;
    private Maze maze;
    private Player p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maze = new Maze(5);
        p = new Player(0,0);
        this.mazeViewMvc = new MazeViewMvc(getApplicationContext(), this, maze.toString(p));
        setContentView(this.mazeViewMvc.getRootView());
    }

    @Override
    public void onPlayerMoveInput(char dir) {
        Log.i("DungeonGame", "controller received player move, handling");
//        p.updatePos(dir, maze);
//        int[] playerPos = p.getPos();
//        Log.i("DungeonGame", "new player position is " + playerPos[0] + "," + playerPos[1]);
//        mazeViewMvc.updateMaze(maze.toString(p));
    }

}