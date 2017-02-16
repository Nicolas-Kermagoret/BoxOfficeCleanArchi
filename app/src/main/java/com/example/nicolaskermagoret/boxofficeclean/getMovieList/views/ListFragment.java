package com.example.nicolaskermagoret.boxofficeclean.getMovieList.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.common.net.RestApiImpl;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.views.MovieDetailsActivity;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters.ListBasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters.MovieListPresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase.GetMovieList;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase.GetMovieListBaseUseCase;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseBaseViewModel;


public class ListFragment extends Fragment implements BaseListView, ListAdapter.MoviesListItemListener {

    public static final String TAG = ListFragment.class.getSimpleName();

    private ListBasePresenter listBasePresenter;
    private RecyclerView recyclerView;
    private ResponseBaseViewModel viewModel;
    private View root;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ListAdapter listAdapter;

    public static Fragment newInstance() {
        return new ListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RestApi restApi = new RestApiImpl();
        final GetMovieListBaseUseCase getMovieListBaseUseCase = new GetMovieList(restApi);

        this.listBasePresenter = new MovieListPresenter(getContext(), getMovieListBaseUseCase);

        this.setHasOptionsMenu(true);

        this.root = inflater.inflate(R.layout.list_fragment_layout, container, false);
        this.initRecyclerView();
        this.drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        this.navigationView = (NavigationView) getActivity().findViewById(R.id.left_drawer);

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

        return this.root;
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) this.root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.listBasePresenter.onViewAttached(this);
        this.listBasePresenter.refreshResponse("popular");
    }

    @Override
    public void onError(String msg) {
        super.onStop();
        this.listBasePresenter.onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.listBasePresenter.onDestroy();
        this.listBasePresenter = null;
    }

    @Override
    public void setResponse(ResponseBaseViewModel viewModel) {
        this.viewModel = viewModel;
        this.listAdapter = new ListAdapter(this.viewModel.getMovieList(), this);
        recyclerView.setAdapter(this.listAdapter);
    }

    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    public void onNoNetwork() {

    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch (menuItem.getItemId()) {
            case R.id.drawerPopular:
                this.listBasePresenter.refreshResponse("popular");
                break;
            case R.id.drawerRated:
                this.listBasePresenter.refreshResponse("rated");
                break;
            case R.id.drawerAction:
                this.listBasePresenter.refreshResponse("genre 28");
                break;
            case R.id.drawerAventure:
                this.listBasePresenter.refreshResponse("genre 12");
                break;
            case R.id.drawerAnimation:
                this.listBasePresenter.refreshResponse("genre 16");
                break;
            case R.id.drawerComedie:
                this.listBasePresenter.refreshResponse("genre 35");
                break;
            case R.id.drawerDocumentary:
                this.listBasePresenter.refreshResponse("genre 99");
                break;
            case R.id.drawerDrama:
                this.listBasePresenter.refreshResponse("genre 18");
                break;
            case R.id.drawerFamily:
                this.listBasePresenter.refreshResponse("genre 10751");
                break;
            case R.id.drawerHorror:
                this.listBasePresenter.refreshResponse("genre 27");
                break;
            case R.id.drawerSF:
                this.listBasePresenter.refreshResponse("genre 878");
                break;
            case R.id.drawerThriller:
                this.listBasePresenter.refreshResponse("genre 53");
                break;
            default:
                this.listBasePresenter.refreshResponse("popular");
        }
        drawerLayout.closeDrawers();
    }

    @Override
    public void itemClicked(String id) {
        MovieDetailsActivity.launchMovieDetailsActivity(getContext(), id);
    }
}
