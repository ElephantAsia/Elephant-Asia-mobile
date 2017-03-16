package fr.elephantasia.elephantasia.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


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

    public void setSex(Gender gender) {
        this.sex = Gender.MALE;
    }

    public Integer id;
    public String name;
    public String nickName;
    public Gender sex ;
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
        name = "";
        nickName = "";
        sex = Gender.UNKNOWN;
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
    }

    public ElephantInfo(ElephantInfo other) {
        id = other.id;
        name = other.name;
        nickName = other.nickName;
        sex = other.sex;
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
    }


    public void displayAttr() {
        Log.i("name", this.name);
        Log.i("nickName", this.nickName);
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
    }

}