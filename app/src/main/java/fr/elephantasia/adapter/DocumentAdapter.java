package fr.elephantasia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import fr.elephantasia.R;
import fr.elephantasia.customView.RoundedImageView;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.refactor.AsyncTasks.LoadBitmapAsyncTask;
import fr.elephantasia.refactor.interfaces.LoadBitmapInterface;
import io.realm.RealmList;

public class DocumentAdapter extends ArrayAdapter<Document> {

  private Context context;
  private RealmList<Document> docs;

  public DocumentAdapter(Context context, RealmList<Document> docs) {
      super(context, R.layout.document_overview, docs);
      this.context = context;
      this.docs = docs;
  }

  @Override
  public View getView(final int index, View old, ViewGroup parent) {
    View view;
    boolean creation = (old == null);

    if (!creation) {
      view = old;
    } else {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.document_overview, parent, false);
    }

    String photo = getItem(index).path;

    refreshView(view, photo);
    if (photo != null) {
      refreshImage(view, photo);
    }

    return view;
  }

  private void refreshView(View view, final String photo) {

  }

  private void refreshImage(View view, String photo) {
    final RoundedImageView image = (RoundedImageView) view.findViewById(R.id.document_image);

    image.setImageResource(R.drawable.placeholder);
    new LoadBitmapAsyncTask(context, photo, new LoadBitmapInterface() {
      @Override
      public void onFinish(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
      }
    }).execute();

  }

}
