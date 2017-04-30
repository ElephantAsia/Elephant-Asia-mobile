package fr.elephantasia.realm.parceler;

import android.os.Parcel;

import org.parceler.Parcels;

import fr.elephantasia.realm.model.Contact;
import fr.elephantasia.realm.model.Elephant;

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
