package fr.elephantasia.customView;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
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

public class ElephantPreviewFull extends ElephantPreview {

  public ElephantPreviewFull(Context ctx, AttributeSet attrs, int defStyle) {
    super(ctx, attrs, defStyle);
    initView(R.layout.elephant_preview);
  }

  public ElephantPreviewFull(Context ctx, AttributeSet attrs) {
    super(ctx, attrs);
    initView(R.layout.elephant_preview);
  }

  public ElephantPreviewFull(Context ctx) {
    super(ctx);
    initView(R.layout.elephant_preview);
  }
}
