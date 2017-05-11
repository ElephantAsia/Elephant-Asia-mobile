package fr.elephantasia.fragment.addElephant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.SearchContactActivity;
import fr.elephantasia.adapter.ListContactPreviewAdapter;
import fr.elephantasia.realm.model.Contact;
import io.realm.RealmList;

import static butterknife.ButterKnife.findById;
import static fr.elephantasia.constants.ActivityResult.CONTACT_SELECTED;

public class ContactFragment extends Fragment {

  @BindView(R.id.list) ListView list;

  private ListContactPreviewAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.add_elephant_contact_fragment, container, false);
    View footerView = inflater.inflate(R.layout.add_contact_list_footer, list, false);
    TextView addButton = findById(footerView, R.id.add_contact);

    addButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchContactActivity.class);
        getActivity().startActivityForResult(intent, CONTACT_SELECTED);
      }
    });

    ButterKnife.bind(this, view);
    adapter = new ListContactPreviewAdapter(getContext(), new RealmList<Contact>(), true);
    list.addFooterView(footerView);
    list.setAdapter(adapter);
    return (view);
  }

  public void addContactTolist(Contact contact) {
    adapter.add(contact);
  }
}