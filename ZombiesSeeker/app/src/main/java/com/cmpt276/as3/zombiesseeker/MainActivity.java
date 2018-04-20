package com.cmpt276.as3.zombiesseeker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.cmpt276.as3.zombiesseeker.model.GameConfiguration;
import br.com.bloder.magic.view.MagicButton;

/*
    Removed some unused imports
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setupPlayGameButton();
        setupOptionButton();
        setupHelpButton();
    }



    private void setupPlayGameButton() {
        MagicButton btn = findViewById(R.id.btn_play);
        btn.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameConfiguration conf = GameConfiguration.getConfiguration(MainActivity.this);
                Intent intent = PlayGameActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });

    }

    private void setupOptionButton() {
        MagicButton btn = findViewById(R.id.btn_options);
        btn.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameConfiguration conf = GameConfiguration.getConfiguration(MainActivity.this);
                Intent intent = OptionalActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupHelpButton() {
        MagicButton btn = findViewById(R.id.btn_help);
        btn.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
