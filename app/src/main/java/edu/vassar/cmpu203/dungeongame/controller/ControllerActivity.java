package edu.vassar.cmpu203.dungeongame.controller;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import edu.vassar.cmpu203.dungeongame.R;
import edu.vassar.cmpu203.dungeongame.model.Chest;
import edu.vassar.cmpu203.dungeongame.model.Interactable;
import edu.vassar.cmpu203.dungeongame.model.Maze;
import edu.vassar.cmpu203.dungeongame.model.Node;
import edu.vassar.cmpu203.dungeongame.model.Player;
import edu.vassar.cmpu203.dungeongame.view.ILeaderboardView;
import edu.vassar.cmpu203.dungeongame.view.IMainView;
import edu.vassar.cmpu203.dungeongame.view.IMazeView;
import edu.vassar.cmpu203.dungeongame.view.IMenuView;
import edu.vassar.cmpu203.dungeongame.view.LeaderboardFragment;
import edu.vassar.cmpu203.dungeongame.view.MainView;
import edu.vassar.cmpu203.dungeongame.view.MazeFragment;
import edu.vassar.cmpu203.dungeongame.view.MenuFragment;

public class ControllerActivity extends AppCompatActivity implements IMazeView.Listener, IMenuView.Listener, ILeaderboardView.Listener {

    private Maze maze;
    private IMainView mainView;
    private ILeaderboardView leaderboardView;
    private Player p;
    private Chest c = new Chest();
    private static final String MAZE = "maze";
    private static final String PLAYER = "player";
    private MediaPlayer mediaPlayer;
    private boolean firstOpen = true;

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
//        if (maze.isEnd(p)) this.onEnd(mazeView);
    }


    @Override
    public void onPlayerInteract(IMazeView mazeView) {
        Node n = this.maze.mazeArray[p.getPos()[1]][p.getPos()[0]];
        Interactable interactable = n.nodeContents;
        if (interactable.id == "Nothing") return;
        if (interactable.id == "End") {
            if (maze.isEnd(p)) this.onMazeComplete(mazeView);
            return;
        }
        Log.i("DungeonGame", "controller received player interaction, handling: ");
        p.openObject(maze);
        mazeView.onInteraction(interactable);
    }

    @Override
    public void onInventoryOpen(IMazeView mazeView) {
        String inventoryString = "";
        for (int i = 0; i < c.loot.length;i++) {
            if (p.inventory[i] > 0) {
                inventoryString += c.loot[i] + ": x" + p.inventory[i] + "\n";}
        }
        if (p.notes > 0) {
            inventoryString += "Notes: x" + p.notes; }
        if (inventoryString == "") {
            // this uses strings.xml to be more easily testable
            inventoryString = getResources().getString(R.string.inventory_default_text);
        }
        Interactable workAround = new Interactable();
        workAround.bodyText = inventoryString;
        workAround.titleText = "Inventory";
        mazeView.onInteraction(workAround);
    }


    @Override
    public void onLoadNextMaze(IMazeView mazeView) {
        //preserve player data, move to start and generate new maze
        int[] savedInventory = p.inventory;
        int savedNotes = p.notes;
        int savedMazeScore = p.mazeScore;
        this.p = new Player(0, 0);
        p.inventory = savedInventory;
        p.notes = savedNotes;
        p.mazeScore = savedMazeScore;
        this.onStartGame(maze.getSize() + (int)(Math.random() * 3 + 1));
    }

    @Override
    public void onGameOver(IMazeView mazeView) {
        Log.i("DungeonGame", "game over, switching to leaderboard");

        //TODO - uncomment once leaderboard is implemented/remove if we're not
        // showing the leaderboard before entering their name
//        String lbText = this.leaderboardView.nameEditText
//        Bundle fragArgs = LeaderboardFragment.makeArgsBundle(lbText);
        Fragment lbFrag = new LeaderboardFragment(this);
//        lbFrag.setArguments(fragArgs);
//
        this.mainView.displayFragment(lbFrag);

        stopMusic();
    }

    @Override
    public void onPlayerNameInput(String name, ILeaderboardView leaderboardView) {
        //TODO - add player name + score to leaderboard and call
        // leaderboardView.updateLeaderboardView(leaderboardText);
        int score = 0;
        for (int i = 0; i < c.value.length; i++) {
            score += (p.inventory[i] * c.value[i]);
        }
        score += p.notes;
        score += p.mazeScore;


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("score", score);

        CollectionReference colref = db.collection("leaderboard");
        colref.add(userMap);

        String newPlayerEntry = "" + score;
        while(newPlayerEntry.length() < 8) newPlayerEntry += " ";
        newPlayerEntry += name; // Name always starts at index 8
        leaderboardView.updateEntries(newPlayerEntry + "\n\n");

        db.collection("leaderboard")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qSnap) {
                        for (DocumentSnapshot dSnap: qSnap) {
                            String oldPlayerEntry = "" + dSnap.get("score");
                            while(oldPlayerEntry.length() < 8) oldPlayerEntry += " ";
                            oldPlayerEntry += dSnap.get("name"); // Name always starts at index 8
                            leaderboardView.updateEntries(oldPlayerEntry);
                        }
                    }
                });

        leaderboardView.setAddedEntryConfiguration();
    }

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
    public void onStartGame(int mazeSize) {
        Log.i("DungeonGame", "creating maze of size " + mazeSize);

        this.maze = new Maze(mazeSize);
        p.mazeScore += mazeSize;

        //bundle args and instantiate new fragment
        String mazeText = maze.toObscuredString(p);
        Bundle fragArgs = MazeFragment.makeArgsBundle(mazeText);
        Fragment mazeFrag = new MazeFragment(this);
        mazeFrag.setArguments(fragArgs);

        this.mainView.displayFragment(mazeFrag);

        if (firstOpen) playMusic();
        firstOpen = false;
    }

    public void onMazeComplete(IMazeView mazeView) {
        Log.i("DungeonGame", "reached end of maze");
        mazeView.setMazeSuccessConfiguration();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MAZE, this.maze);
        outState.putSerializable(PLAYER, this.p);
    }

    @Override
    public void onVolumeToggle(IMazeView mazeView) {
        Button volumeButton = findViewById(R.id.volumeButton);
        if (mediaPlayer == null) {
            playMusic();
            volumeButton.setText("\uD83D\uDD0A");
        }
        else {
            stopMusic();
            volumeButton.setText("\uD83D\uDD07");
        }
    }

    public void playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.soundtrack);
            mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.start());
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.release();
        mediaPlayer = null;
        Log.i("DungeonGame", "MediaPlayer released");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer == null) return;
        stopMusic();
    }

    @Override
    public void onReturnToMenu(ILeaderboardView leaderboardView) {
        //recreates player to wipe notes/inventory
        this.p = new Player(0,0);
        this.mainView.displayFragment(new MenuFragment(this));
        firstOpen = true;
    }
}
