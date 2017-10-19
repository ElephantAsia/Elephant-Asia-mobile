package fr.elephantasia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.customView.ElephantPreviewFull;
import fr.elephantasia.database.model.Elephant;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by seb on 15/10/2017.
 * https://github.com/realm/realm-android-adapters/blob/master/example/src/main/java/io/realm/examples/adapters/ui/recyclerview/MyRecyclerViewAdapter.java
 */

public class SearchElephantAdapter extends RealmRecyclerViewAdapter<Elephant, SearchElephantAdapter.ViewHolder> {

  public interface OnActionClickListener {
    void onActionClick(Elephant item);
  }

  // ViewHolder inner class
  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreview elephantPreview;

    ViewHolder(ElephantPreview v) {
      super(v);
      elephantPreview = v;
    }

    public void bindActionListener(final Elephant item, final OnActionClickListener listener, String action) {
      elephantPreview.setActionListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              listener.onActionClick(item);
            }
          }, action);
    }
  }

    final String action;
    final OnActionClickListener actionListener;

    public SearchElephantAdapter(OrderedRealmCollection<Elephant> elephants, OnActionClickListener actionListener, String action) {
      super(elephants, true);
      setHasStableIds(true);
      this.action = action;
      this.actionListener = actionListener;
    }

    @Override
    public SearchElephantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      ElephantPreview v = new ElephantPreviewFull(parent.getContext());

      return new ViewHolder(v);
    }

    @Override
    @SuppressWarnings({"ConstantConditions"})
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      holder.elephantPreview.setElephant(getItem(position));
      holder.elephantPreview.setPreviewContent();

      holder.bindActionListener(getItem(position), this.actionListener, this.action);
    }

    @Override
    public long getItemId(int index) {
      //noinspection ConstantConditions
      return getItem(index).id;
    }

  }

