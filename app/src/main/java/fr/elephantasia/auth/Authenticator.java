package fr.elephantasia.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import fr.elephantasia.network.GetAuthTokenRequest;
import fr.elephantasia.utils.TextHelpers;

/**
 * Created by Stephane on 05/01/2018.
 */

public class Authenticator extends AbstractAccountAuthenticator {

	private final Context context;

	Authenticator(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response,
													 String accountType,
													 String authTokenType,
													 String[] requiredFeatures,
													 Bundle options) throws NetworkErrorException {
		Log.i("authenticator", "addAcount()");
		final Intent intent = new Intent(context, AuthActivity.class);
		AuthActivity.setExtraLaunchedByAccountManager(intent, true);
		final Bundle bundle = new Bundle();
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response,
																	 Account account,
																	 Bundle options) throws NetworkErrorException {
		Log.i("authenticator", "confirmCredentials()");
		return null;
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		Log.i("authenticator", "editProperties()");
		return null;
	}


	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response,
														 Account account,
														 String authTokenType,
														 Bundle loginOptions) throws NetworkErrorException {
		Log.i("authenticator", "getAuthToken()");
		if (authTokenType.equals(Constants.AUTHTOKEN_TYPE)) {
			Bundle result = new Bundle();
			result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
			return result;
		}
		final AccountManager am = AccountManager.get(context);
		final String pwd = am.getPassword(account);
		if (pwd != null) {
			GetAuthTokenRequest req = new GetAuthTokenRequest(account.name, pwd);
			String authToken = req.execute();
			if (!TextHelpers.IsEmpty(authToken)) {
				final Bundle res = new Bundle();
				res.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
				res.putString(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
				res.putString(AccountManager.KEY_AUTHTOKEN, authToken);
				return res;
			}
		}
		Intent intent = new Intent(context, AuthActivity.class);
		intent.putExtra(AuthActivity.EXTRA_USERNAME, account.name);
		intent.putExtra(AuthActivity.EXTRA_AUTHTOKEN_TYPE, authTokenType);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		Bundle result = new Bundle();
		result.putParcelable(AccountManager.KEY_INTENT, intent);
		return result;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		Log.i("authenticator", "getAuthTokenLabel()");
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response,
														Account account,
														String[] features) throws NetworkErrorException {
		Log.i("authenticator", "hasFeatures()");
		final Bundle bundle = new Bundle();
		bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
		return bundle;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response,
																	Account account,
																	String authTokenType,
																	Bundle bundle) throws NetworkErrorException {
		Log.i("authenticator", "updateCredentials()");
		return null;
	}


}
