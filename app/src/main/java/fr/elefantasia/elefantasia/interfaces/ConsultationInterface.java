package fr.elefantasia.elefantasia.interfaces;

import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 09/01/2017.
 */

public interface ConsultationInterface {

    ElephantInfo getElephantInfo();
    void updateElephant(ElephantInfo info);

}
