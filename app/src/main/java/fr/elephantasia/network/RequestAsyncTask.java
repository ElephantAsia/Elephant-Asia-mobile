package fr.elephantasia.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Stephane on 05/01/2018.
 */
public abstract class RequestAsyncTask<ResultType> extends AsyncTask<Void, Integer, ResultType> {

	private Context context;
	private Request request = new Request();
	private boolean running;

	RequestAsyncTask(@NonNull Context context) {
		this.context = context;
	}

	public void execute() {
		executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	public boolean isRunning() {
		return (running);
	}

	@NonNull
	protected Context getContext() {
		return (context);
	}

	@Override
	@CallSuper
	protected void onPreExecute() {
		running = true;
	}

	void POSTUrlEncoded(String url, Map<String, String> params) {
		request.POSTUrlEncoded(url, params);
	}

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

	protected JSONObject getJson() {
		return request.getJson();
	}

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
