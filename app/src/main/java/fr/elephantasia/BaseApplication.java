package fr.elephantasia;

import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import fr.elephantasia.database.DatabaseController;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application's entry point
 */
public class BaseApplication extends MultiDexApplication {

  private DatabaseController databaseController;

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder()
      .deleteRealmIfMigrationNeeded()
      .build();
    Realm.setDefaultConfiguration(config);

    databaseController = new DatabaseController();
  }

  @NonNull
  public DatabaseController getDatabaseController() {
    return databaseController;
  }

}
