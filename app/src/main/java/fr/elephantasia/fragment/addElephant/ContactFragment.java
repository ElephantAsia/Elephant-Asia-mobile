package fr.elephantasia.fragment.addElephant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.elephantasia.R;
import fr.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.activities.SelectContactActivity;
import fr.elephantasia.adapter.ContactAdapter;
import fr.elephantasia.dialogs.AddContactDialog;
import fr.elephantasia.interfaces.ContactListener;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.utils.Contact;

public class ContactFragment extends Fragment {
  private Elephant elephant;
  private ListView list;
  private ContactAdapter adapter;

  public void addContact() {
    Intent intent = new Intent(getActivity(), SelectContactActivity.class);
    startActivityForResult(intent, AddElephantActivity.REQUEST_ADD_CONTACT);
  }

  private void refreshContact(Contact user) {
//    ContactFragment fragment = (ContactFragment) adapter.getItem(FRAGMENT_OWNERSHIP_IDX);

//    elephant.addOwner(user.id);
//    fragment.refreshOwner(user);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((AddElephantActivity) getActivity()).getElephant();

    adapter = new ContactAdapter(getContext(), true, new ContactListener() {
      @Override
      public void onAddClick() {
        AddContactDialog dialog = new AddContactDialog(getActivity(), elephant, 0);
        dialog.show();
//        addContact();
      }

      @Override
      public void onItemClick(Contact userInfo) {
        Log.i("click", userInfo.name);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.add_elephant_contact_fragment, container, false);

    list = (ListView) view.findViewById(R.id.list);
    list.setAdapter(adapter);

    return (view);
  }

  public void refreshOwner(Contact user) {
    adapter.addItem(user);
  }

}