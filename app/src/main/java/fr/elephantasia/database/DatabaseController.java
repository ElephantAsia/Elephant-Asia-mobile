package fr.elephantasia.database;

import android.support.annotation.NonNull;

import java.util.List;

import javax.annotation.Nullable;

import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
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

  public void insertOrUpdate(Elephant elephant, List<Document> documents) {
    RealmDB.insertOrUpdateElephant(elephant, documents);
  }

  public void updateLastVisitedDateElephant(Integer id) {
    realmDB.updateLastVisitedDateElephant(id);
  }

  public void copyOrUpdate(Contact contact) {
    realmDB.copyOrUpdate(contact);
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
  public List<Document> getDocumentsByElephantId(Integer elephantId) {
    return realmDB.getDocumentsByElephantId(elephantId);
  }

  public List<Elephant> getLastVisitedElephant() {
    return realmDB.getLastVisitedElephant();
  }

  public List<Elephant> getElephantsReadyToUpload() {
    return realmDB.getElephantsReadyToUpload();
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

}
