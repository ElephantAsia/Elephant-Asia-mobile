package fr.elephantasia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import fr.elephantasia.R;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import io.realm.RealmList;


public class ListElephantPreviewAdapter extends ArrayAdapter<Elephant> {

  // Attr
  private boolean favorite;
  private boolean remove;

  public ListElephantPreviewAdapter(Context context, RealmList<Elephant> elephants, boolean remove, boolean favorite) {
    super(context, R.layout.elephant_preview, elephants);
    this.favorite = favorite;
    this.remove = remove;
  }

  public View getView(int position, View view, ViewGroup parent) {

    if (view == null) {
      view = new ElephantPreview(getContext());
      ((ElephantPreview) view).setElephant(this.getItem(position));
      ((ElephantPreview) view).setRemoveButtonVisibility(remove);
      ((ElephantPreview) view).setFavoriteButtonVisibility(favorite);
      ((ElephantPreview) view).setRemoveListener(removeListener(position));
    }
    return view;
  }

  private View.OnClickListener removeListener(final int pos) {
    return new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        remove(getItem(pos));
      }
    };
  }

}