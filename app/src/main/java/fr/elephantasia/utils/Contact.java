package fr.elephantasia.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephane on 16/03/2017.
 */

public class Contact implements Parcelable {

  public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {

    @Override
    public Contact createFromParcel(Parcel in) {
      return new Contact(in);
    }

    @Override
    public Contact[] newArray(int size) {
      return new Contact[size];
    }
  };
  public int id;
  public String name;
  public Status status;
  public String email;
  public String address;

  public Contact() {
    id = 0;
    name = "username";
    status = Status.DEFAULT;
    email = "email@email.org";
    address = "address";
  }

  private Contact(int id, String username, Status status, String email, String address) {
    this.id = id;
    this.name = username;
    this.status = status;
    this.email = email;
    this.address = address;
  }

  private Contact(Parcel in) {
    id = in.readInt();
    name = in.readString();
    status = Status.valueOf(in.readString());
    email = in.readString();
    address = in.readString();
  }

  public static List<Contact> generateTestUser() {
    List<Contact> list = new ArrayList<>();

    list.add(new Contact(1, "jean thierry", Status.Admin, "jeanthierry@gmail.com", "10 rue tartanpion"));
    list.add(new Contact(2, "jean rachid", Status.Medic, "jeanrachid@gmail.com", "20 rue tartanpion"));
    list.add(new Contact(3, "azerty", Status.Medic, "azerty@gmail.com", "30 rue tartanpion"));
    list.add(new Contact(4, "admindu34", Status.Admin, "admindu34@gmail.com", "40 rue tartanpion"));
    list.add(new Contact(5, "XoX--Th3D4rkK1ll3r--XoX", Status.Medic, "darkkiller@gmail.com", "50 rue tartanpion"));

    return list;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(id);
    out.writeString(name);
    out.writeString(String.valueOf(status));
    out.writeString(email);
    out.writeString(address);
  }

  public enum Status {
    DEFAULT,
    Admin,
    Medic
  }

  public enum Gender {
    Man,
    Woman
  }

}
