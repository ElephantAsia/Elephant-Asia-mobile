package fr.elephantasia.activities.home.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.view.ElephantPreview;
import fr.elephantasia.view.ElephantPreviewSmall;

public class RecentElephantsAdapter extends RecyclerView.Adapter<RecentElephantsAdapter.ViewHolder> {

  private List<Elephant> data;
  private Listener listener;

  public RecentElephantsAdapter(List<Elephant> data, Listener listener) {
    this.data = data;
    this.listener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ElephantPreview v = new ElephantPreviewSmall(parent.getContext());
    return new RecentElephantsAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.elephantPreview.setElephant(getItem(position));
    holder.elephantPreview.setPreviewContent();
  }

  @Override
  public int getItemCount() {
    return (data == null) ? 0 : data.size();
  }

  @Override
  public long getItemId(int index) {
    return getItem(index).id;
  }

  private Elephant getItem(int index) {
    return data.get(index);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreview elephantPreview;
    CardView card;

    ViewHolder(ElephantPreview v) {
      super(v);
      elephantPreview = v;
      card = v.findViewById(R.id.card_view);
      card.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Elephant elephant = getItem(getAdapterPosition());
          listener.onClick(elephant);
        }
      });
    }
  }

  public interface Listener {
    void onClick(Elephant e);
  }

}
