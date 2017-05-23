package fr.elephantasia.database.parceler;

import android.os.Parcel;

import org.parceler.Parcels;

import fr.elephantasia.database.model.Contact;

/**
 * Created by seb on 30/04/2017.
 */

public class ContactParcelConverter extends RealmListParcelConverter<Contact> {
  @Override
  public void itemToParcel(Contact input, Parcel parcel) {
    parcel.writeParcelable(Parcels.wrap(input), 0);
  }

  @Override
  public Contact itemFromParcel(Parcel parcel) {
    return Parcels.unwrap(parcel.readParcelable(Contact.class.getClassLoader()));
  }
}
