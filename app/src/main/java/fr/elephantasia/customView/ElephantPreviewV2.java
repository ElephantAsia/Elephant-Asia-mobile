package fr.elephantasia.customView;

import android.content.Context;
import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.database.model.Elephant;

import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;

/**
 * Created by seb on 27/05/2017.
 */

public class ElephantPreviewV2 extends CardView {

  @BindView(R.id.description) TextView description;

  Elephant elephant;

  public ElephantPreviewV2(Context ctx, AttributeSet attrs, int defStyle) {
    super(ctx, attrs, defStyle);
    initView();
  }

  public ElephantPreviewV2(Context ctx, AttributeSet attrs) {
    super(ctx, attrs);
    initView();
  }

  public ElephantPreviewV2(Context ctx) {
    super(ctx);
    initView();
  }

  private void initView() {
    View v = inflate(getContext(), R.layout.elephant_preview_v2, null);
    ButterKnife.bind(v);
    setTextIcons();
    addView(v);
  }

  public void setElephant(Elephant e) {
    this.elephant = e;
  }

  private void setTextIcons() {
//    gender.setCompoundDrawables(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_venus_mars)
//        .color(ContextCompat.getColor(getContext(), R.color.md_indigo)).sizeDp(14), null, null, null);

  }



}
