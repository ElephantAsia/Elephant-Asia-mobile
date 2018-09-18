package fr.elephantasia.network;

import android.os.AsyncTask;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public abstract class RequestAsyncTask<ResultType> extends AsyncTask<Void, Integer, ResultType> {

	private Request request; // BECAUSE NO MULTIPLE INHERITANCE - JAVA SUCKS !
	private boolean running;

	static protected final int PROGRESS_UPLOAD = 1;
	static protected final int PROGRESS_RESPONSE = 2;

	protected RequestAsyncTask() {
		request = new Request(new Request.Listener() {
			@Override
			public void uploadProgressUpdate(int currentProgress) {
				publishProgress(currentProgress, PROGRESS_UPLOAD);
			}
			@Override
			public void responseProgressUpdate(int currentProgress) {
				publishProgress(currentProgress, PROGRESS_RESPONSE);
			}
		});
	}

	public void execute() {
		executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	public boolean isRunning() {
		return (running);
	}

	@Override
	@CallSuper
	protected void onPreExecute() {
		running = true;
	}

	protected void POSTUrlEncoded(String url, Map<String, String> params) {
		request.POSTUrlEncoded(url, params);
	}

	protected void GET(String url) {
		request.GET(url, getHeader(), getParams());
	}

	protected void POSTJSON(String url) { request.POSTJSON(url, getHeader(), getBody()); }

	@Nullable
	protected Map<String, String> getParams() {
    return null;
  }

  @Nullable
  protected Map<String, String> getHeader() {
    return null;
  }

  @Nullable
	protected byte[] getBody() { return null; }

	@Override
	@Nullable
	abstract protected ResultType doInBackground(@Nullable Void... params);

	@Override
	@CallSuper
	protected void onPostExecute(ResultType result) {
		running = false;
	}

	@Override
	@CallSuper
	protected void onCancelled(@Nullable ResultType result) {
		closeHttpClient();
	}

	protected Integer getResponseCode() {
		return request.getResponseCode();
	}

	protected JSONObject getJsonObject() {
		return request.getJsonObjectResponse();
	}

	protected JSONArray getJsonArray() { return  request.getJsonArrayResponse(); }

	protected JSONObject getJsonError() {
		return request.getJsonErrorResponse();
	}

	@Override
	@CallSuper
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	private void closeHttpClient() {
		running = false;
		request.closeHttpClient();
	}

}
