package fr.elephantasia;

import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import fr.elephantasia.database.DatabaseController;
import io.realm.Realm;

/**
 * \brief Application's Entry point
 */
public class BaseApplication extends MultiDexApplication {

  private DatabaseController databaseController;

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);

    databaseController = new DatabaseController();
  }

  @NonNull
  public DatabaseController getDatabaseController() {
    return databaseController;
  }

}
