package com.example.nicolaskermagoret.boxofficeclean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.ListFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ListFragment.newInstance(), ListFragment.TAG).commit();

    }
}
