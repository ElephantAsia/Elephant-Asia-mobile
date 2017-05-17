package fr.elephantasia.utils;

import android.support.multidex.MultiDexApplication;

import fr.elephantasia.realm.RealmDB;
import fr.elephantasia.realm.model.*;
import io.realm.Realm;

public class BaseApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
  }

}
