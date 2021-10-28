package edu.vassar.cmpu203.dungeongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.vassar.cmpu203.dungeongame.view.IMazeViewMvc;

public class MainActivity extends AppCompatActivity implements IMazeViewMvc.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPlayerMoveInput(char dir) {
        //p.updatePos(dir, m);
    }

}