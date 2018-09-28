package com.udacity.gradle.builditbigger.task;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class JokeTask extends AsyncTask<String, Void, String> {

    private static MyApi myApiInstance;
    private ProgressBar mProgressBar;
    private OnJokeUpdateListener mListener;

    public JokeTask(ProgressBar progressBar, OnJokeUpdateListener listener) {
        this.mProgressBar = progressBar;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        if(myApiInstance == null){
            myApiInstance = getMyApiBuilder().build();
        }
        String name = params[0];
        try {
            return myApiInstance.sayHi(name).execute().getData();
        } catch (IOException e) {
            return "An exception has occurred";
        }
    }

    private MyApi.Builder getMyApiBuilder() {
        String ENDPOINT_EMULATOR = "http://10.0.2.2:8080/_ah/api/";
        return new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                                .setRootUrl(ENDPOINT_EMULATOR)
                                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                    @Override
                                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                        request.setDisableGZipContent(true);
                                    }
                                });
    }

    @Override
    protected void onPostExecute(String joke) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mListener.jokeListener(joke);
    }

    public interface OnJokeUpdateListener{
        void jokeListener(String joke);
    }
}
