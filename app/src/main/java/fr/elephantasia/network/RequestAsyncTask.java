package fr.elephantasia.network;

import android.os.AsyncTask;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Stephane on 05/01/2018.
 */
public abstract class RequestAsyncTask<ResultType> extends AsyncTask<Void, Integer, ResultType> {

	private Request request = new Request();
	private boolean running;

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
		return request.getJsonObject();
	}

	protected JSONArray getJsonArray() { return  request.getJsonArray(); }

	protected JSONObject getJsonError() {
		return request.getJsonError();
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
