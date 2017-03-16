package fr.elephantasia.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.utils.Preferences;

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

        mUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validUsername();
                }
            }
        });

        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validPassword();
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validUsername() && validPassword()) {
                    onLoginClick();
                }
            }
        });

        checkHasSignin();
    }

    private boolean validUsername() {
        if (mUsernameEditText.getText().toString().trim().length() == 0) {
            mUsernameEditText.setError(getResources().getString(R.string.empty_username));
            return false;
        }
        mUsernameEditText.setError(null);
        return true;
    }

    private boolean validPassword() {
        if (mPasswordEditText.getText().toString().length() == 0) {
            mPasswordEditText.setError(getResources().getString(R.string.empty_password));
            return false;
        }
        mPasswordEditText.setError(null);
        return true;
    }

    private void onLoginClick() {
        if (mUsernameEditText.getText().toString().equalsIgnoreCase("demo")
                && mPasswordEditText.getText().toString().equalsIgnoreCase("demo")) {

            Preferences.setUsername(getApplicationContext(), mUsernameEditText.getText().toString());
            Preferences.setPassword(getApplicationContext(), mPasswordEditText.getText().toString());

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();

        } else {
            Toast.makeText(LoginActivity.this, "Bad username/password (use demo/demo)", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkHasSignin() {
        if (Preferences.hasSignin(getApplicationContext())) {
            mUsernameEditText.setText(Preferences.getUsername(getBaseContext()));
            mPasswordEditText.setText(Preferences.getPassword(getBaseContext()));
            mButton.performClick();
        }
    }
}
