package fr.elephantasia.database;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.database.model.ElephantNote;
import io.realm.RealmResults;

/**
 * Controller to manipulate the local database.
 * Create an indirection between the application and the database library used.
 **/
public class DatabaseController {

  public enum SearchMode {
    All,
    Pending,
    Draft,
    Saved
  }

  private RealmDB realmDB;

  public DatabaseController() {
    realmDB = new RealmDB();
  }

  public void beginTransaction() {
    realmDB.beginTransaction();
  }

  public void cancelTransaction() {
    realmDB.cancelTransaction();
  }

  public void commitTransaction() {
    realmDB.commitTransaction();
  }

  public void close() { realmDB.close(); }

  public void delete() {
    realmDB.delete();
  }

  /* Modifiers */

  public void insertOrUpdate(Elephant elephant) {
    realmDB.insertOrUpdate(elephant);
  }

  public void insertOrUpdate(Document document) {
    realmDB.insertOrUpdate(document);
  }

  public void insertOrUpdate(Contact contact) {
    realmDB.insertOrUpdate(contact);
  }

  public void insertOrUpdate(ElephantNote n) {
    realmDB.insertOrUpdate(n);
  }

  @Deprecated
  public void insertOrUpdate(Elephant elephant, List<Document> documents) {
    RealmDB.insertOrUpdateElephant(elephant, documents);
  }

  public void updateLastVisitedDateElephant(Integer id) {
    realmDB.updateLastVisitedDateElephant(id);
  }

  public void delete(Elephant elephant) {
    realmDB.delete(elephant);
  }

  /* Getters */

  @NonNull
  public List<Elephant> searchElephantsByState(SearchMode searchMode) {
    return realmDB.searchElephantsByState(searchMode);
  }

  @NonNull
  public List<Elephant> search(Elephant e) {
    return realmDB.search(e);
  }

  @NonNull
  public RealmResults<Contact> search(Contact c) {
    return realmDB.search(c);
  }

  @Nullable
  public Elephant getElephantById(Integer id) {
    return realmDB.getElephantById(id);
  }

  @Nullable
  public Elephant getElephantByCuid(String cuid) {
    return realmDB.getElephantByCuid(cuid);
  }

  @Nullable
  public Contact getContactByCuid(String cuid) { return realmDB.getContactByCuid(cuid); }

  @NonNull
  public List<ElephantNote> getElephantNoteByElephantId(Integer elephantId,
                                                        SortOrder dateOrder, SortOrder priorityOrder,
                                                        ElephantNote.Category categoryFilter, ElephantNote.Priority priorityFilter) {
    return realmDB.getElephantNoteByElephantId(elephantId, dateOrder, priorityOrder, categoryFilter, priorityFilter);
  }

  @NonNull
  public List<ElephantNote> getElephantNotesReadyToPushByElephantId(Integer elephantId) {
    return realmDB.getElephantNotesReadyToPushByElephantId(elephantId);
  }

  @Nullable
  public List<Document> getDocumentsByElephantId(Integer elephantId) {
    return realmDB.getDocumentsByElephantId(elephantId);
  }

  public List<Elephant> getLastVisitedElephant() {
    return realmDB.getLastVisitedElephant();
  }

  public List<Elephant> getElephantsReadyToUpload() {
    return realmDB.getElephantsReadyToUpload();
  }

  public List<Contact> getContactsReadyToSync() {
    return realmDB.getContactsReadyToSync();
  }

  public Long getElephantsCount() {
    return realmDB.getElephantsCount();
  }

  public Long getElephantsSyncStatePendingCount() {
    return realmDB.getElephantsSyncStatePendingCount();
  }

  public Long getElephantsReadyToSyncCount() {
    return realmDB.getElephantsReadyToSyncCount();
  }

  public Long getElephantsDraftCount() {
    return realmDB.getElephantsDraftCount();
  }


  public static class SortOrder {

    public static final SortOrder Ascending = new SortOrder(0);
    public static final SortOrder Descending = new SortOrder(1);
    public static final SortOrder None = new SortOrder(2);

    private static final Map<String, SortOrder> str2sort = new HashMap<String, SortOrder>() {{
      put("Ascending", Ascending);
      put("Descending", Descending);
      put("None", None);
    }};

    private Integer value;

    private SortOrder(Integer value) {
      this.value = value;
    }

    public boolean equals(SortOrder other) {
      return this.value.equals(other.value);
    }

    static public SortOrder valueOf(String value) {
      return str2sort.get(value);
    }

    public Integer getValue() {
      return value;
    }

  }

}
