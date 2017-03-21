package fr.elephantasia.elephantasia.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;


public class ElephantInfo implements Parcelable {

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
        DRAFT
    }

    public void setSex(Gender gender) {
        this.sex = Gender.MALE;
    }

    //Registration
    public Integer id;
    public String name;
    public String nickName;

    public Gender sex;
    public State state;

    public boolean earTag = false;
    public boolean eyeD = false;

    public String birthDate;
    public String birthVillage;
    public String birthDistrict;
    public String birthProvince;

    public String chips1;
    public String chips2;
    public String chips3;

    public String regID;
    public String regVillage;
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

    // Owner
    private String owners;

    // Parentage
    public String father;
    public String mother;
    private String children;

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


    public ElephantInfo() {
        id = 0;
        // Registration
        name = "";
        nickName = "";
        sex = Gender.UNKNOWN;
        state = State.DEFAULT;
        earTag = false;
        eyeD = false;
        birthDate = "";
        birthVillage = "";
        birthDistrict = "";
        birthProvince = "";
        chips1 = "";
        chips2 = "";
        chips3 = "";
        regID = "";
        regVillage = "";
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
        name = other.name;
        nickName = other.nickName;
        sex = other.sex;
        state = other.state;
        earTag = other.earTag;
        eyeD = other.eyeD;
        birthDate = other.birthDate;
        birthVillage = other.birthVillage;
        birthDistrict = other.birthDistrict;
        birthProvince = other.birthProvince;
        chips1 = other.chips1;
        chips2 = other.chips2;
        chips3 = other.chips3;
        regID = other.regID;
        regVillage = other.regVillage;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(nickName);
        out.writeString(String.valueOf(sex));
        out.writeString(String.valueOf(state));
        out.writeString(String.valueOf(earTag));
        out.writeString(String.valueOf(eyeD));
        out.writeString(birthDate);
        out.writeString(birthVillage);
        out.writeString(birthDistrict);
        out.writeString(birthProvince);
        out.writeString(chips1);
        out.writeString(chips2);
        out.writeString(chips3);
        out.writeString(regID);
        out.writeString(regVillage);
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

    private ElephantInfo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        nickName = in.readString();
        sex = Gender.valueOf(in.readString());
        state = State.valueOf(in.readString());
        earTag = Boolean.valueOf(in.readString());
        eyeD = Boolean.valueOf(in.readString());
        birthDate = in.readString();
        birthVillage = in.readString();
        birthDistrict = in.readString();
        birthProvince = in.readString();
        chips1 = in.readString();
        chips2 = in.readString();
        chips3 = in.readString();
        regID = in.readString();
        regVillage = in.readString();
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

    public boolean isValid() {
        return name != null && !name.isEmpty();
    }

    public void displayAttr() {
        Log.i("name", this.name);
        Log.i("nickName", this.nickName);
        Log.i("state", this.state.toString());
        Log.i("sex", this.sex.toString());
        Log.i("earTag", String.valueOf(this.earTag));
        Log.i("eyeD", String.valueOf(this.eyeD));
        Log.i("birthDate", this.birthDate);
        Log.i("birthVillage", this.birthVillage);
        Log.i("birthDistrict", this.birthDistrict);
        Log.i("birthProvince", this.birthProvince);
        Log.i("chips1", this.chips1);
        Log.i("regID", this.regID);
        Log.i("regVillage", this.regVillage);
        Log.i("regDistrict", this.regDistrict);
        Log.i("regProvince", this.regProvince);

        // Description
        Log.i("tusk", this.tusk);
        Log.i("nails front left", this.nailsFrontLeft);
        Log.i("nails front right", this.nailsFrontRight);
        Log.i("nails rear left", this.nailsRearLeft);
        Log.i("nails rear right", this.nailsRearRight);
        Log.i("weight", this.weight);
        Log.i("height", this.weight);

        // Owners
        Log.i("owners", owners);

        // Parentage
        Log.i("father", father);
        Log.i("mother", mother);
        Log.i("children", children);
    }

    public void addChildren(int elephantID) {
        if (children.isEmpty()) {
            children = String.valueOf(elephantID);
        } else {
            children += ";" + elephantID;
        }
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getChildren() {
        return children;
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

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getOwners() {
        return owners;
    }

    public List<String> getOwnersAsList() {
        return Arrays.asList(owners.split(";"));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ElephantInfo
                && ((ElephantInfo)other).id.equals(id)
                && ((ElephantInfo)other).regID.equals(regID);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + regID.hashCode();
    }

}