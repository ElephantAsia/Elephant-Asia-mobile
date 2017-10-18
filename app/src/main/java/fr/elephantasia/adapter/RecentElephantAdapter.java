package fr.elephantasia.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
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

public class RecentElephantAdapter extends RealmRecyclerViewAdapter<Elephant, RecentElephantAdapter.ViewHolder> {

  // ViewHolder inner class
  class ViewHolder extends RecyclerView.ViewHolder {
    public View cardView;
    public TextView name;
    public TextView gender;
    public TextView age;
    public TextView weight;
    public TextView height;

    ViewHolder(View v, Context ctx) {
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
      gender = v.findViewById(R.id.gender);
      age = v.findViewById(R.id.age);
      weight = v.findViewById(R.id.weight);
      height = v.findViewById(R.id.height);

      gender.setCompoundDrawables(new IconicsDrawable(ctx).icon(FontAwesome.Icon.faw_venus_mars)
          .color(ContextCompat.getColor(ctx, R.color.md_indigo)).sizeDp(14), null, null, null);

      age.setCompoundDrawables(new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_cake)
          .color(ContextCompat.getColor(ctx, R.color.md_light_blue)).sizeDp(14), null, null, null);

      weight.setCompoundDrawables(new IconicsDrawable(ctx).icon(CommunityMaterial.Icon.cmd_weight)
          .color(ContextCompat.getColor(ctx, R.color.md_teal)).sizeDp(14), null, null, null);

      height.setCompoundDrawables(new IconicsDrawable(ctx).icon(FontAwesome.Icon.faw_arrows_v)
          .color(ContextCompat.getColor(ctx, R.color.md_green)).sizeDp(14), null, null, null);
    }
  }

  public RecentElephantAdapter(OrderedRealmCollection<Elephant> elephants) {
    super(elephants, true);
    setHasStableIds(true);
  }

  @Override
  public RecentElephantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elephant_recent_card, parent, false);

    ViewHolder vh = new ViewHolder(v, parent.getContext());
    return vh;
  }

  @Override
  @SuppressWarnings({"ConstantConditions"})
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.name.setText(getItem(position).getNameText());
    holder.gender.setText(getItem(position).getGenderText());
    holder.age.setText(getItem(position).getAgeText());
    holder.height.setText(getItem(position).getHeightText());
    holder.weight.setText(getItem(position).getWeightText());
  }

  @Override
  public long getItemId(int index) {
    //noinspection ConstantConditions
    return getItem(index).id;
  }
}

