package fr.elephantasia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

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

public class SearchElephantAdapter extends RealmRecyclerViewAdapter<Elephant, SearchElephantAdapter.ViewHolder> {

  // ViewHolder inner class
  class ViewHolder extends RecyclerView.ViewHolder {
    public View cardView;
    public TextView profil;
    public TextView chip1;
    public TextView location;
    public TextView state;
    public ImageButton favoris;

    ViewHolder(View v, Context ctx) {
      super(v);
      cardView = v;
      profil = v.findViewById(R.id.profil);
      chip1 = v.findViewById(R.id.chip1);
      location = v.findViewById(R.id.location);
      state = v.findViewById(R.id.state);

      chip1.setCompoundDrawables(new IconicsDrawable(ctx)
          .icon(MaterialDesignIconic.Icon.gmi_memory)
          .color(Color.WHITE).sizeDp(20), null, null, null);

    }
  }

  public SearchElephantAdapter(OrderedRealmCollection<Elephant> elephants) {
    super(elephants, true);
    setHasStableIds(true);
  }

  @Override
  public SearchElephantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elephant_preview, parent, false);

    ViewHolder vh = new ViewHolder(v, parent.getContext());
    return vh;
  }

  @Override
  @SuppressWarnings({"ConstantConditions"})
  public void onBindViewHolder(ViewHolder holder, int position) {
//    holder.profil.setText(getItem(position).name);
//    holder.chip1.setText(getItem(position).getRegIDText());
//    holder.location.setText("location tmp");
//    holder.state.setText(getItem(position).getStateText());
  }

  @Override
  public long getItemId(int index) {
    //noinspection ConstantConditions
    return getItem(index).id;
  }
}

