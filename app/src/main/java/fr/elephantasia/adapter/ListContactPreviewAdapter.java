package fr.elephantasia.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.databinding.ContactPreviewBinding;
import fr.elephantasia.realm.model.Contact;
import io.realm.RealmList;


public class ListContactPreviewAdapter extends ArrayAdapter<Contact> {

  private RealmList<Contact> contacts;
  private boolean isRemovable;

  @BindView(R.id.remove_contact) ImageButton removeButton;

  @OnClick(R.id.remove_contact)
  public void removeContact(View v) {
    int position = (int)v.getTag();
    contacts.remove(getItem(position));
    notifyDataSetChanged();
  }

  public ListContactPreviewAdapter(Context context, RealmList<Contact> contacts, boolean isRemovable) {
    super(context, R.layout.contact_preview , contacts);
    this.contacts = contacts;
    this.isRemovable = isRemovable;
  }

  public View getView(int position, View view, ViewGroup parent) {
    ContactPreviewBinding binding;

    if(view == null) {
      binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.contact_preview, parent, false);
      view = binding.getRoot();
    } else {
      binding = (ContactPreviewBinding) view.getTag();
    }

    ButterKnife.bind(this, view);

    if (isRemovable) {
      removeButton.setTag(position);
      removeButton.setVisibility(View.VISIBLE);
    }

    binding.setC(this.getItem(position));
    view.setTag(binding);
    return view;
  }
}


