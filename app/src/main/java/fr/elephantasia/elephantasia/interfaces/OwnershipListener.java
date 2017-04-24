package fr.elephantasia.elephantasia.interfaces;

import fr.elephantasia.elephantasia.utils.UserInfo;

/**
 * Created by Stephane on 23/03/2017.
 */

public interface OwnershipListener {

    void onAddClick();
    void onItemClick(UserInfo userInfo);

}
