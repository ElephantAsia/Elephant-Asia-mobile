package fr.elephantasia.database;

import java.util.List;

import javax.annotation.Nullable;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.DateHelpers;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Controller to manipulate the local database.
 * Create an indirection between the application and the database library used.
 *
 * Questions:
 * Currently created in the BaseApplication class. Should it be created in each activity ?
 * What about realm.close() if we create only one realm ?
 * copyFromRealm() ?
 **/
public class DatabaseController {

  public enum SearchMode {
    All,
    Pending,
    Draft,
    Saved
  }

  private Realm realm;

  public DatabaseController() {
    realm = Realm.getDefaultInstance();
  }

  public void beginTransaction() {
    realm.beginTransaction();
  }

  public void cancelTransaction() {
    realm.cancelTransaction();
  }

  public void commitTransaction() {
    realm.commitTransaction();
  }

  public void close() {
    realm.close();
  }

  /* Modifiers */

  public void insertOrUpdate(Elephant elephant) {
    if (elephant.id == -1) {
      elephant.id = RealmDB.getNextId(realm, Elephant.class, Elephant.ID);
    }
    realm.insertOrUpdate(elephant);
  }

  public void insertOrUpdate(Document document) {
    RealmDB.insertOrUpdateDocument(document);
  }

  public void insertOrUpdate(Elephant elephant, List<Document> documents) {
    RealmDB.insertOrUpdateElephant(elephant, documents);
  }


  public void updateLastVisitedDateElephant(Integer id) {
    RealmDB.updateLastVisitedDate(id);
  }

  public void copyOrUpdate(Contact contact) {
    RealmDB.copyOrUpdate(contact);
  }

  public void delete(final Elephant elephant) {
    elephant.dbState = Elephant.DbState.Deleted.name();
    realm.insertOrUpdate(elephant);
  }

  /* Getters */

  public RealmResults<Elephant> searchElephantsByState(SearchMode searchMode) {
    RealmQuery<Elephant> query = realm.where(Elephant.class);

    if (searchMode == SearchMode.Draft) {
      query.equalTo(Elephant.DRAFT, true);
    } else if (searchMode == SearchMode.Pending) {
      query.equalTo(Elephant.SYNC_STATE, Elephant.SyncState.Pending.name());
    } else if (searchMode == SearchMode.Saved) {
      query.notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
        .isNotNull(Elephant.DB_STATE)
        .isNull(Elephant.SYNC_STATE)
        .equalTo(Elephant.DRAFT, false);
    }
    return query.findAll();
  }

  public RealmResults<Elephant> searchElephants(Elephant e) {
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
    return query.findAll();
  }

  @Nullable
  public Elephant getElephantById(Integer id) {
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
    List<Document> documents = realm.where(Document.class)
      .equalTo(Document.ELEPHANT_ID, elephantId)
      .findAll();
    if (documents != null) {
      return realm.copyFromRealm(documents);
    }
    return null;
  }

  public RealmResults<Elephant> getLastVisitedElephant() {
    return realm.where(Elephant.class)
      .greaterThan(Elephant.LAST_VISITED, DateHelpers.getLastWeek())
      .findAllSorted(Elephant.LAST_VISITED, Sort.DESCENDING);
  }

  public Long getElephantsCount() {
    return realm.where(Elephant.class).count();
  }

  public Long getElephantsSyncStatePendingCount() {
    return realm.where(Elephant.class)
      .equalTo(Elephant.SYNC_STATE, Elephant.SyncState.Pending.name())
      .count();
  }

  public Long getElephantsReadyToSyncCount() {
    return realm.where(Elephant.class)
      .isNotNull(Elephant.DB_STATE)
      .isNull(Elephant.SYNC_STATE)
      .equalTo(Elephant.DRAFT, false)
      .count();
  }

  public Long getElephantsDraftCount() {
    return realm.where(Elephant.class)
      .equalTo(Elephant.DRAFT, true)
      .count();
  }

}
