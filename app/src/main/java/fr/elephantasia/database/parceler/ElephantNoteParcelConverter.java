package fr.elephantasia.database.parceler;

import android.os.Parcel;

import org.parceler.Parcels;

import fr.elephantasia.database.model.ElephantNote;

public class ElephantNoteParcelConverter extends RealmListParcelConverter<ElephantNote> {
  @Override
  public void itemToParcel(ElephantNote input, Parcel parcel) {
    parcel.writeParcelable(Parcels.wrap(input), 0);
  }

  @Override
  public ElephantNote itemFromParcel(Parcel parcel) {
    return Parcels.unwrap(parcel.readParcelable(ElephantNote.class.getClassLoader()));
  }
}
