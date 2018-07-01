package fr.elephantasia.activities.sync.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.view.ElephantPreviewV2;

public class SelectElephantsAdapter extends RecyclerView.Adapter<SelectElephantsAdapter.ViewHolder> {

  private SparseBooleanArray itemStateArray = new SparseBooleanArray();
  private List<Elephant> data;
  private Listener listener;

  public SelectElephantsAdapter(Listener listener) {
    this.data = data;
    this.listener = listener;
  }

  public void setElephants(List<Elephant> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = new ElephantPreviewV2(parent.getContext());
    view.setLayoutParams(new RecyclerView.LayoutParams(
      RecyclerView.LayoutParams.MATCH_PARENT,
      RecyclerView.LayoutParams.MATCH_PARENT
    ));
    return new SelectElephantsAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    boolean selected = itemStateArray.get(position);

    holder.elephantPreview.setElephant(getItem(position));
    holder.elephantPreview.setListener(new ElephantPreviewV2.Listener() {
      @Override
      public void onSelectButtonClick(Elephant elephant) {
        boolean selected = !itemStateArray.get(holder.getAdapterPosition());
        itemStateArray.put(holder.getAdapterPosition(), selected);
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

  @Override
  public int getItemCount() {
    return (data == null) ? 0 : data.size();
  }

  private Elephant getItem(int index) {
    return data.get(index);
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

  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreviewV2 elephantPreview;

    ViewHolder(View v) {
      super(v);
      elephantPreview = (ElephantPreviewV2) v;
    }
  }

  public interface Listener {
    void onSelectButtonClick(boolean selected, Elephant elephant);
    void onConsultationButtonClick(Elephant elephant);
  }

}
