package fr.elephantasia.database;

import java.util.List;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.StaticTools;
import io.realm.Realm;

/**
 * Created by seb on 29/04/2017.
 */

// https://realm.io/docs/java/latest/
// https://stackoverflow.com/questions/40500944/calling-realm-from-asynctask
public class RealmDB {
  // CRUD

  /* private static void setMother(Realm realm, Elephant dog, String id) {
    dog.mother = realm.where(Elephant.class).equalTo(ID, id).findFirst();
  } */

  static public void insertElephant(final Elephant elephant, final List<Document> documents) {
		Realm realm = Realm.getDefaultInstance();
		realm.executeTransactionAsync(new Realm.Transaction() {
			@Override
			public void execute(Realm bgRealm) {
				elephant.id = StaticTools.increment(bgRealm.where(Elephant.class).max(Elephant.ID));
				bgRealm.insertOrUpdate(elephant);

				for (Document document : documents) {
					document.id = StaticTools.increment(bgRealm.where(Document.class).max(Document.ID));
					document.elephant_id = elephant.id;
					bgRealm.insertOrUpdate(document);
				}
			}
		});
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

}
