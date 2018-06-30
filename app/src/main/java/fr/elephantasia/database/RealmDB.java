package fr.elephantasia.database;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.StaticTools;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

import static fr.elephantasia.database.model.Elephant.ID;

// https://realm.io/docs/java/latest/
// https://stackoverflow.com/questions/40500944/calling-realm-from-asynctask
/**
 * Realm Facade
 */
class RealmDB {

  private Realm realm;
  private boolean inTransaction;

  RealmDB() {
    realm = null;
    inTransaction = false;
  }

  void beginTransaction() {
    realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    inTransaction = true;
  }

  void cancelTransaction() {
    realm.cancelTransaction();
    realm.close();
    inTransaction = false;
  }

  void commitTransaction() {
    realm.commitTransaction();
    realm.close();
    inTransaction = false;
  }

  void insertOrUpdate(Elephant e) {
    begin();
    if (e.id == -1) {
      e.id = GetNextId(realm, Elephant.class, Elephant.ID);
    }
    realm.insertOrUpdate(e);
    end();
  }

  void insertOrUpdate(Document d) {
    begin();
    if (d.id == -1) {
      d.id = GetNextId(realm, Document.class, Document.ID);
    }
    realm.insertOrUpdate(d);
    end();
  }

  void updateLastVisitedDateElephant(Integer id) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    if (elephant != null) {
      realm.beginTransaction();
      elephant.lastVisited = new Date();
      realm.commitTransaction();
    }
    realm.close();
  }

  void copyOrUpdate(Contact c) {
    beginTransaction();
    realm.copyToRealmOrUpdate(c);
    commitTransaction();
  }

  void delete(Elephant e) {
    begin();
    e.dbState = Elephant.DbState.Deleted.name();
    realm.insertOrUpdate(e);
    end();
  }

  @NonNull
  public List<Elephant> searchElephantsByState(DatabaseController.SearchMode searchMode) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<Elephant> query = realm.where(Elephant.class);

    if (searchMode == DatabaseController.SearchMode.Draft) {
      query.equalTo(Elephant.DRAFT, true);
    } else if (searchMode == DatabaseController.SearchMode.Pending) {
      query.equalTo(Elephant.SYNC_STATE, Elephant.SyncState.Pending.name());
    } else if (searchMode == DatabaseController.SearchMode.Saved) {
      query.notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
        .isNotNull(Elephant.DB_STATE)
        .isNull(Elephant.SYNC_STATE)
        .equalTo(Elephant.DRAFT, false);
    }
    return realm.copyFromRealm(query.findAll());
  }

  @NonNull
  public List<Elephant> search(Elephant e) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<Elephant> query = realm.where(Elephant.class);

    query.notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name());
    if (e != null) {
      query.contains(Elephant.NAME, e.name, Case.INSENSITIVE);
      if (e.chips1 != null) {
        query.contains(Elephant.CHIPS1, e.chips1, Case.INSENSITIVE);
      }
      if (e.sex != null) {
        query.equalTo(Elephant.SEX, e.sex);
      }
      if (e.mteOwner) {
        if (e.mteNumber != null) {
          query.equalTo(Elephant.MTE_NUMBER, e.mteNumber);
        } else {
          query.equalTo(Elephant.MTE_OWNER, true);
        }
      }
    }
    return realm.copyFromRealm(query.findAll());
  }

  @NonNull
  public RealmResults<Contact> search(Contact c) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<Contact> query = realm.where(Contact.class);

    query.contains(Contact.LASTNAME, c.lastName, Case.INSENSITIVE)
      .or()
      .contains(Contact.FIRSTNAME, c.lastName, Case.INSENSITIVE);
    if (!c.owner) {
      query.equalTo(Contact.OWNER, false);
    }
    if (!c.cornac) {
      query.equalTo(Contact.CORNAC, false);
    }
    if (!c.vet) {
      query.equalTo(Contact.VET, false);
    }
    return query.findAll();
  }

  @Nullable
  public Elephant getElephantById(Integer id) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class)
      .equalTo(Elephant.ID, id)
      .findFirst();

    if (elephant != null) {
      return realm.copyFromRealm(elephant);
    }
    return null;
  }

  @Nullable
  public Elephant getElephantByCuid(String cuid) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class)
      .equalTo(Elephant.CUID, cuid)
      .findFirst();

    if (elephant != null) {
      return realm.copyFromRealm(elephant);
    }
    return null;
  }

  @Nullable
  public List<Document> getDocumentsByElephantId(Integer elephantId) {
    Realm realm = Realm.getDefaultInstance();
    List<Document> documents = realm.where(Document.class)
      .equalTo(Document.ELEPHANT_ID, elephantId)
      .findAll();

    if (documents != null) {
      return realm.copyFromRealm(documents);
    }
    return null;
  }

  public List<Elephant> getLastVisitedElephant() {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Elephant> results = realm.where(Elephant.class)
      .greaterThan(Elephant.LAST_VISITED, DateHelpers.getLastWeek())
      .sort(Elephant.LAST_VISITED, Sort.DESCENDING)
      .findAll();
    return realm.copyFromRealm(results);
  }

  public Long getElephantsCount() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(Elephant.class).count();
  }

  public Long getElephantsSyncStatePendingCount() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(Elephant.class)
      .equalTo(Elephant.SYNC_STATE, Elephant.SyncState.Pending.name())
      .count();
  }

  public Long getElephantsReadyToSyncCount() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(Elephant.class)
      .isNotNull(Elephant.DB_STATE)
      .isNull(Elephant.SYNC_STATE)
      .equalTo(Elephant.DRAFT, false)
      .count();
  }

  public Long getElephantsDraftCount() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(Elephant.class)
      .equalTo(Elephant.DRAFT, true)
      .count();
  }

	@Deprecated // TODO: rework that with documents
  static void insertOrUpdateElephant(final Elephant elephant, final List<Document> documents) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(@NonNull Realm bgRealm) {
        if (elephant.id == -1) {
          elephant.id = GetNextId(bgRealm, Elephant.class, ID);
        }
        elephant.lastVisited = new Date();
        bgRealm.insertOrUpdate(elephant);

        for (Document document : documents) {
          if (document.id == -1) {
            document.id = GetNextId(bgRealm, Document.class, Document.ID);
          }
          document.elephant_id = elephant.id;
          bgRealm.insertOrUpdate(document);
        }
      }
    });
  }

  private void begin() {
    if (!inTransaction) {
      realm = Realm.getDefaultInstance();
    }
  }

  private void end() {
    if (!inTransaction) {
      realm.close();
    }
  }

  private static Integer GetNextId(Realm realm, Class<? extends RealmObject> cls, String columnIdName) {
    return StaticTools.increment(realm.where(cls).max(columnIdName));
  }

}
