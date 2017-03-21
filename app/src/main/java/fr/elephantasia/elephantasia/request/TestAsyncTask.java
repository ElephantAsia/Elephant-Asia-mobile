package fr.elephantasia.elephantasia.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;

import fr.elephantasia.elephantasia.utils.StaticTools;


/**
 * Created by Stephane on 31/10/2016.
 */

@Deprecated
public class TestAsyncTask extends RequestAsyncTask {

    private static final String PATH = "/test/route/example";

    private Listener _listener;
    private JSONArray _jsonArray;

    public TestAsyncTask(Context context, @NonNull Listener listener) {
        super(context);
        _listener = listener;
    }

    @Override
    @Nullable
    protected Void doInBackground(@Nullable Void... params) {
        String httpUrl, httpBody = "";

        httpUrl = SERVER + PATH;
        setURL(httpUrl);
        createHttpConnection();
        setRequestMethod("GET");
        setDoInput(true);

        super.doInBackground();

        InputStream is = getHttpResponseStream();
        if (is != null) {
            httpBody = StaticTools.getStringFromStream(is, "UTF-8");
            try {
                _jsonArray = new JSONArray(httpBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        closeHttpClient();

        Log.i("GET %s", httpUrl);
        Log.i("BODY %s", httpBody);
        return (null);
    }

    @Override
    protected void onPostExecute(@Nullable Void result) {
        super.onPostExecute(result);
        _listener.onFinish(getHttpResponseCode(), _jsonArray);
    }

    public interface Listener {

        /**
         * Méthode invoquée à la fin de la requête sur le thread UI.
         *
         * @param responseCode Le code HTTP ou NULL si la requete a échoué
         * @param jsonArray Une réponse JSON ou NULL si le parse a échoué
         */
        void onFinish(@Nullable Integer responseCode,
                      @Nullable JSONArray jsonArray);
    }
}
