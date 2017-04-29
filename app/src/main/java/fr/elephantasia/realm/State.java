package fr.elephantasia.realm;

import io.realm.RealmObject;

/**
 * Created by seb on 29/04/2017.
 */

public class State extends RealmObject {
  public boolean pending = false;
  public boolean deleted = false;
  public boolean local = false;
  public boolean draft = false;
}
