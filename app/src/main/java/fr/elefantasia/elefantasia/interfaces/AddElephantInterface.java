package fr.elefantasia.elefantasia.interfaces;

import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 08/01/2017.
 */

public interface AddElephantInterface {

    void nextPage();

    void setName(String name);
    void setNickname(String nickname);
    void hasEarTag(boolean value);
    void hasEyeD(boolean value);

    void setBirthdate(String date);
    void setBirthVillage(String village);
    void setBirthDistrict(String district);
    void setBirthProvince(String province);

    void setChipNumber(String value);

    void setRegistrationID(String id);
    void setRegistrationVillage(String village);
    void setRegistrationDistrict(String district);
    void setRegistrationProvince(String province);
}
