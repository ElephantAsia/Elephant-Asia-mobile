package fr.elefantasia.elefantasia.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by Stephane on 31/10/2016.
 */

public class RequestAsyncTask extends AsyncTask<Void, Void, Void> {

    protected static final String SERVER = "https://api.elefant.asia";
    private static final Integer CONNECTION_TIMEOUT = 5000;
    private static final Integer READ_TIMEOUT = 10000;

    private URL _url;
    private HttpURLConnection _httpURLConnection;
    private Integer _httpResponseCode;
    private InputStream _httpResponseStream;

    private Context _context;
    private boolean _running;

    /**
     * Constructeur.
     *
     * @param context Le contexte de l'activité
     */
    public RequestAsyncTask(@NonNull Context context) {
        _context = context;
    }

    /**
     * Exécute la tâche dans une thread pool
     */
    public void execute() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     *  Méthode invoquée sur le thread UI lorsque la requête HTTP démarre.
     */
    @Override
    protected void onPreExecute() {
        _running = true;
    }

    /**
     * Méthode invoquée sur un thread séparé pour exécuter la requête HTTP.
     *
     * @param params TOujours vide
     * @return Toujours NULL
     */
    @Override
    @Nullable
    protected Void doInBackground(@Nullable Void... params) {
        try {
            if (_httpURLConnection != null) {
                _httpURLConnection.connect();
                _httpResponseCode = _httpURLConnection.getResponseCode();
                _httpResponseStream = _httpURLConnection.getInputStream();
            }
        } catch (IOException e) {
            try {
                _httpResponseStream.close();
            } catch (IOException er) {
            }
            e.printStackTrace();
        }
        return (null);
    }

    /**
     * Méthode invoquée sur le thread UI lorsque la requête HTTP est terminée.
     *
     * @param result Toujours NULL
     */
    @Override
    protected void onPostExecute(Void result) {
        _running = false;
    }

    /**
     * Méthode invoquée sur le thread UI lorsque la requête HTTP est annulée.
     *
     * @param result Toujours NULL
     */
    @Override
    protected void onCancelled(@Nullable Void result) {
        // ...
        if (_httpURLConnection != null) {
            _httpURLConnection.disconnect();
        }
        _running = false;
    }

    /**
     * Retourne le contexte.
     *
     * @return Le contexte
     */
    @NonNull
    protected Context getContext() {
        return (_context);
    }

    /**
     * Permet de savoir si la tâche est en cours d'exécution
     *
     * @return TRUE ou FALSE
     */
    public boolean isRunning() {
        return (_running);
    }

    /**
     * Crée une URL
     *
     * @param url L'URL de la route
     */
    protected void setURL(String url) {
        try {
            _url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée une connexion HTTP à partir d'une URL
     */
    protected void createHttpConnection() {
        try {
            if (_url != null) {
                _httpURLConnection = (HttpURLConnection) _url.openConnection();
                _httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                _httpURLConnection.setReadTimeout(READ_TIMEOUT);
            } else {
                Log.i("createHttpConnection", "URL is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change le type de la requête HTTP
     *
     * @param method Le nom du type de la requête HTTP
     */
    protected void setRequestMethod(String method) {
        try {
            _httpURLConnection.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    /**
     * Si la tâche doit reçevoir de la donnée
     *
     * @param value TRUE ou FALSE
     */
    protected void setDoInput(boolean value) {
        _httpURLConnection.setDoInput(value);
    }

    /**
     * Si la tâche doit envoyer de la donnée.
     *
     * @param value TRUE ou FALSE
     */
    protected void setDoOutput(boolean value) {
        _httpURLConnection.setDoOutput(value);
    }

    /**
     * Retourne le code HTTP de la réponse du webservice.
     *
     * @return Le code HTTP de la réponse ou NULL si erreur
     */
    @Nullable
    protected Integer getHttpResponseCode() {
        return (_httpResponseCode);
    }

    /**
     * @return Retourne le flux de donnée de la requête HTTP.
     */
    @Nullable
    protected InputStream getHttpResponseStream() {
        return (_httpResponseStream);
    }

    protected void closeHttpClient() {
        _httpURLConnection.disconnect();
    }
}
