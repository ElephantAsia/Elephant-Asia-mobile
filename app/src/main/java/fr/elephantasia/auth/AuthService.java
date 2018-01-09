package fr.elephantasia.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Stephane on 05/01/2018.
 */

public class AuthService extends Service {

	private Authenticator authenticator;

	@Override
	public void onCreate() {
		authenticator = new Authenticator(this);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return authenticator.getIBinder();
	}

}
