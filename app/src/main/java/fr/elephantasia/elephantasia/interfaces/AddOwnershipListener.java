package fr.elephantasia.elephantasia.interfaces;

import fr.elephantasia.elephantasia.utils.UserInfo;

/**
 * Created by Stephane on 16/03/2017.
 */

public interface AddOwnershipListener {

    void onAddClick();
    void onItemClick(UserInfo userInfo);

}
