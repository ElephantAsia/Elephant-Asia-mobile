package fr.elephantasia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.elephantasia.AsyncTasks.LoadBitmapAsyncTask;
import fr.elephantasia.R;
import fr.elephantasia.customView.RoundedImageView;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.refactor.interfaces.LoadBitmapInterface;

public class DocumentAdapter extends ArrayAdapter<Document> {

  private Context context;
  private List<Document> docs;

  public DocumentAdapter(Context context, List<Document> docs) {
    super(context, R.layout.document_overview, docs);
    this.context = context;
    this.docs = docs;
  }

  @Override
  @NonNull
  public View getView(final int index, View old, @NonNull ViewGroup parent) {
    View view;
    boolean creation = (old == null);

    if (!creation) {
      view = old;
    } else {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.document_overview, parent, false);
    }

    Document document = getItem(index);
    if (document == null) {
        return view;
    }

    if (document.path != null) {
      refreshImage(view, document.path);
    }
    refreshTitle(view, document.title);
    refreshType(view, document.type);

    return view;
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

  private void refreshTitle(View view, String title) {
      ((TextView)view.findViewById(R.id.title)).setText(title);
  }

  private void refreshType(View view, String type) {
      ((TextView)view.findViewById(R.id.type)).setText(type);
  }

}
