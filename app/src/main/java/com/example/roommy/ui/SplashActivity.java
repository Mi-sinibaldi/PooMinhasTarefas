package com.example.roommy.ui;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roommy.R;


public class SplashActivity extends AppCompatActivity {

    private Animation transTop;
    private ImageView imageViewSplash;
    private TextView textViewNomeSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        transicionar();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passarActivity();

            }
        });
        thread.start();

        setFonts();

//        //implementa a splashscreen
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getBaseContext(), LoginActivity.class));
//                finish();
//            }
//        }, 3000);
    }

    public void transicionar() {
        imageViewSplash = findViewById(R.id.imageViewSplash);
        textViewNomeSplash = findViewById(R.id.textViewNomeSplash);
        transTop = AnimationUtils.loadAnimation(this, R.anim.splash_transition_top);

        imageViewSplash.setAnimation(transTop);
        textViewNomeSplash.setAnimation(transTop);

    }

    public void passarActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setFonts() {
        Typeface negrito = Typeface.createFromAsset(getAssets(),
                "BwModelica-BoldCondensed.ttf");
        textViewNomeSplash.setTypeface(negrito);
    }
}