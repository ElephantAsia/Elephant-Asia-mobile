package fr.elephantasia.utils;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

public class BaseApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
  }

}
