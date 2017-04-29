package fr.elephantasia.dialogs;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import fr.elephantasia.R;

public class PickImageDialogBuilder extends MaterialDialog.Builder {

  public PickImageDialogBuilder(Context context) {
    super(context);
    initView();
  }

  private void initView() {
    title(R.string.add_document);
    customView(R.layout.pick_image_dialog, false);
  }

  @Override
  public PickImageDialog build() {
    return new PickImageDialog(this);
  }

  @Override
  public PickImageDialog show() {
    PickImageDialog dialog = build();
    super.show();
    return dialog;
  }
}
