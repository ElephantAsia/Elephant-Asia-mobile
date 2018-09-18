package fr.elephantasia.view;

import android.content.Context;
import android.util.AttributeSet;

import fr.elephantasia.R;


/**
 * Created by seb on 27/05/2017.
 */
public class ElephantPreviewSmall extends ElephantPreview {

  public ElephantPreviewSmall(Context ctx, AttributeSet attrs, int defStyle) {
    super(ctx, attrs, defStyle);
    initView(R.layout.elephant_preview_small);
  }

  public ElephantPreviewSmall(Context ctx, AttributeSet attrs) {
    super(ctx, attrs);
    initView(R.layout.elephant_preview_small);
  }

  public ElephantPreviewSmall(Context ctx) {
    super(ctx);
    initView(R.layout.elephant_preview_small);
  }
}
