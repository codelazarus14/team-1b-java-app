package edu.vassar.cmpu203.dungeongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.view.IMazeViewMvc;
import edu.vassar.cmpu203.dungeongame.view.MazeViewMvc;

public class MainActivity extends AppCompatActivity implements IMazeViewMvc.Listener {
    private IMazeViewMvc mazeViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mazeViewMvc = new MazeViewMvc(getApplicationContext(), this);
        setContentView(this.mazeViewMvc.getRootView());
    }

    @Override
    public void onPlayerMoveInput(char dir) {
        Log.i("DungeonGame", "controller received player move, handling");
        //p.updatePos(dir, m);
    }

}