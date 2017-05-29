package fr.elephantasia.database.model;

import org.parceler.Parcel;

import java.util.UUID;

import io.realm.DocumentRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Stephane on 26/05/2017.
 */

@Parcel(implementations = {DocumentRealmProxy.class})
public class Document extends RealmObject {
  @Ignore public static final String PATH = "path";
  @Ignore public static final String ID_ELEPHANT = "id_elephant";

  @PrimaryKey
  public String id = UUID.randomUUID().toString();
  public String id_elephant;
  public String path;
}
