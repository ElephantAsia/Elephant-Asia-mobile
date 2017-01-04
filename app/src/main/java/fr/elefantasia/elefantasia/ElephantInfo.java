package fr.elefantasia.elefantasia;

/**
 * Created by care_j on 29/10/16.
 */

public class ElephantInfo {

    public boolean complete;
    private String name;
    private String databaseNumber;
    private String registrationName;
    private Legallity legallyRegistered;
    private Gender sex;
    private String bornDate;

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

    public ElephantInfo() {
        complete = false;
        name = "";
        databaseNumber = "";
        registrationName = "";
        legallyRegistered = Legallity.UNKNOWN;
        sex = Gender.UNKNOWN;
        bornDate = "0/0/0000";
    }

    public ElephantInfo(String name, String database, String registration, String born, Legallity legal, Gender sex) {
        complete = false;
        setName(name);
        setDatabaseNumber(database);
        setSex(sex);
        setLegallyRegistered(legal);
        setRegistrationName(registration);
        setBornDate(born);
    }

    public void setName(String newName) {
        name = newName;
        setComplete(isComplete());
    }

    public void setDatabaseNumber(String newDatabaseNumber) {
        databaseNumber = newDatabaseNumber;
        setComplete(isComplete());
    }

    public void setComplete(boolean isComplete) {
        complete = isComplete;
    }

    public void setRegistrationName(String newRegistrationName) {
        registrationName = newRegistrationName;
        setComplete(isComplete());
    }

    public void setLegallyRegistered(Legallity newLegallyRegistered) {
        legallyRegistered = newLegallyRegistered;
        setComplete(isComplete());
    }

    public void setSex(Gender newSex) {
        sex = newSex;
        setComplete(isComplete());
    }

    public void setBornDate(String newBornDate) {
        bornDate = newBornDate;
        setComplete(isComplete());
    }

    public String getName() {
        return (name);
    }

    public String getDatabaseNumber() {
        return (databaseNumber);
    }

    public String getRegistrationName() {
        return (registrationName);
    }

    public Legallity getLegallyRegistered() {
        return (legallyRegistered);
    }

    public Gender getSex() {
        return (sex);
    }

    public String getBornDate() {
        return (bornDate);
    }

    public boolean isComplete() {
        return (!name.equals("") &&
                !databaseNumber.equals("") &&
                !registrationName.equals("") &&
                legallyRegistered != Legallity.UNKNOWN &&
                sex != Gender.UNKNOWN &&
                !bornDate.equals("0/0/0000"));
    }

}