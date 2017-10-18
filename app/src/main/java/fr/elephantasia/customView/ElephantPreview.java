package fr.elephantasia.customView;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.database.model.Elephant.StateValue;

/**
 * Created by seb on 27/05/2017.
 */

public class ElephantPreview extends FrameLayout {



//  @BindView(R.id.remove_elephant) ImageButton removeButton;
//  @BindView(R.id.favorite_elephant_off) ImageButton favoriteButton;

  // Attr
  Elephant elephant;
  TextView name;
  TextView gender;
  TextView age;
  TextView weight;
  TextView height;

  public ElephantPreview(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initView(context);
  }

  public ElephantPreview(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context);
  }

  public ElephantPreview(Context context) {
    super(context);
    initView(context);
  }

  private void initView(Context context) {
    View v = inflate(getContext(), R.layout.elephant_preview, null);
    name = v.findViewById(R.id.name);
    gender = v.findViewById(R.id.gender);
    age = v.findViewById(R.id.age);
    weight = v.findViewById(R.id.weight);
    height = v.findViewById(R.id.height);
    initIcon();
    addView(v);
  }

  private void initIcon() {
    gender.setCompoundDrawables(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_venus_mars)
        .color(ContextCompat.getColor(getContext(), R.color.md_indigo)).sizeDp(14), null, null, null);

    age.setCompoundDrawables(new IconicsDrawable(getContext()).icon(MaterialDesignIconic.Icon.gmi_cake)
        .color(ContextCompat.getColor(getContext(), R.color.md_light_blue)).sizeDp(14), null, null, null);

    weight.setCompoundDrawables(new IconicsDrawable(getContext()).icon(CommunityMaterial.Icon.cmd_weight)
        .color(ContextCompat.getColor(getContext(), R.color.md_teal)).sizeDp(14), null, null, null);

    height.setCompoundDrawables(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_arrows_v)
        .color(ContextCompat.getColor(getContext(), R.color.md_green)).sizeDp(14), null, null, null);
  }

  public void setElephant(Elephant elephant) {
    this.elephant = elephant;

    if (elephant != null) {
      name.setText(elephant.getNameText());
      gender.setText(elephant.getGenderText());
      age.setText(elephant.getAgeText());
      height.setText(elephant.getHeightText());
      weight.setText(elephant.getWeightText());
    }
  }

  public void setRemoveListener(View.OnClickListener listener) {
//    removeButton.setOnClickListener(listener);
  }


  public void setRemoveButtonVisibility(boolean show) {
    if (show) {
//      removeButton.setVisibility(VISIBLE);
    }
  }

  public void setFavoriteButtonVisibility(boolean show) {
    if (show) {
//      favoriteButton.setVisibility(VISIBLE);
    }
  }
}
