package fr.elephantasia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.refactor.AsyncTasks.LoadBitmapAsyncTask;
import fr.elephantasia.R;
import fr.elephantasia.refactor.interfaces.DocumentInterface;
import fr.elephantasia.refactor.interfaces.LoadBitmapInterface;
import fr.elephantasia.view.RoundedImageView;


public class DocumentAdapter extends BaseAdapter {

  private Context context;
  private boolean showFooter;
  private DocumentInterface listener;
  private List<String> photos;

  public DocumentAdapter(Context context, boolean showFooter, DocumentInterface listener) {
    this.context = context;
    this.showFooter = showFooter;
    this.listener = listener;

    photos = new ArrayList<>();
  }

  public void setData(List<String> users) {
    this.photos = users;
    notifyDataSetChanged();
  }

  public void addItem(String photo) {
    photos.add(photo);
    notifyDataSetChanged();
  }

  public void clear() {
    this.photos.clear();
  }

  @Override
  public int getCount() {
    return (photos.size() + ((showFooter) ? 1 : 0));
  }

  @Override
  public String getItem(int index) {
    String ret = null;

    if (index < photos.size()) {
      ret = photos.get(index);
    }
    return (ret);
  }

  @Override
  public long getItemId(int index) {
    return index;
  }

  @Override
  public View getView(final int index, View old, ViewGroup parent) {
    View view;
    boolean creation = (old == null);
    boolean isFooter = isFooter(index);

    if (!creation) {
      view = old;
    } else {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.document_overview, parent, false);
    }

    String photo = getItem(index);

    refreshView(view, photo, isFooter);
    if (photo != null) {
      refreshImage(view, photo);
    }

    return view;
  }

  private void refreshView(View view, final String photo, boolean footer) {
    if (footer) {
      view.findViewById(R.id.footer_parent).setVisibility(View.VISIBLE);
      view.findViewById(R.id.document_parent).setVisibility(View.GONE);

      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          listener.onAddClick();
        }
      });
    } else {
      view.findViewById(R.id.footer_parent).setVisibility(View.GONE);
      view.findViewById(R.id.document_parent).setVisibility(View.VISIBLE);

      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          //listener.onDocumentClick(userInfo);
        }
      });
    }
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

  private boolean isFooter(int index) {
    return index > photos.size() - 1 && showFooter;
  }

}
