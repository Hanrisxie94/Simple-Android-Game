package com.cmpt276.as3.zombiesseeker;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static java.lang.Thread.sleep;

public class WelcomeActivity extends AppCompatActivity {
    private int WELCOME_TIME_OUT = 6000;
    private int skilClicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();


        setupSkipButton();

        setupWelcomeAnimation();

        // setup Exit After 4s;
        setupExitAfter4s();
    }


    private void setupWelcomeAnimation() {
        TextView tv = findViewById(R.id.tv_Hardys);
        ImageView iv = findViewById(R.id.iv_logo);

        Animation anim_welcome = AnimationUtils.loadAnimation(this, R.anim.wel_anim);
        tv.startAnimation(anim_welcome);
        iv.startAnimation(anim_welcome);

        Animator animator_logo = AnimatorInflater.loadAnimator(WelcomeActivity.this, R.animator.scale_logo);
        animator_logo.setTarget(iv);
        animator_logo.start();

        Animator animator_Hardys = AnimatorInflater.loadAnimator(WelcomeActivity.this, R.animator.move);
        animator_Hardys.setTarget(tv);
        animator_Hardys.start();


    }


    private void setupExitAfter4s() {
        final Intent intent = MainActivity.makeIntent(WelcomeActivity.this);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(WELCOME_TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (skilClicked == 0) {
                    startActivity(intent);
                    finish();
                } else{
                    finish();
                }
            }
        }; timer.start();
    }

    private void setupSkipButton() {
        Button btn = findViewById(R.id.btnSkip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skilClicked = 1;
                Intent intent = MainActivity.makeIntent(WelcomeActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

}
