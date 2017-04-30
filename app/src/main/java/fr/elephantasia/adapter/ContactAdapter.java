package fr.elephantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.interfaces.ContactListener;
import fr.elephantasia.utils.Contact;

public class ContactAdapter extends BaseAdapter {

  private Context context;
  private boolean showFooter;
  private ContactListener listener;
  private List<Contact> contacts;

  public ContactAdapter(Context context, boolean showFooter, ContactListener listener) {
    this.context = context;
    this.showFooter = showFooter;
    this.listener = listener;
    contacts = new ArrayList<>();
  }

  public void setData(List<Contact> contacts) {
//    this.contacts = contacts;
//    notifyDataSetChanged();
  }

  public void addItem(Contact contact) {
//    contacts.add(contact);
//    notifyDataSetChanged();
  }

  public void clear() {
    this.contacts.clear();
  }

  @Override
  public int getCount() {
    return (contacts.size() + ((showFooter) ? 1 : 0));
  }

  @Override
  public Contact getItem(int index) {
//    Contact ret = null;
//
//    if (index < contacts.size()) {
//      ret = contacts.get(index);
//    }
//    return (ret);
    return null;
  }

  @Override
  public long getItemId(int index) {
    return index;
  }

  @Override
  public View getView(final int index, View old, ViewGroup parent) {
    View view;
    boolean creation = (old == null);
    boolean isFooter = isFooter(index);

    if (!creation) {
      view = old;
    } else {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.contact_preview, parent, false);
    }

    Contact contact = getItem(index);

    refreshView(view, contact, isFooter);
    if (contact != null) {
      refreshUser(view, contact);
    }

    return view;
  }

  private void refreshView(View view, final Contact contact, boolean footer) {
    if (footer) {
      view.findViewById(R.id.footer_parent).setVisibility(View.VISIBLE);
      view.findViewById(R.id.owner_parent).setVisibility(View.GONE);

      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          listener.onAddClick();
        }
      });
    } else {
      view.findViewById(R.id.footer_parent).setVisibility(View.GONE);
      view.findViewById(R.id.owner_parent).setVisibility(View.VISIBLE);

      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          listener.onItemClick(contact);
        }
      });
    }
  }

  private void refreshUser(View view, Contact user) {
    ((TextView) view.findViewById(R.id.username)).setText(user.name);
    ((TextView) view.findViewById(R.id.address)).setText(user.address);
    ((TextView) view.findViewById(R.id.email)).setText(user.email);
    ((TextView) view.findViewById(R.id.status)).setText(user.status.toString());
  }

  private boolean isFooter(int index) {
    return index > contacts.size() - 1 && showFooter;
  }
}
