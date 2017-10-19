package fr.elephantasia.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.customView.ElephantPreviewSmall;
import fr.elephantasia.database.model.Elephant;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;

/**
 * Created by seb on 15/10/2017.
 * https://github.com/realm/realm-android-adapters/blob/master/example/src/main/java/io/realm/examples/adapters/ui/recyclerview/MyRecyclerViewAdapter.java
 */

public class RecentElephantAdapter extends RealmRecyclerViewAdapter<Elephant, RecentElephantAdapter.ViewHolder> {

  // ViewHolder inner class
  class ViewHolder extends RecyclerView.ViewHolder {
    ElephantPreview elephantPreview;
    CardView card;

    ViewHolder(ElephantPreview v) {
      super(v);
      elephantPreview = v;
      card = v.findViewById(R.id.card_view);
      card.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(v.getContext(), ShowElephantActivity.class);
          Elephant elephant = getItem(getAdapterPosition());

          if (elephant != null) {
            intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
            elephantPreview.getContext().startActivity(intent);
          }
        }
      });
    }
  }

  public RecentElephantAdapter(OrderedRealmCollection<Elephant> elephants) {
    super(elephants, true);
    setHasStableIds(true);
  }

  @Override
  public RecentElephantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ElephantPreview v = new ElephantPreviewSmall(parent.getContext());
//    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elephant_preview_small, parent, false);

    return new ViewHolder(v);
  }

  @Override
  @SuppressWarnings({"ConstantConditions"})
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.elephantPreview.setElephant(getItem(position));
    holder.elephantPreview.setPreviewContent();
  }

  @Override
  public long getItemId(int index) {
    //noinspection ConstantConditions
    return getItem(index).id;
  }

}

