package com.example.nicolaskermagoret.boxofficeclean;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nicolaskermagoret.boxofficeclean.about.ui.activity.AboutActivity;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.ListFragment;
import com.example.nicolaskermagoret.boxofficeclean.search.views.SearchActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) this.findViewById(R.id.left_drawer);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        selectDrawerItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        FragmentManager fm = getSupportFragmentManager();
        this.listFragment = (ListFragment) fm.findFragmentByTag(ListFragment.TAG);
        if (this.listFragment == null) {
            this.listFragment = ListFragment.newInstance();
            Bundle args = new Bundle();
            args.putString(ListFragment.QUERY, getString(R.string.category_popular));
            this.listFragment.setArguments(args);
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, this.listFragment, ListFragment.TAG).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawerPopular:
                this.listFragment.refreshResponse(getString(R.string.category_popular));
                break;
            case R.id.drawerRated:
                this.listFragment.refreshResponse(getString(R.string.category_rated));
                break;
            case R.id.drawerAction:
                this.listFragment.refreshResponse(getString(R.string.category_action));
                break;
            case R.id.drawerAventure:
                this.listFragment.refreshResponse(getString(R.string.category_aventure));
                break;
            case R.id.drawerAnimation:
                this.listFragment.refreshResponse(getString(R.string.category_animation));
                break;
            case R.id.drawerComedie:
                this.listFragment.refreshResponse(getString(R.string.category_comedy));
                break;
            case R.id.drawerDocumentary:
                this.listFragment.refreshResponse(getString(R.string.category_documentary));
                break;
            case R.id.drawerDrama:
                this.listFragment.refreshResponse(getString(R.string.category_drama));
                break;
            case R.id.drawerFamily:
                this.listFragment.refreshResponse(getString(R.string.category_family));
                break;
            case R.id.drawerHorror:
                this.listFragment.refreshResponse(getString(R.string.category_horror));
                break;
            case R.id.drawerSF:
                this.listFragment.refreshResponse(getString(R.string.category_sf));
                break;
            case R.id.drawerThriller:
                this.listFragment.refreshResponse(getString(R.string.category_thriller));
                break;
//            case R.id.drawerAccount:
//                menuItem.setChecked(false);
//                break;
            case R.id.drawerAbout:
                menuItem.setChecked(false);
                startActivity(new Intent(this, AboutActivity.class));
                break;
            default:
                this.listFragment.refreshResponse(getString(R.string.category_popular));
        }
    }
}
