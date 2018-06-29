package fr.elephantasia.activities.searchElephant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.customView.ElephantPreviewFull;
import fr.elephantasia.database.model.Elephant;


public class ElephantsAdapter extends RecyclerView.Adapter<ElephantsAdapter.ViewHolder> {

  private List<Elephant> data;
  private ActionClickListener listener;
  private String action;

  public ElephantsAdapter(ActionClickListener listener, String action) {
    this.listener = listener;
    this.action = action;
  }

  public void setData(List<Elephant> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ElephantPreview v = new ElephantPreviewFull(parent.getContext());
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.elephantPreview.setElephant(getItem(position));
    holder.elephantPreview.setPreviewContent();
    holder.bind(getItem(position), this.listener, this.action);
  }


  @Override
  public int getItemCount() {
    return (data == null) ? 0 : data.size();
  }

  @Override
  public long getItemId(int index) {
    return getItem(index).id;
  }

  public Elephant getItem(int index) {
    return data.get(index);
  }


  public interface ActionClickListener {
    void onActionClick(Elephant item);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreview elephantPreview;

    ViewHolder(ElephantPreview v) {
      super(v);
      elephantPreview = v;
    }

    void bind(final Elephant e, final ActionClickListener listener, String action) {
      elephantPreview.setActionListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onActionClick(e);
        }
      }, action);
    }
  }

}
