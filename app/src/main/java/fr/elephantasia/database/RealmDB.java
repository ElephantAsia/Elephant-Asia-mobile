package fr.elephantasia.database;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.StaticTools;
import io.realm.Realm;
import io.realm.RealmObject;

import static fr.elephantasia.database.model.Elephant.CUID;
import static fr.elephantasia.database.model.Elephant.DB_STATE;
import static fr.elephantasia.database.model.Elephant.ID;
import static fr.elephantasia.database.model.Elephant.SYNC_STATE;

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

  static public Integer getNextId(Realm realm, Class<? extends RealmObject> cls, String columnIdName) {
		return StaticTools.increment(realm.where(cls).max(columnIdName));
	}

  static public void insertOrUpdateElephant(final Elephant elephant, final List<Document> documents) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(@NonNull Realm bgRealm) {
        if (elephant.id == -1) {
          elephant.id = getNextId(bgRealm, Elephant.class, ID);
        }
        elephant.lastVisited = new Date();
        bgRealm.insertOrUpdate(elephant);

        for (Document document : documents) {
          if (document.id == -1) {
            document.id = getNextId(bgRealm, Document.class, Document.ID);
          }
          document.elephant_id = elephant.id;
          bgRealm.insertOrUpdate(document);
        }
      }
    });
  }

  static public void insertOrUpdateDocument(final Document document) {
  	Realm realm = Realm.getDefaultInstance();
  	realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(@NonNull Realm bgRealm) {
				if (document.id == -1) {
					document.id = getNextId(bgRealm, Document.class, Document.ID);
				}
				bgRealm.insertOrUpdate(document);
			}
		});
	}

  static public void updateLastVisitedDate(final int id) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    realm.beginTransaction();
    elephant.lastVisited = new Date();
    realm.commitTransaction();
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
