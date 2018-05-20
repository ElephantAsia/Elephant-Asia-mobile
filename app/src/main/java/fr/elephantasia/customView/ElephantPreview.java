package fr.elephantasia.customView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.database.model.Elephant;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;

/**
 * Created by seb on 27/05/2017.
 */

public abstract class ElephantPreview extends FrameLayout {

  //  @BindView(R.id.remove_elephant) ImageButton removeButton;
//  @BindView(R.id.favorite_elephant_off) ImageButton favoriteButton;
  public static String SELECT = "SELECT";
  public static String UNSELECT = "SELECT";
  public static String EDIT = "SELECT";

  // Views
  TextView name;
  TextView gender;
  TextView age;
  TextView weight;
  TextView height;

  TextView showButton;
  TextView actionButton;

  CheckBox selectedCb;

  // Attr
  Elephant elephant;


  public ElephantPreview(Context ctx, AttributeSet attrs, int defStyle) {
    super(ctx, attrs, defStyle);
  }

  public ElephantPreview(Context ctx, AttributeSet attrs) {
    super(ctx, attrs);
  }

  public ElephantPreview(Context ctx) {
    super(ctx);
  }

  protected void initView(int layoutId) {
    View v = inflate(getContext(), layoutId, null);

    name = v.findViewById(R.id.name);
    gender = v.findViewById(R.id.gender);
    age = v.findViewById(R.id.age);
    weight = v.findViewById(R.id.weight);
    height = v.findViewById(R.id.height);

    showButton = v.findViewById(R.id.show_button);
    actionButton = v.findViewById(R.id.action_button);

    selectedCb = v.findViewById(R.id.checkbox);

    if (this.isFocusable()) {
      showButton.setVisibility(GONE);
      actionButton.setVisibility(GONE);
      selectedCb.setVisibility(VISIBLE);
    }

    if (showButton != null) {
      showButton.setOnClickListener(
          new View.OnClickListener() {
            public void onClick(View v) {
              Intent intent = new Intent(v.getContext(), ShowElephantActivity.class);
              intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
              getContext().startActivity(intent);
            }
          });
    }


    setTextIcons();
    addView(v);
  }

  private void setTextIcons() {
    gender.setCompoundDrawables(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_venus_mars)
        .color(ContextCompat.getColor(getContext(), R.color.md_indigo)).sizeDp(14), null, null, null);

    age.setCompoundDrawables(new IconicsDrawable(getContext()).icon(MaterialDesignIconic.Icon.gmi_cake)
        .color(ContextCompat.getColor(getContext(), R.color.md_light_blue)).sizeDp(14), null, null, null);

    weight.setCompoundDrawables(new IconicsDrawable(getContext()).icon(CommunityMaterial.Icon.cmd_weight)
        .color(ContextCompat.getColor(getContext(), R.color.md_teal)).sizeDp(14), null, null, null);

    height.setCompoundDrawables(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_arrows_v)
        .color(ContextCompat.getColor(getContext(), R.color.md_green)).sizeDp(14), null, null, null);
  }

  public void setActionListener(View.OnClickListener listener, String action) {
    actionButton.setOnClickListener(listener);
    actionButton.setText(action);
  }

  public void hideActionButton() {
    actionButton.setVisibility(GONE);
  }

  public void setElephant(Elephant elephant) {
    this.elephant = elephant;
  }

  public void setPreviewContent() {
    if (elephant != null) {
      name.setText(elephant.getNameText());
      gender.setText(elephant.getGenderText());
      age.setText(elephant.getAgeText());
      height.setText(elephant.getHeightText());
      weight.setText(elephant.getWeightText());

      name.setMaxLines(1);
      name.setHorizontallyScrolling(true);
      name.setEllipsize(TextUtils.TruncateAt.END);
    }
  }
}
