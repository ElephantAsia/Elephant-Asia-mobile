package fr.elephantasia.database.parceler;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by seb on 30/04/2017.
 */

// Abstract class for working with RealmLists
public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {
  @Override
  public RealmList<T> createCollection() {
    return new RealmList<>();
  }
}