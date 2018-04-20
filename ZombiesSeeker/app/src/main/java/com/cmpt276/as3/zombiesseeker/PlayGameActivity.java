package com.cmpt276.as3.zombiesseeker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import com.cmpt276.as3.zombiesseeker.model.Game;
import com.cmpt276.as3.zombiesseeker.model.GameConfiguration;

public class PlayGameActivity extends AppCompatActivity {
    // constants:
    public static final String GAME_STATE = "GameState";
    public static final String MY_GAME_STATE = "myGameState";
    public static final String LAST_GAME_SIZE = "lastGameSize";
    public static final String LAST_GAME_NUM_ZOMBIE = "lastGameNumZombie";
    public static final String TOTAL_GAME_COUNT = "totalGameCount";
    public static final String CONGRATULATION_DIALOG = "CongratulationDialog";

    // load game configurations:
    private GameConfiguration conf = GameConfiguration.getConfiguration(this);
    private final int NUM_ROWS = conf.getNumRow();
    private final int NUM_COLS = conf.getNumCol();
    private Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    private final int NUM_ZOMBIE = conf.getNumZombie();

    // initialize game:
    private Game game;
    private boolean clicked[][];
    private boolean[][] foundZombies;
    private int gameCount;
    private int bestScore;

    // use of store game state
    private SharedPreferences sharedPreferences;
    private Gson gson;

    // play sound when zombie detected
    private MediaPlayer found_zombies_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play_game);
        getSupportActionBar().hide();

        found_zombies_sound = MediaPlayer.create(PlayGameActivity.this, R.raw.sound);

        getDataFromPrevious();
        populateButtons();
        updateUI();
        displayBestScore();
    }

    private void displayBestScore() {
        TextView textView = findViewById(R.id.tv_bestScore);
        if (bestScore == -1){
            textView.setText(R.string.best_score_null);
        }else{
            textView.setText(getString(R.string.best_score, bestScore));
        }
    }

    private void getDataFromPrevious() {
        gson = new Gson();
        sharedPreferences = getSharedPreferences(GAME_STATE, MODE_PRIVATE);
        bestScore = sharedPreferences.getInt("Best Score in" + NUM_ROWS + NUM_ZOMBIE, -1);
        gameCount = sharedPreferences.getInt(TOTAL_GAME_COUNT, 0);
        if (sharedPreferences.getInt(LAST_GAME_SIZE, 0) == NUM_ROWS
                && sharedPreferences.getInt(LAST_GAME_NUM_ZOMBIE, 0) == NUM_ZOMBIE) {
            String json = sharedPreferences.getString(MY_GAME_STATE, "");
            game = gson.fromJson(json, new TypeToken<Game>(){}.getType());
            clicked = game.getClicked();
            foundZombies = game.getFoundZombie();
        } else {
            game = new Game(NUM_ROWS, NUM_COLS, NUM_ZOMBIE);
            clicked = new boolean[NUM_ROWS][NUM_COLS];
            foundZombies = new boolean[NUM_ROWS][NUM_COLS];
        }
    }



    private void populateButtons() {
        TableLayout table = findViewById(R.id.btnsTable);
        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);
            for (int col =0; col < NUM_COLS; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                // make text not clip on small
                button.setPadding(0,0,0,0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL);

                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private void gridButtonClicked(int row, int col) {
        Button button = buttons[row][col];

        // lock Button sizes:
        lockButtonSize();

        // Does not scale image
        // button.setBackgroundResource(R.drawable.zombie_head);

        int result = game.checkPosition(row, col);
        if (result == -1){
            // Scale image to button
            // Only works in JellyBean
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.zombie_head);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            button.setBackground(new BitmapDrawable(getResources(), scaledBitmap));
            if (game.getNumFound() == NUM_ZOMBIE){
                FragmentManager manager = getSupportFragmentManager();
                CongratulateFragment dialog = new CongratulateFragment();
                dialog.show(manager, CONGRATULATION_DIALOG);

            }
            found_zombies_sound.start();
        } else {
            if (!clicked[row][col]) {
                clicked[row][col] = true;
                button.setText("" + result);
                button.setTextColor(Color.RED);
            }
        }
        updateUI();


    }

    private void lockButtonSize() {
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        TextView textView = findViewById(R.id.tv_countScan);
        textView.setText(getString(R.string.scanUsed, game.getNumScan()));
        TextView tv_num_zombie = findViewById(R.id.tv_zombie_left);
        tv_num_zombie.setText(getString(R.string.found_x_of_total, game.getNumFound(), NUM_ZOMBIE));
        TextView tv_gameCount = findViewById(R.id.tv_totalGameCount);
        tv_gameCount.setText(getString(R.string.total_times_played, gameCount));

        int[][] board = game.getBoard();
        boolean[][] foundZombies = game.getFoundZombie();
        for (int i = 0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++){
                Button button = buttons[i][j];
                if (clicked[i][j]) {
                    button.setText("" + board[i][j]);
                    button.setTextColor(Color.RED);
                }
            }
        }

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, PlayGameActivity.class);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    lockButtonSize();
                    Button button = buttons[i][j];
                    if (foundZombies[i][j]) {
                        int newWidth = button.getWidth();
                        int newHeight = button.getHeight();
                        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.zombie_head);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                        button.setBackground(new BitmapDrawable(getResources(), scaledBitmap));
                    }
                }
            }
        }

    }
    @Override
    protected void onStop() {
        super.onStop();

        // check if we need a new game:
        if (game.getNumFound() == NUM_ZOMBIE){
            if (bestScore == -1 || game.getNumScan() < bestScore){
                bestScore = game.getNumScan();
            }
            game = new Game(NUM_ROWS, NUM_COLS, NUM_ZOMBIE);
            gameCount++;
        }

        // save data on Gson
        sharedPreferences = getSharedPreferences(GAME_STATE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();
        String json = gson.toJson(game);
        editor.putString(MY_GAME_STATE, json);
        editor.putInt(LAST_GAME_SIZE, NUM_ROWS);
        editor.putInt(LAST_GAME_NUM_ZOMBIE, NUM_ZOMBIE);
        editor.putInt(TOTAL_GAME_COUNT, gameCount);
        editor.putInt("Best Score in" + NUM_ROWS + NUM_ZOMBIE, bestScore);
        editor.apply();
    }



}
