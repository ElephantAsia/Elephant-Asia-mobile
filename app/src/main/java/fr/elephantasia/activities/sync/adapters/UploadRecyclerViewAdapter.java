package fr.elephantasia.activities.sync.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.view.ElephantPreviewV2;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class UploadRecyclerViewAdapter extends RealmRecyclerViewAdapter<Elephant, UploadRecyclerViewAdapter.ViewHolder> {

  // Attributes
  private SparseBooleanArray itemStateArray = new SparseBooleanArray();
  private Listener listener;

  // View holder
  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreviewV2 elephantPreview;

    ViewHolder(View v) {
      super(v);
      elephantPreview = (ElephantPreviewV2) v;
    }
  }

  public UploadRecyclerViewAdapter(OrderedRealmCollection<Elephant> items, @NonNull  Listener listener) {
    super(items, true);
    this.listener = listener;
//    for (int i = 0 ; i < items.size() ; ++i) {
//      itemStateArray.put(i, false);
//    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = new ElephantPreviewV2(parent.getContext());
    view.setLayoutParams(new RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.MATCH_PARENT,
        RecyclerView.LayoutParams.MATCH_PARENT
    ));
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    boolean selected = itemStateArray.get(position);

    holder.elephantPreview.setElephant(getItem(position));
    holder.elephantPreview.setListener(new ElephantPreviewV2.Listener() {
      @Override
      public void onSelectButtonClick(Elephant elephant) {
        Log.w("select", "elephant " + elephant.name);
        boolean selected = !itemStateArray.get(holder.getAdapterPosition());
        itemStateArray.put(holder.getAdapterPosition(), selected);
        Log.w("select", "position " + holder.getAdapterPosition());
        Log.w("select", "selected " + selected);
        holder.elephantPreview.refreshSelectButtonLogo(selected);
        listener.onSelectButtonClick(selected, elephant);
      }

      @Override
      public void onConsultationButtonClick(Elephant elephant) {
        listener.onConsultationButtonClick(elephant);
      }
    });
    holder.elephantPreview.refreshView(selected);
  }

  public SparseBooleanArray getSelectedElephants() {
    return itemStateArray;
  }

  public int countElephantsSelected() {
    int k = 0;

    for (int i = 0 ; i < getItemCount() ; ++i) {
      if (itemStateArray.get(i)) ++k;
    }
    return k;
  }

  public void resetSelection() {
    itemStateArray.clear();
    notifyDataSetChanged();
  }

  public interface Listener {
    void onSelectButtonClick(boolean selected, Elephant elephant);
    void onConsultationButtonClick(Elephant elephant);
  }
}
