package com.example.nicolaskermagoret.boxofficeclean.search.views;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApiImpl;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.ListFragment;
import com.example.nicolaskermagoret.boxofficeclean.search.entity.SuggestionEntity;
import com.example.nicolaskermagoret.boxofficeclean.search.presenter.SuggestionBasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.search.presenter.SuggestionPresenter;
import com.example.nicolaskermagoret.boxofficeclean.search.usecase.SuggestionBaseUseCase;
import com.example.nicolaskermagoret.boxofficeclean.search.usecase.SuggestionUseCase;
import com.example.nicolaskermagoret.boxofficeclean.search.viewModel.SuggestionsBaseViewModel;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener, SearchBaseView {

    public static final String CURSOR_ROW = "COLUMNT_INTENT_DATA";

    private SearchView searchView;
    private ListFragment listFragment;
    private SuggestionBasePresenter suggestionPresenter;

    private CursorAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.searchFragment);

        final RestApi restApi = new RestApiImpl(this.getCacheDir(), this);
        final SuggestionBaseUseCase useCase = new SuggestionUseCase(restApi);
        this.suggestionPresenter = new SuggestionPresenter(useCase);
        this.suggestionPresenter.onViewAttached(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(this, SearchActivity.class);
        final MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        searchView.setIconified(false);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        suggestionAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);
        searchView.setSuggestionsAdapter(suggestionAdapter);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.isEmpty()) {
            return false;
        }

        if (listFragment != null) {
            listFragment.refreshResponse(getString(R.string.category_search) + " " + query);
        }

        if (searchView != null) {
            searchView.clearFocus();
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            this.suggestionPresenter.getSuggestions(newText);
        }
        return true;
    }

    @Override
    public void setSuggestions(SuggestionsBaseViewModel viewModel) {
        int i = 0;
        String[] columns = {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA
        };

        MatrixCursor cursor = new MatrixCursor(columns);

        for (SuggestionEntity entity : viewModel.getSuggestionList().getResults()) {
            String[] tmp = {Integer.toString(i), entity.getName(), CURSOR_ROW};
            cursor.addRow(tmp);
            if (i == 5) {
                break;
            }
            i++;
        }

        suggestionAdapter.changeCursor(cursor);

    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(
                position);
        String query = cursor.getString(cursor
                .getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
        if (listFragment != null) {
            listFragment.refreshResponse(getString(R.string.category_search) + " " + query);
        }

        searchView.setQuery(query, true);
        return true;
    }
}
