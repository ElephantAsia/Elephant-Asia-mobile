package fr.elephantasia.interfaces;

import fr.elephantasia.utils.ElephantInfo;

/**
 * Created by Stephane on 08/01/2017.
 */

public interface AddElephantInterface {

  void nextPage();

  void setFather();

  void setMother();

  void addChildren();

  void onElephantClick(ElephantInfo elephant);

  void onAddDocumentClick();

}
