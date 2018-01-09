package fr.elephantasia.asyncTasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

abstract class AsyncTask extends android.os.AsyncTask<Void, Void, Void> {

  private Context context;
  private boolean running;

  AsyncTask(Context context) {
    this.context = context;
  }

  public void execute() {
    executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  protected void onPreExecute() {
    running = true;
  }

    /*@Override
    protected Void doInBackground(Void... params) {
        return null;
    }*/

  @Override
  protected void onPostExecute(Void result) {
    running = false;
  }

  @Override
  protected void onCancelled(@Nullable Void result) {
    running = false;
  }

  @NonNull
  protected Context getContext() {
    return (context);
  }

  public boolean isRunning() {
    return (running);
  }
}
