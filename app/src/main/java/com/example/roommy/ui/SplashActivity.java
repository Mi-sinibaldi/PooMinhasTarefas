package com.example.roommy.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.roommy.R;


public class SplashActivity extends AppCompatActivity {

    private Animation transTop;
    private ImageView imageViewSplash;

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
        transTop = AnimationUtils.loadAnimation(this, R.anim.splash_transition_top);

        imageViewSplash.setAnimation(transTop);

    }

    public void passarActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
