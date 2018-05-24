package fr.elephantasia.customView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;

public class ElephantPreviewV2 extends CardView {

  private Elephant elephant;
  private Listener listener;

  private AlphaAnimation backgroundInAnimation;
  private AlphaAnimation backgroundOutAnimation;

  @BindView(R.id.name) TextView name;
  @BindView(R.id.localization) TextView localization;
  @BindView(R.id.db_status) TextView dbStatus;
  @BindView(R.id.age) TextView age;
  @BindView(R.id.registration_id) TextView registrationId;
  @BindView(R.id.mte_id) TextView mteId;
  @BindView(R.id.select) FloatingActionButton selectButton;
  @BindView(R.id.consultation) FloatingActionButton consultationButton;
  @BindView(R.id.select_mask) FrameLayout selectMask;

  public ElephantPreviewV2(Context ctx, AttributeSet attrs, int defStyle) {
    super(ctx, attrs, defStyle);
    init();
  }

  public ElephantPreviewV2(Context ctx, AttributeSet attrs) {
    super(ctx, attrs);
    init();
  }

  public ElephantPreviewV2(Context ctx) {
    super(ctx);
    init();
  }

  private void init() {
    View view = inflate(getContext(), R.layout.elephant_preview_v2, null);
    addView(view);

    backgroundInAnimation = new AlphaAnimation(0f, 1f);
    backgroundInAnimation.setInterpolator(new LinearInterpolator());
    backgroundInAnimation.setDuration(400);

    backgroundOutAnimation = new AlphaAnimation(1f, 0f);
    backgroundOutAnimation.setInterpolator(new LinearInterpolator());
    backgroundOutAnimation.setDuration(300);

    backgroundInAnimation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) { selectMask.setVisibility(View.VISIBLE); }
      @Override public void onAnimationEnd(Animation animation) {}
      @Override public void onAnimationRepeat(Animation animation) {}
    });
    backgroundOutAnimation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {}
      @Override public void onAnimationEnd(Animation animation) { selectMask.setVisibility(View.GONE); }
      @Override public void onAnimationRepeat(Animation animation) {}
    });
  }

  public void setElephant(Elephant e) {
    this.elephant = e;
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  public void refreshView(boolean selected) {
    if (elephant != null) {
      ButterKnife.bind(this);
      refreshName();
      refreshLocalization();
      refreshDbStatus();
      refreshAge();
      refreshRegistrationId();
      refreshMteId();

      refreshSelectButton(selected);
      refreshConsultationButton();
    }
  }

  private void refreshName() {
    name.setText(elephant.getNameText());
  }

  private void refreshLocalization() {
    localization.setText(format(elephant.currentLoc.format()));
  }

  private void refreshDbStatus() {
    dbStatus.setText(elephant.dbState);
  }

  private void refreshAge() {
    age.setText(format(elephant.getAgeText()));
  }

  private void refreshRegistrationId() {
    registrationId.setText(format(elephant.regID));
  }

  private void refreshMteId() {
    mteId.setText(format(elephant.mteNumber));
  }

  private void refreshSelectButton(boolean selected) {
    selectButton.setImageDrawable(
      new IconicsDrawable(getContext())
        .icon(MaterialDesignIconic.Icon.gmi_plus)
        .color(Color.WHITE)
        .sizeDp(22)
    );

    refreshSelectButtonLogo(selected);
    selectButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onSelectButtonClick(elephant);
        }
      }
    });
  }

  public void refreshSelectButtonLogo(boolean selected) {
    if (selected) {
      selectMask.startAnimation(backgroundInAnimation);
      selectButton.animate()
        .rotation(-135)
        .setInterpolator(new AccelerateDecelerateInterpolator())
        .setDuration(400);
    } else {
      selectMask.startAnimation(backgroundOutAnimation);
      selectButton.animate()
        .rotation(0)
        .setInterpolator(new AccelerateDecelerateInterpolator())
        .setDuration(300);
    }
  }

  private void refreshConsultationButton() {
    consultationButton.setImageDrawable(
      new IconicsDrawable(getContext())
        .icon(MaterialDesignIconic.Icon.gmi_account)
        .color(Color.WHITE)
        .sizeDp(22)
    );

    if (listener != null) {
      consultationButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.onConsultationButtonClick(elephant);
        }
      });
    }
  }

  private @NonNull String format(@Nullable String s) {
    return ((s != null && !s.trim().isEmpty()) ? s : "-");
  }

  public interface Listener {
    void onSelectButtonClick(Elephant elephant);
    void onConsultationButtonClick(Elephant elephant);
  }

}
