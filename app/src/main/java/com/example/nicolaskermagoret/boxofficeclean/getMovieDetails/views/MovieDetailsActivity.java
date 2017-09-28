package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nicolaskermagoret.boxofficeclean.R;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";

    private ShareActionProvider shareActionProvider;

    private String id;
    private String title;

    public static void launchMovieDetailsActivity(Context context, String id, String title) {
        final Intent movieIntent = new Intent(context, MovieDetailsActivity.class);
        movieIntent.putExtra(EXTRA_ID, id);
        movieIntent.putExtra(EXTRA_TITLE, title);
        context.startActivity(movieIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        this.id = intent.getStringExtra(EXTRA_ID);
        this.title = intent.getStringExtra(EXTRA_TITLE);
        args.putString(EXTRA_ID, id);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame_details, fragment)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case R.id.menu_item_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = this.getString(R.string.share_message, title, id);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, this.getString(R.string.share)));
                return true;

            case android.R.id.home:
                finish(); // close this activity and return to preview activity (if there is any)


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }
}
