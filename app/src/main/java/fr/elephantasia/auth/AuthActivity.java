package fr.elephantasia.auth;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import fr.elephantasia.R;
import fr.elephantasia.activities.home.HomeActivity;
import fr.elephantasia.network.GetAuthTokenAsyncRequest;
import fr.elephantasia.utils.KeyboardHelpers;

/**
 * Created by Stephane on 09/01/2018.
 */
public class AuthActivity extends AccountAuthenticatorActivity {

	private static final String EXTRA_LAUNCHED_BY_ACCOUNT_MANAGER = "launched_by_account_manager";

	public static final String EXTRA_USERNAME = "username";
	public static final String EXTRA_AUTHTOKEN_TYPE = "authtoken_type";

	private GetAuthTokenAsyncRequest getAuthTokenAsyncRequest;

	private AccountManager accountManager;
	private boolean launchedByAccountManager = false;
	private boolean newAccount = false;
	private String username;
	private String password;

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private Button mButton;


	public static void setExtraLaunchedByAccountManager(Intent intent, boolean value) {
		intent.putExtra(EXTRA_LAUNCHED_BY_ACCOUNT_MANAGER, value);
	}

	public static boolean getExtraLaunchedByAccountManager(Intent intent) {
		return intent.getBooleanExtra(EXTRA_LAUNCHED_BY_ACCOUNT_MANAGER, false);
	}

	private boolean isGetAuthTokenRunning() {
		return getAuthTokenAsyncRequest != null && getAuthTokenAsyncRequest.isRunning();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_activity);
		final View activityRootView = findViewById(R.id.login_activity_main_layout);
		KeyboardHelpers.hideKeyboardListener(activityRootView, this);

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

		accountManager = AccountManager.get(this);
		username = getIntent().getStringExtra(EXTRA_USERNAME);
		launchedByAccountManager = getExtraLaunchedByAccountManager(getIntent());
		newAccount = username == null;

		Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);

		if (accounts.length > 0) {
			if (launchedByAccountManager) {
				new MaterialDialog.Builder(AuthActivity.this)
					.title("Account limit reached")
					.content("Multi-account is not allowed")
					.positiveText("Ok")
					.onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
							setResult(RESULT_CANCELED);
							finish();
						}
					})
					.show();
			} else {
     // TODO: check if steph and gilles what we want to do here
     final Intent intent = new Intent(this, HomeActivity.class);
     startActivity(intent);
     finish();
//				username = accounts[0].name;
			}
		}

		mUsernameEditText.setText(username);
		mUsernameEditText.setEnabled(newAccount);
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
		String username = mUsernameEditText.getText().toString();
		password = mPasswordEditText.getText().toString();

		if (!isGetAuthTokenRunning()) {
			getAuthTokenAsyncRequest = new GetAuthTokenAsyncRequest(username, password,
				new GetAuthTokenAsyncRequest.Listener() {
				@Override
				public void onSuccess(JSONObject json) {
					try {
						onAuthenticationResult(json.getString("token"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					refreshLoginButton();
				}
				@Override
				public void onError(Integer code, JSONObject json) {
					if (code == null) {
						okDialog("Network Error", "Try again later");
					} else if (code == 401) {
						okDialog("Bad credentials", "Enter correct credentials");
					}
					Log.i("getAuthToken", "error");
					refreshLoginButton();
				}
			});
			getAuthTokenAsyncRequest.execute();
			refreshLoginButton();
		}
	}

	private void refreshLoginButton() {
		mButton.setEnabled(!isGetAuthTokenRunning());
	}

	private void okDialog(String title, String content) {
		new MaterialDialog.Builder(AuthActivity.this)
			.title(title)
			.content(content)
			.positiveText("Ok")
			.show();
	}

	private boolean onAuthenticationResult(final String authToken) {
		if (authToken == null || authToken.trim().length() == 0) {
			if (newAccount) {
				Log.i("message", "bad username / password");
			} else {
				Log.i("message", "bad password");
			}
			return false;
		}

		finishAuth(authToken);

		return true;
	}

	private void finishAuth(final String authToken) {
		Account account = new Account(mUsernameEditText.getText().toString(), Constants.ACCOUNT_TYPE);
		if (newAccount) {
			accountManager.addAccountExplicitly(account, password, null); // dernier parametre : bundle informations de l'user
			accountManager.setAuthToken(account, Constants.AUTHTOKEN_TYPE, authToken);
		} else {
			accountManager.setPassword(account, password);
		}

		if (launchedByAccountManager) {
			final Intent intent = new Intent();
			intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
			intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
			setAccountAuthenticatorResult(intent.getExtras());
			setResult(RESULT_OK, intent);
			finish();
		} else {
			final Intent intent = new Intent(this, HomeActivity.class);
			Log.i("login", "authtoken: " + authToken);
			startActivity(intent);
			finish();
		}
	}

}
