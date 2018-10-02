package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.msf.jokelibrary.JokeTextActivity;
import com.udacity.gradle.builditbigger.Util.IdlingResourceImp;
import com.udacity.gradle.builditbigger.task.JokeTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements JokeTask.OnJokeUpdateListener{

    @BindView(R.id.progress_main)
    ProgressBar mProgress;

    private JokeTask jokeTask;

    private IdlingResourceImp mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_to_replace,
                    new MainActivityFragment()).commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mProgress.setVisibility(View.VISIBLE);
        getJokeFromApi();
    }

    private void getJokeFromApi() {
        if(jokeTask != null){
            jokeTask.cancel(true);
        }
        setStateToIdlingResource(false);
        jokeTask = new JokeTask(mProgress,this);
        jokeTask.execute("JOKER");
    }

    private void setStateToIdlingResource(boolean state) {
        if(mIdlingResource != null){
            mIdlingResource.setIdleState(state);
        }
    }


    @Override
    public void jokeListener(String joke) {
        setStateToIdlingResource(true);
        Intent i = new Intent(MainActivity.this, JokeTextActivity.class);
        i.putExtra(Intent.EXTRA_TEXT, joke);
        startActivity(i);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResourceImp getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new IdlingResourceImp();
        }
        return mIdlingResource;
    }
}
