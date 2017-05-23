package fr.elephantasia;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

//http://jakewharton.github.io/butterknife/
public class BaseApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
  }

}
