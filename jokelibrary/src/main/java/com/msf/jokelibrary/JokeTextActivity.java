package com.msf.jokelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class JokeTextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_text);
        Toolbar toolbar = findViewById(R.id.toolbar_library);
        toolbar.setTitle(getString(R.string.joketextactivity_title));
        setSupportActionBar(toolbar);

        TextView textViewJoke = findViewById(R.id.text_joke);

        if (savedInstanceState == null) {
            String joke = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            textViewJoke.setText(joke);
        }
    }

}
