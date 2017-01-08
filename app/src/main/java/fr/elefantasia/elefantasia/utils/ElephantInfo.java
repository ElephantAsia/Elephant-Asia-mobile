package fr.elefantasia.elefantasia.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by care_j on 29/10/16.
 */

public class ElephantInfo {

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

    public List<String> chips;

    public String registrationID;
    public String registrationVillage;
    public String registrationDistrict;
    public String registrationProvince;

    //private String databaseNumber;
    //private Legallity legallyRegistered;

    public ElephantInfo() {
        name = "null";
        nickName = "null";
        sex = Gender.UNKNOWN;
        earTag = false;
        eyeD = false;
        birthDate = "null";
        birthVillage = "null";
        birthDistrict = "null";
        birthProvince = "null";
        chips = new ArrayList<>();
        registrationID = "null";
        registrationVillage = "null";
        registrationDistrict = "null";
        registrationProvince = "null";
    }

    /*String idNumber, List<String> chips,
    public ElephantInfo(String name, String nickname, Gender sex, boolean earTag, boolean eyeD,
                        String birthdate, String birthVillage, String birthDistrict, String birthP) {
        this.chips = new ArrayList<>();
        setName(name);
        setNickname(nickname);
        setRegistrationName(idNumber);
        setChips(chips);
        setSex(sex);
        setBirthdate(birthdate);
    }*/

    public void addChips(String item) {
        this.chips.clear();
        this.chips.add(item);
    }

    public void setChips(List<String> chips) {
        this.chips.addAll(chips);
    }

}