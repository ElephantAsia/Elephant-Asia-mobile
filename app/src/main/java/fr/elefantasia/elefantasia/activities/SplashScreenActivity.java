package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.elefantasia.elefantasia.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, AddElephant.class);
                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DELAY);



    }
}
