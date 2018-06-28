package fr.elephantasia.activities.manageElephant.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.contact.SearchContactActivity;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.adapter.ContactPreviewAdapter;
import fr.elephantasia.database.model.Contact;
import fr.elephantasia.database.model.Elephant;

import static butterknife.ButterKnife.findById;
import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.REQUEST_CONTACT_SELECTED;

public class ContactFragment extends Fragment {

  // View binding
  @BindView(R.id.list) ListView list;

  // Attr
  private ContactPreviewAdapter adapter;
  private Elephant elephant;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((ManageElephantActivity) getActivity()).getElephant();
    adapter = new ContactPreviewAdapter(getContext(), elephant.contacts, true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.manage_elephant_contact_fragment, container, false);
    View footerView = inflater.inflate(R.layout.add_button_footer_list, list, false);

    TextView addButton = findById(footerView, R.id.add_button_footer);
    int color = ResourcesCompat.getColor(getResources(), R.color.primary_50, null);
    addButton.setCompoundDrawables(new IconicsDrawable(getActivity())
        .icon(MaterialDesignIconic.Icon.gmi_plus)
        .color(color).sizeDp(40), null, null, null);

    addButton.setHint(getString(R.string.add_contact));
    addButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchContactActivity.class);
        getActivity().startActivityForResult(intent, REQUEST_CONTACT_SELECTED);
      }
    });

    ButterKnife.bind(this, view);
    list.addFooterView(footerView);
    list.setAdapter(adapter);
    return (view);
  }

  public void addContactTolist(Contact contact) {
    adapter.add(contact);
  }
}