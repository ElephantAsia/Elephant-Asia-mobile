package fr.elephantasia.database.parceler;

import android.os.Parcel;

import org.parceler.Parcels;

import fr.elephantasia.database.model.Elephant;

/**
 * Created by seb on 30/04/2017.
 */

public class ElephantParcelConverter extends RealmListParcelConverter<Elephant> {
  @Override
  public void itemToParcel(Elephant input, Parcel parcel) {
    parcel.writeParcelable(Parcels.wrap(input), 0);
  }

  @Override
  public Elephant itemFromParcel(Parcel parcel) {
    return Parcels.unwrap(parcel.readParcelable(Elephant.class.getClassLoader()));
  }
}
