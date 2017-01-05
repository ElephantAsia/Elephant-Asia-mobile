package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.utils.Preferences;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mButton = (Button)findViewById(R.id.login_button);
        mUsernameEditText = (EditText)findViewById(R.id.login_id);
        mPasswordEditText = (EditText)findViewById(R.id.login_pwd);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUsernameEditText.getText().toString().equalsIgnoreCase("demo")
                        && mPasswordEditText.getText().toString().equalsIgnoreCase("demo")) {

                    Preferences.setUsername(getApplicationContext(), mUsernameEditText.getText().toString());
                    Preferences.setPassword(getApplicationContext(), mPasswordEditText.getText().toString());

                    Intent intent = new Intent(LoginActivity.this, AddElephant.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Bad username or password (use demo/demo)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Preferences.hasSignin(getApplicationContext())) {
            mUsernameEditText.setText(Preferences.getUsername(getBaseContext()));
            mPasswordEditText.setText(Preferences.getPassword(getBaseContext()));
            mButton.performClick();
        }

    }

}
