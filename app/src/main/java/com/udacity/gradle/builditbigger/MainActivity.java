package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.msf.jokelibrary.JokeTextActivity;
import com.udacity.gradle.builditbigger.task.JokeTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements JokeTask.OnJokeUpdateListener{

    @BindView(R.id.progress_main)
    ProgressBar mProgress;

    private JokeTask jokeTask;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        jokeTask = new JokeTask(mProgress,this);
        jokeTask.execute("JOKER");
    }


    @Override
    public void jokeListener(String joke) {
        Intent i = new Intent(MainActivity.this, JokeTextActivity.class);
        i.putExtra(Intent.EXTRA_TEXT, joke);
        startActivity(i);
    }
}
