package fr.elephantasia.realm;

import android.net.Uri;

import fr.elephantasia.realm.model.Contact;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.utils.ImageUtil;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by seb on 29/04/2017.
 */

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
