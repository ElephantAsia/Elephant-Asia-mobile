package fr.elephantasia.interfaces;

import fr.elephantasia.utils.Contact;

/**
 * Created by Stephane on 23/03/2017.
 */

public interface ContactListener {

  void onAddClick();

  void onItemClick(Contact contact);

}
