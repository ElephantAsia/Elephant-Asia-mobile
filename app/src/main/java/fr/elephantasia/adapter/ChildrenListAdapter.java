package fr.elephantasia.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.view.ElephantPreview;
import fr.elephantasia.view.ElephantPreviewFull;
import io.realm.RealmList;


public class ChildrenListAdapter extends ArrayAdapter<Elephant> {

  boolean hideActionButton = false;

  public ChildrenListAdapter(Context context, RealmList<Elephant> elephants) {
    super(context, R.layout.elephant_preview_full, elephants);
  }

  public ChildrenListAdapter(Context context, RealmList<Elephant> elephants, boolean hideActionButton) {
    super(context, R.layout.elephant_preview_full, elephants);
    this.hideActionButton = hideActionButton;
  }


  public View getView(int position, View view, ViewGroup parent) {

    if (view == null) {
      view = new ElephantPreviewFull(getContext());
      ((ElephantPreview) view).setElephant(this.getItem(position));
      ((ElephantPreview) view).setPreviewContent();
      ((ElephantPreview) view).setActionListener(removeListener(position), getContext().getString(R.string.unselect));

      if (hideActionButton) {
        ((ElephantPreview) view).hideActionButton();
      }
    }
    return view;
  }

  public View.OnClickListener removeListener(final int pos) {
    return new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        remove(getItem(pos));
      }
    };
  }
}