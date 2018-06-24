package fr.elephantasia.database;

/**
 * Created by seb on 28/05/2017.
 */

// Unused class
//public class RealmManager {
//  private static final RealmManager ourInstance = new RealmManager();
//  private ThreadLocal<Realm> realms;
//
//  public RealmManager() {
//    realms = new ThreadLocal<>();
//  }
//
//  public static RealmManager getInstance() {
//    return ourInstance;
//  }
//
//  public Realm openRealm() {
//    Realm realm = realms.get();
//    if (realm != null && !realm.isClosed()) {
//      throw new IllegalStateException("Realm is already open");
//    }
//    realm = Realm.getDefaultInstance();
//    realms.set(realm);
//    return realm;
//  }
//
//  public Realm getRealm() {
//    Realm realm = realms.get();
//    if (realm == null) {
//      throw new IllegalStateException("There is no open Realm on this thread");
//    }
//    return realm;
//  }
//
//  public void closeRealm() {
//    Realm realm = realms.get();
//    if (realm == null) {
//      throw new IllegalStateException("No Realm found to close");
//    }
//    if (!realm.isClosed()) {
//      realm.close();
//    }
//    realms.set(null);
//  }
//}
