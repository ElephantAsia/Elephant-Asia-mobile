package fr.elephantasia.database;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

/**
 * Created by seb on 29/04/2017.
 */

//https://realm.io/docs/java/latest/
public class RealmDB {

  // CRUD
  static public void copyOrUpdate(final Elephant elephant) {
    Realm realm = Realm.getDefaultInstance();

    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(elephant);
      }
    });
    realm.close();
  }

  static public void copyOrUpdate(final Contact contact) {
    Realm realm = Realm.getDefaultInstance();

    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(contact);
      }
    });
    realm.close();
  }

}
