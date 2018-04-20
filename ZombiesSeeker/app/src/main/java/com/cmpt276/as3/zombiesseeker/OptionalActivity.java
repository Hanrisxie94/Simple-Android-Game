package com.cmpt276.as3.zombiesseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cmpt276.as3.zombiesseeker.model.GameConfiguration;

public class OptionalActivity extends AppCompatActivity {
    public static final String BOARD_ROWS = "Board rows";
    public static final String BOARD_COLS = "Board cols";

    public static final String NUM_ZOMBIE_SELECTED = "num zombie selected";
    public static final String APP_PREFS = "AppPrefs";
    public static final String BEST_SCORE_IN46 = "Best Score in46";
    public static final String BEST_SCORE_IN410 = "Best Score in410";
    public static final String BEST_SCORE_IN415 = "Best Score in415";
    public static final String BEST_SCORE_IN420 = "Best Score in420";
    public static final String BEST_SCORE_IN56 = "Best Score in56";
    public static final String BEST_SCORE_IN510 = "Best Score in510";
    public static final String BEST_SCORE_IN515 = "Best Score in515";
    public static final String BEST_SCORE_IN520 = "Best Score in520";
    public static final String BEST_SCORE_IN66 = "Best Score in66";
    public static final String BEST_SCORE_IN610 = "Best Score in610";
    public static final String BEST_SCORE_IN615 = "Best Score in615";
    public static final String BEST_SCORE_IN620 = "Best Score in620";
    public static final String TOTAL_GAME_COUNT = "totalGameCount";
    private GameConfiguration conf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional);

        conf = GameConfiguration.getConfiguration(this);

        createBoardSizeRadioGroup();
        createNumZombieRadioGroup();
        setupOkButton();
        setupEraseTimesPlayedButton();
    }

    private void setupEraseTimesPlayedButton() {
        Button btn = findViewById(R.id.btn_erase);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("GameState", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(BEST_SCORE_IN46, -1);
                editor.putInt(BEST_SCORE_IN410, -1);
                editor.putInt(BEST_SCORE_IN415, -1);
                editor.putInt(BEST_SCORE_IN420, -1);
                editor.putInt(BEST_SCORE_IN56, -1);
                editor.putInt(BEST_SCORE_IN510, -1);
                editor.putInt(BEST_SCORE_IN515, -1);
                editor.putInt(BEST_SCORE_IN520, -1);
                editor.putInt(BEST_SCORE_IN66, -1);
                editor.putInt(BEST_SCORE_IN610, -1);
                editor.putInt(BEST_SCORE_IN615, -1);
                editor.putInt(BEST_SCORE_IN620, -1);

                editor.putInt(TOTAL_GAME_COUNT, 0);
                editor.apply();
                Toast.makeText(OptionalActivity.this, "Times Reseted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupOkButton() {
        Button btn = findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void createBoardSizeRadioGroup() {
        RadioGroup boardRadios = findViewById(R.id.board_size_radios);

        int[] boardRows = getResources().getIntArray(R.array.num_of_row);
        int[] boardCols = getResources().getIntArray(R.array.num_of_col);

        // Create the buttons:
        for (int i = 0; i < boardRows.length; i++){
            final int rows = boardRows[i];
            final int cols = boardCols[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.game_size, rows, cols));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(OptionalActivity.this, "You clicked: " + rows + " × " + cols,
//                            Toast.LENGTH_SHORT).show();
                    conf.setNumCol(cols);
                    conf.setNumRow(rows);
                    saveBoardSizeSelected(rows, cols);
                }
            });
            // Add to radio group
            boardRadios.addView(button);

            // Select default button:
            if (rows == getBoardRows(OptionalActivity.this)){
                button.setChecked(true);
            }
        }
    }

    private void saveBoardSizeSelected(int rows, int cols) {
        SharedPreferences prefs = this.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BOARD_ROWS, rows);
        editor.putInt(BOARD_COLS, cols);
        editor.apply();
    }

    static public int getBoardRows(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        int defaultNumRows = context.getResources().getInteger(R.integer.default_num_of_row);
        return  prefs.getInt(BOARD_ROWS, defaultNumRows);
    }

    static public int getBoardCols(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        int defaultNumCols = context.getResources().getInteger(R.integer.default_num_of_col);
        return prefs.getInt(BOARD_COLS, defaultNumCols);
    }

    private void createNumZombieRadioGroup() {
        RadioGroup numberZombiesRadios = findViewById(R.id.num_zombie_radios);

        int[] numZombies = getResources().getIntArray(R.array.num_of_zombies);

        // Create buttons:
        for (final int numZombie : numZombies) {
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.number_zombies, numZombie));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(OptionalActivity.this, "Picked: " + numZombie + " zombies",
//                            Toast.LENGTH_SHORT).show();
                    conf.setNumZombie(numZombie);
                    saveNumZombieSelected(numZombie);
                }
            });

            // Add to radio group
            numberZombiesRadios.addView(button);

            // select default button;
            if (numZombie == getNumZombieSelected(OptionalActivity.this)) {
                button.setChecked(true);
            }

        }
    }

    private void saveNumZombieSelected(int numZombie) {
        SharedPreferences prefs = this.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_ZOMBIE_SELECTED, numZombie);
        editor.apply();
    }

    public static int getNumZombieSelected(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        int defaultNumZombie = context.getResources().getInteger(R.integer.default_num_of_zombies);
        return prefs.getInt(NUM_ZOMBIE_SELECTED, defaultNumZombie);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionalActivity.class);
    }
}
