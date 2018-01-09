package fr.elephantasia.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.elephantasia.R;
import fr.elephantasia.activities.home.HomeActivity;
import fr.elephantasia.auth.Constants;
import fr.elephantasia.utils.KeyboardHelpers;

// Deprecated, use AuthActivity instead
@Deprecated
public class LoginActivity extends AppCompatActivity {

  //TODO: refactor using view binding
  private EditText mUsernameEditText;
  private EditText mPasswordEditText;
  private Button mButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);
    final View activityRootView = findViewById(R.id.login_activity_main_layout);
    KeyboardHelpers.hideKeyboardListener(activityRootView, this);

    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }

    mButton = findViewById(R.id.login_button);
    mUsernameEditText = findViewById(R.id.login_id);
    mPasswordEditText = findViewById(R.id.login_pwd);

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

      // Preferences.setUsername(getApplicationContext(), mUsernameEditText.getText().toString());
      // Preferences.setPassword(getApplicationContext(), mPasswordEditText.getText().toString());

      Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
      LoginActivity.this.startActivity(intent);
      LoginActivity.this.finish();

    } else {
      Toast.makeText(LoginActivity.this, "Bad username/password (use demo/demo)", Toast.LENGTH_SHORT).show();
    }
  }

  private void checkHasSignin() {
    AccountManager accountManager = AccountManager.get(this);
    Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
    if (accounts.length > 0) {
      // use accounts[0]
			Log.i("login", "username: " + accounts[0].name);
			Log.i("login", "authtoken: " + accountManager.peekAuthToken(accounts[0], Constants.AUTHTOKEN_TYPE));
    } else {
    	Log.i("login", "no account");
    	// addAccount();
    	// ?
		}
    /* if (Preferences.hasSignin(getApplicationContext())) {
      mUsernameEditText.setText(Preferences.getUsername(getBaseContext()));
      mPasswordEditText.setText(Preferences.getPassword(getBaseContext()));
      mButton.performClick();
    } */
  }

  /* private void addAccount() {
  	AccountManager accountManager = AccountManager.get(this);
		final AccountManagerFuture<Bundle> future = accountManager.addAccount(Constants.ACCOUNT_TYPE, Constants.AUTHTOKEN_TYPE,
			null, null, this, new AccountManagerCallback<Bundle>() {
			@Override
			public void run(AccountManagerFuture<Bundle> future) {
				try {
					Bundle bundle = future.getResult();
					Toast.makeText(LoginActivity.this, "Account created", Toast.LENGTH_SHORT).show();
					Log.i("login", "bundle: " + bundle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);

	} */
}
