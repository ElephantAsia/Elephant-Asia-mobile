package fr.elefantasia.elefantasia.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by care_j on 29/10/16.
 */

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

    public Integer id;
    public String name;
    public String nickName;
    public Gender sex;
    public boolean earTag;
    public boolean eyeD;

    public String birthDate;
    public String birthVillage;
    public String birthDistrict;
    public String birthProvince;

    public String chips1;
    public String chips2;
    public String chips3;

    public String registrationID;
    public String registrationVillage;
    public String registrationDistrict;
    public String registrationProvince;

    public ElephantInfo() {
        id = 0;
        name = "null";
        nickName = "null";
        sex = Gender.UNKNOWN;
        earTag = false;
        eyeD = false;
        birthDate = "null";
        birthVillage = "null";
        birthDistrict = "null";
        birthProvince = "null";
        chips1 = "null";
        chips2 = "";
        chips3 = "";
        registrationID = "null";
        registrationVillage = "null";
        registrationDistrict = "null";
        registrationProvince = "null";
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
        registrationID = other.registrationID;
        registrationVillage = other.registrationVillage;
        registrationDistrict = other.registrationDistrict;
        registrationProvince = other.registrationProvince;
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
        out.writeString(registrationID);
        out.writeString(registrationVillage);
        out.writeString(registrationDistrict);
        out.writeString(registrationProvince);
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
        registrationID = in.readString();
        registrationVillage = in.readString();
        registrationDistrict = in.readString();
        registrationProvince = in.readString();
    }

}