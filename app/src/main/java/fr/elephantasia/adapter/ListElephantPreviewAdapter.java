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
  public ListElephantPreviewAdapter(Context context, RealmList<Elephant> elephants) {
    super(context, R.layout.elephant_preview, elephants);
  }

  public View getView(int position, View view, ViewGroup parent) {

    if (view == null) {
      view = new ElephantPreview(getContext());
      ((ElephantPreview) view).setElephant(this.getItem(position));
    }
    return view;
  }
}


