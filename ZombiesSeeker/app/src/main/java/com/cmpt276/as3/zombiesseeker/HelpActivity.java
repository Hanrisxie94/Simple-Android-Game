package com.cmpt276.as3.zombiesseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setupBackButton();
        activateAllLinks();
    }

    private void setupBackButton() {
        Button button = findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void activateAllLinks() {
        TextView textView0 = findViewById(R.id.reference_0);
        TextView textView1 = findViewById(R.id.reference_1);
        TextView textView2 = findViewById(R.id.reference_2);
        TextView textView3 = findViewById(R.id.reference_3);
        TextView textView4 = findViewById(R.id.reference_4);
        TextView textView5 = findViewById(R.id.reference_5);
        TextView textView6 = findViewById(R.id.reference_6);
        TextView textView7 = findViewById(R.id.reference_7);
        TextView textView8 = findViewById(R.id.reference_8);
        TextView textView9 = findViewById(R.id.reference_9);
        textView0.setMovementMethod(LinkMovementMethod.getInstance());
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
        textView5.setMovementMethod(LinkMovementMethod.getInstance());
        textView6.setMovementMethod(LinkMovementMethod.getInstance());
        textView7.setMovementMethod(LinkMovementMethod.getInstance());
        textView8.setMovementMethod(LinkMovementMethod.getInstance());
        textView9.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
