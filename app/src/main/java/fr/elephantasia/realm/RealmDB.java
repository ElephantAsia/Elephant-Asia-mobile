package fr.elephantasia.realm;

import fr.elephantasia.realm.model.Elephant;
import io.realm.Realm;

/**
 * Created by seb on 29/04/2017.
 */

public class RealmDB {

  static public void copyOrUpdate(final Elephant elephant) {
    Realm realm = Realm.getDefaultInstance();

    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(elephant);
      }
    });
    realm.close();
  }

}
