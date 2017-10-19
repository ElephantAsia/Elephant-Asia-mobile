package fr.elephantasia.customView;

import android.content.Context;
import android.util.AttributeSet;

import fr.elephantasia.R;

/**
 * Created by seb on 27/05/2017.
 */

public class ElephantPreviewFull extends ElephantPreview {

  public ElephantPreviewFull(Context ctx, AttributeSet attrs, int defStyle) {
    super(ctx, attrs, defStyle);
    initView(R.layout.elephant_preview_full);
  }

  public ElephantPreviewFull(Context ctx, AttributeSet attrs) {
    super(ctx, attrs);
    initView(R.layout.elephant_preview_full);
  }

  public ElephantPreviewFull(Context ctx) {
    super(ctx);
    initView(R.layout.elephant_preview_full);
  }
}
