package fr.elephantasia.elephantasia.interfaces;

import fr.elephantasia.elephantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 09/01/2017.
 */

public interface ConsultationInterface {

    ElephantInfo getElephantInfo();
    void updateElephant(ElephantInfo info);
    void deleteElephant(ElephantInfo info);

}
