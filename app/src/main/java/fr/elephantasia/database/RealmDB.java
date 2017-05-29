package fr.elephantasia.database;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static fr.elephantasia.database.model.Elephant.ID;

/**
 * Created by seb on 29/04/2017.
 */

//https://realm.io/docs/java/latest/
public class RealmDB {
  // CRUD

  private static void setMother(Realm realm, Elephant dog, String id) {
    dog.mother = realm.where(Elephant.class).equalTo(ID, id).findFirst();
  }


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

  static public void copyOrUpdate(final Document document) {
    Realm realm = Realm.getDefaultInstance();

    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.copyToRealmOrUpdate(document);
      }
    });
    realm.close();
  }

}
