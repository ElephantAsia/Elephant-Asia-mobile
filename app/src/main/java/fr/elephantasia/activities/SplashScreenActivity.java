package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.elephantasia.auth.AuthActivity;

public class SplashScreenActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
		Intent intent = new Intent(this, AuthActivity.class);
    startActivity(intent);
    finish();
  }

}
