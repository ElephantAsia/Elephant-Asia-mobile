package fr.elephantasia.database;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import fr.elephantasia.database.DatabaseController.SortOrder;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.database.model.ElephantNote;
import fr.elephantasia.utils.DateHelpers;
import fr.elephantasia.utils.StaticTools;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Realm Facade
 * https://realm.io/docs/java/latest/
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
    realm = null;
    inTransaction = false;
  }

  void commitTransaction() {
    realm.commitTransaction();
    realm.close();
    realm = null;
    inTransaction = false;
  }

  void close() {
    if (realm != null) {
      realm.close();
      realm = null;
    }
  }

  void delete() {
    if (realm != null) {
      realm.close();
      realm = null;
    }
    Realm.deleteRealm(Realm.getDefaultConfiguration());
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

  void insertOrUpdate(Contact c) {
    begin();
    if (c.id == -1) {
      c.id = GetNextId(realm, Contact.class, Contact.ID);
    }
    realm.insertOrUpdate(c);
    end();
  }

  void insertOrUpdate(ElephantNote n) {
    begin();
    if (n.getId() == -1) {
      n.setId(GetNextId(realm, ElephantNote.class, ElephantNote.ID));
      n.setCreatedAt(DateHelpers.GetCurrentStringDate());
    }
    realm.insertOrUpdate(n);
    end();
  }

  void updateLastVisitedDateElephant(Integer id) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class)
      .equalTo(Elephant.ID, id)
      .findFirst();

    if (elephant != null) {
      realm.beginTransaction();
      elephant.lastVisited = new Date();
      realm.commitTransaction();
    }
    realm.close();
  }

  void delete(Elephant e) {
    begin();
    e.dbState = Elephant.DbState.Deleted.name();
    realm.insertOrUpdate(e);
    end();
  }

  @NonNull
  List<Elephant> searchElephantsByState(DatabaseController.SearchMode searchMode) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<Elephant> query = realm.where(Elephant.class);

    if (searchMode == DatabaseController.SearchMode.Draft) {
      query.equalTo(Elephant.DRAFT, true);
    } else if (searchMode == DatabaseController.SearchMode.Pending) {
      query.equalTo(Elephant.SYNC_STATE, Elephant.SyncState.Pending.name());
    } else if (searchMode == DatabaseController.SearchMode.Saved) {
      query.notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
        .isNotNull(Elephant.DB_STATE)
        .or()
        .isNotNull(Elephant.JOURNAL_STATE)
        .and()
        .isNull(Elephant.SYNC_STATE)
        .equalTo(Elephant.DRAFT, false);
    }
    List<Elephant> results = realm.copyFromRealm(query.findAll());
    realm.close();
    return results;
  }

  @NonNull
  List<Elephant> search(Elephant e) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<Elephant> query = realm.where(Elephant.class);

    // TODO: What we do with deleted elephants ?
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
    List<Elephant> results = realm.copyFromRealm(query.findAll());
    realm.close();
    return results;
  }

  @NonNull
  RealmResults<Contact> search(Contact c) { // TODO: return List
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
  Elephant getElephantById(Integer id) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class)
      .equalTo(Elephant.ID, id)
      .findFirst();

    if (elephant != null) {
      elephant = realm.copyFromRealm(elephant);
      realm.close();
      return elephant;
    }
    realm.close();
    return null;
  }

  @Nullable
  Elephant getElephantByCuid(String cuid) {
    Realm realm = Realm.getDefaultInstance();
    Elephant elephant = realm.where(Elephant.class)
      .equalTo(Elephant.CUID, cuid)
      .findFirst();
    if (elephant != null) {
      elephant = realm.copyFromRealm(elephant);
      realm.close();
      return elephant;
    }
    realm.close();
    return null;
  }

  @Nullable
  Contact getContactByCuid(String cuid) {
    Realm realm = Realm.getDefaultInstance();
    Contact contact = realm.where(Contact.class)
      .equalTo(Contact.CUID, cuid)
      .findFirst();
    if (contact != null) {
      contact = realm.copyFromRealm(contact);
      realm.close();
      return contact;
    }
    realm.close();
    return null;
  }

  @NonNull
  List<ElephantNote> getElephantNoteByElephantId(Integer elephantId,
                                                 SortOrder dateOrder, SortOrder priorityOrder,
                                                 ElephantNote.Category categoryFilter, ElephantNote.Priority priorityFilter) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<ElephantNote> query = realm.where(ElephantNote.class)
      .equalTo(ElephantNote.ELEPHANT_ID, elephantId);

    if (dateOrder != SortOrder.None || priorityOrder != SortOrder.None) {
      if (dateOrder != SortOrder.None && priorityOrder != SortOrder.None) {
        query.sort(
          ElephantNote.PRIORITY,
          (priorityOrder == SortOrder.Ascending) ? Sort.ASCENDING : Sort.DESCENDING,
          ElephantNote.CREATED_AT,
          (dateOrder == SortOrder.Ascending) ? Sort.ASCENDING : Sort.DESCENDING
        );
      } else {
        if (dateOrder != SortOrder.None) {
          query.sort(
            ElephantNote.CREATED_AT,
            (dateOrder == SortOrder.Ascending) ? Sort.ASCENDING : Sort.DESCENDING
          );
        } else {
          query.sort(
            ElephantNote.PRIORITY,
            (priorityOrder == SortOrder.Ascending) ? Sort.ASCENDING : Sort.DESCENDING
          );
        }
      }
    }

    if (!categoryFilter.equals(ElephantNote.Category.None)) {
      query.equalTo(ElephantNote.CATEGORY, categoryFilter.toString());
    }
    if (!priorityFilter.equals(ElephantNote.Priority.None)) {
      query.equalTo(ElephantNote.PRIORITY, priorityFilter.getValue());
    }

    List<ElephantNote> notes = query.findAll();
    if (notes != null) {
      notes = realm.copyFromRealm(notes);
      realm.close();
      return notes;
    }
    realm.close();
    return new ArrayList<>();
  }

  @NonNull
  List<ElephantNote> getElephantNotesReadyToPushByElephantId(Integer elephantId) {
    Realm realm = Realm.getDefaultInstance();
    List<ElephantNote> notes = realm.where(ElephantNote.class)
      .equalTo(ElephantNote.ELEPHANT_ID, elephantId)
      .isNotNull(ElephantNote.DB_STATE)
      .findAll();

    if (notes != null) {
      notes = realm.copyFromRealm(notes);
      realm.close();
      return notes;
    }
    realm.close();
    return new ArrayList<>();
  }

  @Nullable
  List<Document> getDocumentsByElephantId(Integer elephantId) {
    Realm realm = Realm.getDefaultInstance();
    List<Document> documents = realm.where(Document.class)
      .equalTo(Document.ELEPHANT_ID, elephantId)
      .findAll();

    if (documents != null) {
      documents = realm.copyFromRealm(documents);
      realm.close();
      return documents;
    }
    realm.close();
    return null;
  }

  List<Elephant> getLastVisitedElephant() {
    Realm realm = Realm.getDefaultInstance();
    List<Elephant> results = realm.copyFromRealm(
      realm.where(Elephant.class)
      .greaterThan(Elephant.LAST_VISITED, DateHelpers.getLastWeek())
      .sort(Elephant.LAST_VISITED, Sort.DESCENDING)
      .notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
      .findAll()
    );
    realm.close();
    return results;
  }

  List<Elephant> getElephantsReadyToUpload() {
    Realm realm = Realm.getDefaultInstance();
    List<Elephant> elephants = realm.copyFromRealm(
      realm.where(Elephant.class)
      .isNotNull(Elephant.DB_STATE)
      .or()
      .isNotNull(Elephant.JOURNAL_STATE)
      .and()
      .notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
      .isNull(Elephant.SYNC_STATE)
      .equalTo(Elephant.DRAFT, false)
      .findAll()
    );
    realm.close();
    return elephants;
  }

  Long getElephantsCount() {
    Realm realm = Realm.getDefaultInstance();
    Long count = realm.where(Elephant.class).count();
    realm.close();
    return count;
  }

  Long getElephantsSyncStatePendingCount() {
    Realm realm = Realm.getDefaultInstance();
    Long count = realm.where(Elephant.class)
      .equalTo(Elephant.SYNC_STATE, Elephant.SyncState.Pending.name())
      .notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
      .count();
    realm.close();
    return count;
  }

  Long getElephantsReadyToSyncCount() {
    Realm realm = Realm.getDefaultInstance();
    Long count = realm.where(Elephant.class)
      .isNotNull(Elephant.DB_STATE)
      .or()
      .isNotNull(Elephant.JOURNAL_STATE)
      .and()
      .notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
      .isNull(Elephant.SYNC_STATE)
      .equalTo(Elephant.DRAFT, false)
      .count();
    realm.close();
    return count;
  }

  Long getElephantsDraftCount() {
    Realm realm = Realm.getDefaultInstance();
    Long count = realm.where(Elephant.class)
      .equalTo(Elephant.DRAFT, true)
      .notEqualTo(Elephant.DB_STATE, Elephant.DbState.Deleted.name())
      .count();
    realm.close();
    return count;
  }

	@Deprecated // TODO: rework that with documents
  static void insertOrUpdateElephant(final Elephant elephant, final List<Document> documents) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(@NonNull Realm bgRealm) {
        if (elephant.id == -1) {
          elephant.id = GetNextId(bgRealm, Elephant.class, Elephant.ID);
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
    return StaticTools.increment(realm.where(cls).max(columnIdName)); // TODO: remove StaticTools
  }

}
