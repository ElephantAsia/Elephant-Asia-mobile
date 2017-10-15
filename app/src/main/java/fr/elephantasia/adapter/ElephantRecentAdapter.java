package fr.elephantasia.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.database.model.Elephant;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;

/**
 * Created by seb on 15/10/2017.
 * https://github.com/realm/realm-android-adapters/blob/master/example/src/main/java/io/realm/examples/adapters/ui/recyclerview/MyRecyclerViewAdapter.java
 */

public class ElephantRecentAdapter extends RealmRecyclerViewAdapter<Elephant, ElephantRecentAdapter.ViewHolder> {

  // ViewHolder inner class
  class ViewHolder extends RecyclerView.ViewHolder {
    public View cardView;
    public TextView name;
    public TextView id;
    public TextView gender;
    public TextView state;

    ViewHolder(View v) {
      super(v);
      cardView = v;
      cardView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(v.getContext(), ShowElephantActivity.class);
          Elephant elephant = getItem(getAdapterPosition());

          if (elephant != null) {
            intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
            cardView.getContext().startActivity(intent);
          }
        }
      });
      name = v.findViewById(R.id.name);
      id = v.findViewById(R.id.id);
      gender = v.findViewById(R.id.gender);
      state = v.findViewById(R.id.state);
    }
  }

  public ElephantRecentAdapter(OrderedRealmCollection<Elephant> elephants) {
    super(elephants, true);
    setHasStableIds(true);
  }

  @Override
  public ElephantRecentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elephant_recent_card, parent, false);

    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override
  @SuppressWarnings({"ConstantConditions"})
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.name.setText(getItem(position).name);
    holder.id.setText(getItem(position).getRegIDText());
    holder.gender.setText(getItem(position).getGenderText());
    holder.state.setText(getItem(position).getStateText());
  }

  @Override
  public long getItemId(int index) {
    //noinspection ConstantConditions
    return getItem(index).id;
  }
}

