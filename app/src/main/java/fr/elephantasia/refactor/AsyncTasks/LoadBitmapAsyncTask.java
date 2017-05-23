package fr.elephantasia.refactor.AsyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import fr.elephantasia.refactor.interfaces.LoadBitmapInterface;
import fr.elephantasia.utils.ImageUtil;

public class LoadBitmapAsyncTask extends AsyncTask {

  private LoadBitmapInterface listener;
  private String path;
  private Bitmap bitmap;

  public LoadBitmapAsyncTask(Context context, String path, LoadBitmapInterface listener) {
    super(context);
    this.listener = listener;
    this.path = path;
  }

  @Override
  protected Void doInBackground(Void... params) {
    byte[] bytes = ImageUtil.resizeImage(path, 1440, 1440);
    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    return null;
  }

  @Override
  protected void onPostExecute(Void result) {
    super.onPostExecute(result);
    listener.onFinish(bitmap);
  }
}
