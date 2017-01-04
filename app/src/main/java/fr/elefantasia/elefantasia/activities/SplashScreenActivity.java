package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.fragment.SplashScreenFragment;

public class SplashScreenActivity extends AppCompatActivity {

    private SplashScreenFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

        refreshFragment();
    }

    private void refreshFragment() {
        if (fragment == null) {
            fragment = new SplashScreenFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.splash_screen_fragment, fragment);
    }
}
