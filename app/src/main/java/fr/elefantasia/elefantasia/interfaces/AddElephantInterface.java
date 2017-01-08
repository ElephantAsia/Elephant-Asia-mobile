package fr.elefantasia.elefantasia.interfaces;

import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 08/01/2017.
 */

public interface AddElephantInterface {

    void next();

    void setElephantName(String name);
    void setElephantNickname(String nickname);
    void setElephantIDNumber(String number);
    void setElephantChipNumber(String value);
    void setElephantSex(ElephantInfo.Gender sex);
    void setElephantBirthdate(String birthdate);

}
