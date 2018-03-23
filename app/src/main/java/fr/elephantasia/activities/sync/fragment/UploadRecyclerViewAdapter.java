package fr.elephantasia.activities.sync.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import fr.elephantasia.R;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class UploadRecyclerViewAdapter extends RealmRecyclerViewAdapter<Elephant, UploadRecyclerViewAdapter.ViewHolder> {

  // Attributes
  private SparseBooleanArray itemStateArray = new SparseBooleanArray();

  // View holder
  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreview elephantPreview;
    CheckBox cb;

    ViewHolder(View v) {
      super(v);
      elephantPreview = v.findViewById(R.id.preview);
      cb = elephantPreview.findViewById(R.id.checkbox);
    }
  }

  public UploadRecyclerViewAdapter(OrderedRealmCollection<Elephant> items) {
    super(items, true);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.upload_selection, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.elephantPreview.setElephant(getItem(position));
    holder.elephantPreview.setPreviewContent();
    if (holder.cb.isChecked()) {
      holder.cb.toggle();
    }
    holder.elephantPreview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        holder.cb.toggle();
        itemStateArray.put(holder.getAdapterPosition(), holder.cb.isChecked());
      }
    });
  }

  public SparseBooleanArray getSelectedElephants() {
    return itemStateArray;
  }

  public void resetSelection() {
    itemStateArray = new SparseBooleanArray();
  }
}
