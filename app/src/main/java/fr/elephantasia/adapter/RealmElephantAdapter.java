package fr.elephantasia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

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

public class RealmElephantAdapter extends RealmBaseAdapter<Elephant> implements ListAdapter {
  // Attr
  private boolean favorite;
  private boolean remove;
  private Context context;

  public RealmElephantAdapter(OrderedRealmCollection<Elephant> realmResults, Context context, boolean remove, boolean favorite) {
    super(realmResults);
    this.context = context;
    this.favorite = favorite;
    this.remove = remove;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    ViewHolder viewHolder;

    if (view == null) {
      view = new ElephantPreview(this.context);
      ((ElephantPreview) view).setElephant(this.getItem(position));
      ((ElephantPreview) view).setRemoveButtonVisibility(remove);
      ((ElephantPreview) view).setFavoriteButtonVisibility(favorite);
      ButterKnife.bind(this, view);
      viewHolder = new ViewHolder(view);

      if (adapterData != null) {
        viewHolder.elephant = adapterData.get(position);
      }
    }
    return view;
  }

  static class ViewHolder {
    Elephant elephant;

    ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }

    // Bind View
    @OnClick(R.id.favorite_elephant_off)
    public void addFavorite(View v) {
//      this.view = null;
//      remove(getItem(pos));
    }

  }
}