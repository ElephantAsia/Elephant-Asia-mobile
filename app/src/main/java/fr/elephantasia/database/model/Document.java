package fr.elephantasia.database.model;

import org.parceler.Parcel;

import io.realm.DocumentRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Stephane on 26/05/2017.
 */

// TODO: discutez avec la team back end au sujet des champs bd
@Parcel(implementations = { DocumentRealmProxy.class })
public class Document extends RealmObject {
	@Ignore
	public static final String ID = "id";

	@Ignore
	public static final String PATH = "path";

	@Ignore
	public static final String TITLE = "title";

	@Ignore
	public static final String TYPE = "type"; // TODO: type_id ?

	@Ignore
	public static final String URL = "url";

	@Ignore
	public static final String ELEPHANT_ID = "elephant_id";

	@PrimaryKey
	public Integer id = -1;
	public String path;
	public String title;
	public String type;
	public String url;
	public Integer elephant_id;

	public Document() {}
}
