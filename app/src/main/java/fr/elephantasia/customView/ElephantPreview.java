package fr.elephantasia.customView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;

/**
 * Created by seb on 27/05/2017.
 */

public class ElephantPreview extends FrameLayout {

  // Views Binding
  @BindView(R.id.profil) TextView profil;
  @BindView(R.id.chip1) TextView chip1;
  @BindView(R.id.location) TextView location;
  @BindView(R.id.state_local) TextView stateLocal;
  @BindView(R.id.remove_elephant) ImageButton removeButton;
  @BindView(R.id.favorite_elephant_off) ImageButton favoriteButton;

  // Attr
  private Elephant elephant;

  public ElephantPreview(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initView();
  }

  public ElephantPreview(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public ElephantPreview(Context context) {
    super(context);
    initView();
  }

  private void initView() {
    View view = inflate(getContext(), R.layout.elephant_preview, null);
    ButterKnife.bind(this, view);
    addView(view);
  }

  public void setElephant(Elephant elephant) {
    this.elephant = elephant;

    if (elephant != null) {
      profil.setText(formatProfil());
      chip1.setText(formatChip());
      location.setText(elephant.currentLoc.format());

      if (elephant.state.local || elephant.state.draft) {
        stateLocal.setVisibility(View.VISIBLE);
      }
    }
  }

  public void setRemoveListener(View.OnClickListener listener) {
    removeButton.setOnClickListener(listener);
  }


  public void setRemoveButtonVisibility(boolean show) {
    if (show) {
      removeButton.setVisibility(VISIBLE);
    }
  }

  public void setFavoriteButtonVisibility(boolean show) {
    if (show) {
      favoriteButton.setVisibility(VISIBLE);
    }
  }


  private String formatProfil() {
    String res = elephant.name + ", " + elephant.getSex() + ", ";

    if (!TextUtils.isEmpty(elephant.birthDate)) {
      res += elephant.getAge() + " y/o";
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
