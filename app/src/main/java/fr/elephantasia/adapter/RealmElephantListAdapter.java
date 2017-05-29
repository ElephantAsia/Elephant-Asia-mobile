package fr.elephantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by seb on 29/05/2017.
 */

class RealmElephantListAdapter extends RealmBaseAdapter<Elephant> implements ListAdapter {

  // Bind View
  @OnClick(R.id.remove_elephant)
  public void removeFromList(View v) {
    int pos = (int) v.getTag();
     pos = (int) v.getTag();
  }

  // Attr
  private boolean favorite;
  private boolean remove;
  private Context context;

  RealmElephantListAdapter(OrderedRealmCollection<Elephant> realmResults, Context context, boolean remove, boolean favorite) {
    super(realmResults);
    this.context = context;
    this.favorite = favorite;
    this.remove = remove;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = new ElephantPreview(this.context);
      ((ElephantPreview) view).setElephant(this.getItem(position));
      ((ElephantPreview) view).setRemoveButtonVisibility(remove);
      ((ElephantPreview) view).setFavoriteButtonVisibility(favorite);
//      ((ElephantPreview) view).setRemoveListener(removeListener(position));
      ButterKnife.bind(this, view);
      view.setTag(position);
    }

    if (adapterData != null) {
      final Elephant item = adapterData.get(position);
    }
    return view;
  }
}