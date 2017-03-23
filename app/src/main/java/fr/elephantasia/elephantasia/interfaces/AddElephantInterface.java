package fr.elephantasia.elephantasia.interfaces;

import fr.elephantasia.elephantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 08/01/2017.
 */

public interface AddElephantInterface {

    void nextPage();

    void addOwner();

    void setFather();
    void setMother();
    void addChildren();

    void onElephantClick(ElephantInfo elephant);

    void onAddDocumentClick();

}
