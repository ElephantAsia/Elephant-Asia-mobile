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
    public String registrationName;
    public List<String> chips;
    //private String databaseNumber;
    //private Legallity legallyRegistered;
    public Gender sex;
    public String birthDate;

    public ElephantInfo() {
        //complete = false;
        name = "";
        nickName = "";
        registrationName = "";
        chips = new ArrayList<>();
        /*databaseNumber = "";
        registrationName = "";
        legallyRegistered = Legallity.UNKNOWN;*/
        sex = Gender.UNKNOWN;
        birthDate = "0/0/0000";
    }

    public ElephantInfo(String name, String nickname, String idNumber, List<String> chips, Gender sex, String birthdate) {
        this.chips = new ArrayList<>();
        setName(name);
        setNickname(nickname);
        setRegistrationName(idNumber);
        setChips(chips);
        setSex(sex);
        setBirthdate(birthdate);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickName) {
        this.nickName = nickName;
    }

    public void setRegistrationName(String idNumber) {
        this.registrationName = idNumber;
    }

    public void addChips(String item) {
        this.chips.clear();
        this.chips.add(item);
    }

    public void setChips(List<String> chips) {
        this.chips.addAll(chips);
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public void setBirthdate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        if (chips.size() > 0) {
            return ("name: " + name + " nickname: " + nickName + " id: " + registrationName + " chip: " + chips.get(0)
                    + " sex: " + ((sex == Gender.FEMALE) ? "female" : "male") + " birthdate: " + birthDate);
        }
        return super.toString();
    }

}