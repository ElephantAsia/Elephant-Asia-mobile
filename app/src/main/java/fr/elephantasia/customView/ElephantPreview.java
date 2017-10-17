package fr.elephantasia.customView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

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

  // Views Binding
  @BindView(R.id.profil) TextView profil;
  @BindView(R.id.chip1) TextView chip1;
  @BindView(R.id.location) TextView location;
  @BindView(R.id.state) TextView stateLocal;
//  @BindView(R.id.remove_elephant) ImageButton removeButton;
//  @BindView(R.id.favorite_elephant_off) ImageButton favoriteButton;

  // Attr
  private Elephant elephant;

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
    View view = inflate(getContext(), R.layout.elephant_preview, null);
    ButterKnife.bind(this, view);
    initIcon(context);
    addView(view);
  }

  private void initIcon(Context context) {
//    favoriteButton.setImageDrawable(
//        new IconicsDrawable(context)
//        .icon(MaterialDesignIconic.Icon.gmi_favorite_outline)
//        .color(Color.WHITE).sizeDp(22));
//
//    removeButton.setImageDrawable(new IconicsDrawable(context)
//        .icon(MaterialDesignIconic.Icon.gmi_close_circle)
//        .color(Color.WHITE).sizeDp(22));

    chip1.setCompoundDrawables(new IconicsDrawable(context)
        .icon(MaterialDesignIconic.Icon.gmi_card_sd)
        .color(Color.WHITE).sizeDp(20), null, null, null);

    location.setCompoundDrawables(new IconicsDrawable(context)
        .icon(MaterialDesignIconic.Icon.gmi_pin)
        .color(Color.WHITE).sizeDp(20), null, null, null);

    stateLocal.setCompoundDrawables(new IconicsDrawable(context)
        .icon(MaterialDesignIconic.Icon.gmi_info_outline)
        .color(Color.WHITE).sizeDp(20), null, null, null);

  }

  public void setElephant(Elephant elephant) {
    this.elephant = elephant;

    if (elephant != null) {
      profil.setText(formatProfil());
      chip1.setText(formatChip());
      location.setText(elephant.currentLoc.format());

      if (elephant.state.equals(StateValue.draft.name()) || elephant.state.equals(StateValue.saved.name())) {
        stateLocal.setVisibility(View.VISIBLE);
      }
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


  private String formatProfil() {
    String res = elephant.name + ", " + elephant.getGenderText() + ", ";

    if (!TextUtils.isEmpty(elephant.birthDate)) {
      res += elephant.getAgeText() + " y/o";
    } else {
      res += "Age N/A";
    }
    return res;
  }

  private String formatChip() {
    String res;

    if (!TextUtils.isEmpty(elephant.chips1)) {
      res = "#" + elephant.chips1;
    } else {
      res = "N/A";
    }
    return res;
  }
}
