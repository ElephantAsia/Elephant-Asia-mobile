package fr.elephantasia.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import fr.elephantasia.R;
import fr.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.databinding.ContactPreviewBinding;
import fr.elephantasia.realm.model.Contact;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by seb on 30/04/2017.
 */

public class ContactResultAdapter extends RealmBaseAdapter<Contact> implements ListAdapter {

  private static class ViewHolder {
//    TextView countText;
//    CheckBox deleteCheckBox;
  }


  public ContactResultAdapter(OrderedRealmCollection<Contact> realmResults) {
    super(realmResults);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    ContactPreviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_preview, parent, false);
    Contact contact = getItem(position);
    binding.setC(contact);
    convertView = binding.getRoot();

    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_preview, parent, false);
      viewHolder = new ViewHolder();
//      viewHolder.countText = (TextView) convertView.findViewById(R.id.textview);
//      viewHolder.deleteCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    return convertView;
  }

}
