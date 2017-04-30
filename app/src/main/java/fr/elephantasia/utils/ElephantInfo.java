package fr.elephantasia.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;


public class ElephantInfo implements Parcelable {

  public static final Parcelable.Creator<ElephantInfo> CREATOR = new Parcelable.Creator<ElephantInfo>() {

    @Override
    public ElephantInfo createFromParcel(Parcel in) {
      return new ElephantInfo(in);
    }

    @Override
    public ElephantInfo[] newArray(int size) {
      return new ElephantInfo[size];
    }
  };
  public Integer id;
  public State state;
  //Profil
  public String name;
  public String nickName;
  public Gender sex;
  public String currentCity;
  public String currentDistrict;
  public String currentProvince;
  public String birthDate;
  public String birthCity;
  public String birthDistrict;
  public String birthProvince;
  // Registration
  public boolean earTag = false;
  public boolean eyeD = false;
  public String chips1;
  public String chips2;
  public String chips3;
  public String regID;
  public String regCity;
  public String regDistrict;
  public String regProvince;
  // Description
  public String tusk;
  public String nailsFrontLeft;
  public String nailsFrontRight;
  public String nailsRearLeft;
  public String nailsRearRight;
  public String weight;
  public String height;
  // Parentage
  public String father;
  public String mother;
  // Owner
  private String owners;
  private String children;

  public ElephantInfo() {
    id = 0;
    state = State.DEFAULT;

    // Profil
    name = "";
    nickName = "";
    sex = Gender.UNKNOWN;
    currentCity = "";
    currentDistrict = "";
    currentProvince = "";
    birthDate = "";
    birthCity = "";
    birthDistrict = "";
    birthProvince = "";

    // Registration
    earTag = false;
    eyeD = false;
    chips1 = "";
    chips2 = "";
    chips3 = "";
    regID = "";
    regCity = "";
    regDistrict = "";
    regProvince = "";

    // Description
    tusk = "";
    nailsFrontLeft = "";
    nailsFrontRight = "";
    nailsRearLeft = "";
    nailsRearRight = "";
    weight = "";
    height = "";

    // Owner
    owners = "";

    // Parentage
    father = "";
    mother = "";
    children = "";
  }

  public ElephantInfo(ElephantInfo other) {
    id = other.id;
    state = other.state;

    // Profil
    name = other.name;
    nickName = other.nickName;
    sex = other.sex;
    regCity = other.currentCity;
    regDistrict = other.currentDistrict;
    regProvince = other.currentProvince;
    birthDate = other.birthDate;
    birthCity = other.birthCity;
    birthDistrict = other.birthDistrict;
    birthProvince = other.birthProvince;

    // Registration
    earTag = other.earTag;
    eyeD = other.eyeD;
    chips1 = other.chips1;
    chips2 = other.chips2;
    chips3 = other.chips3;
    regID = other.regID;
    regCity = other.regCity;
    regDistrict = other.regDistrict;
    regProvince = other.regProvince;

    // Description
    tusk = other.tusk;
    nailsFrontLeft = other.nailsFrontLeft;
    nailsFrontRight = other.nailsFrontRight;
    nailsRearLeft = other.nailsRearLeft;
    nailsRearRight = other.nailsRearRight;
    weight = other.weight;
    height = other.height;

    // Owners
    owners = other.owners;

    // Parentage
    father = other.father;
    mother = other.mother;
    children = other.children;
  }

  private ElephantInfo(Parcel in) {
    id = in.readInt();
    state = State.valueOf(in.readString());

    // Profil
    name = in.readString();
    nickName = in.readString();
    sex = Gender.valueOf(in.readString());
    currentCity = in.readString();
    currentDistrict = in.readString();
    currentProvince = in.readString();
    birthDate = in.readString();
    birthCity = in.readString();
    birthDistrict = in.readString();
    birthProvince = in.readString();

    // Registration
    earTag = Boolean.valueOf(in.readString());
    eyeD = Boolean.valueOf(in.readString());
    chips1 = in.readString();
    chips2 = in.readString();
    chips3 = in.readString();
    regID = in.readString();
    regCity = in.readString();
    regDistrict = in.readString();
    regProvince = in.readString();

    // Description
    tusk = in.readString();
    nailsFrontLeft = in.readString();
    nailsFrontRight = in.readString();
    nailsRearLeft = in.readString();
    nailsRearRight = in.readString();
    weight = in.readString();
    height = in.readString();

    // Owners
    owners = in.readString();

    // Parentage
    father = in.readString();
    mother = in.readString();
    children = in.readString();
  }

  public String getSex() {
    String sex = this.sex.toString();
    return sex.substring(0, 1).toUpperCase() + sex.substring(1, sex.length()).toLowerCase();
  }


  public boolean isMale() {
    return this.sex == Gender.MALE;
  }

  public boolean isFemale() {
    return this.sex == Gender.FEMALE;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(id);
    out.writeString(String.valueOf(state));

    // Profil
    out.writeString(name);
    out.writeString(nickName);
    out.writeString(String.valueOf(sex));
    out.writeString(currentCity);
    out.writeString(currentDistrict);
    out.writeString(currentProvince);
    out.writeString(birthDate);
    out.writeString(birthCity);
    out.writeString(birthDistrict);
    out.writeString(birthProvince);

    // Registration
    out.writeString(String.valueOf(earTag));
    out.writeString(String.valueOf(eyeD));
    out.writeString(chips1);
    out.writeString(chips2);
    out.writeString(chips3);
    out.writeString(regID);
    out.writeString(regCity);
    out.writeString(regDistrict);
    out.writeString(regProvince);

    // Description
    out.writeString(tusk);
    out.writeString(nailsFrontLeft);
    out.writeString(nailsFrontRight);
    out.writeString(nailsRearLeft);
    out.writeString(nailsRearRight);
    out.writeString(weight);
    out.writeString(height);

    // Owners
    out.writeString(owners);

    // Parentage
    out.writeString(father);
    out.writeString(mother);
    out.writeString(children);
  }

  /**
   * Used to check if an elephant should be saved as draft before
   * the end of AddElephantActivity.
   *
   * @return true if all relevant fields are empty.
   */
  public boolean isEmpty() {
    return name.isEmpty() && nickName.isEmpty()
        && currentCity.isEmpty() && currentDistrict.isEmpty()
        && currentProvince.isEmpty()
        && birthDate.isEmpty() && birthCity.isEmpty()
        && birthDistrict.isEmpty() && birthProvince.isEmpty()
        && chips1.isEmpty() && regID.isEmpty()
        && regCity.isEmpty() && regDistrict.isEmpty()
        && regProvince.isEmpty() && tusk.isEmpty()
        && nailsFrontLeft.isEmpty() && nailsFrontRight.isEmpty()
        && nailsRearLeft.isEmpty() && nailsRearRight.isEmpty()
        && weight.isEmpty() && height.isEmpty()
        && owners.isEmpty() && father.isEmpty()
        && mother.isEmpty() && children.isEmpty();
  }

  public void addChildren(int elephantID) {
    if (children.isEmpty()) {
      children = String.valueOf(elephantID);
    } else {
      children += ";" + elephantID;
    }
  }

  public String getChildren() {
    return children;
  }

  public void setChildren(String children) {
    this.children = children;
  }

  public List<String> getChildrenAsList() {
    return Arrays.asList(children.split(";"));
  }

  public void addOwner(int ownerID) {
    if (owners.isEmpty()) {
      owners = String.valueOf(ownerID);
    } else {
      owners += ";" + ownerID;
    }
  }

  public String getOwners() {
    return owners;
  }

  public void setOwners(String owners) {
    this.owners = owners;
  }

  public List<String> getOwnersAsList() {
    return Arrays.asList(owners.split(";"));
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof ElephantInfo
        && ((ElephantInfo) other).id.equals(id)
        && ((ElephantInfo) other).regID.equals(regID);
  }

  @Override
  public int hashCode() {
    return id.hashCode() + regID.hashCode();
  }

  public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN
  }

  public enum Legallity {
    LEGAL,
    ILLEGAL,
    UNKNOWN
  }

  public enum State {
    DEFAULT,
    PENDING,
    DELETED,
    LOCAL,
    DRAFT
  }

}