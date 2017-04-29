package fr.elephantasia.interfaces;

import fr.elephantasia.utils.UserInfo;

/**
 * Created by Stephane on 23/03/2017.
 */

public interface OwnershipListener {

  void onAddClick();

  void onItemClick(UserInfo userInfo);

}
