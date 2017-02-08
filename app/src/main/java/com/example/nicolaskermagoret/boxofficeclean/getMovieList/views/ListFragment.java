package com.example.nicolaskermagoret.boxofficeclean.getMovieList.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.net.RestApi;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.net.RestApiImpl;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters.BasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters.ListPresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters.Presenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase.GetMovieList;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase.UseCase;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseBaseViewModel;


public class ListFragment extends Fragment implements BaseView {

    public static final String TAG = ListFragment.class.getSimpleName();

    private Presenter presenter;
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
        final UseCase useCase = new GetMovieList(restApi);

        this.presenter = new ListPresenter(getContext(), useCase);

        this.setHasOptionsMenu(true);

        this.root = inflater.inflate(R.layout.list_fragment_layout, container, false);
        this.bindView(root);
        this.initRecyclerView();
        this.drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        this.navigationView = (NavigationView)getActivity().findViewById(R.id.left_drawer);

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
        this.presenter.onViewAttached(this);
        this.presenter.refreshResponse("popular");
    }

    @Override
    public void onError(String msg) {
        super.onStop();
        this.presenter.onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
        this.presenter = null;
    }

    @Override
    public void setResponse(ResponseBaseViewModel viewModel) {
        this.viewModel = viewModel;
        this.listAdapter = new ListAdapter(this.viewModel.getMovieList());
        recyclerView.setAdapter(this.listAdapter);
    }

    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    public void onNoNetwork() {

    }

    private void bindView(View rootView) {

    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch(menuItem.getItemId()) {
            case R.id.drawerPopular:
                this.presenter.refreshResponse("popular");
                break;
            case R.id.drawerRated:
                this.presenter.refreshResponse("rated");
                break;
            case R.id.drawerAction:
                this.presenter.refreshResponse("genre 28");
                break;
            case R.id.drawerAventure:
                this.presenter.refreshResponse("genre 12");
                break;
            case R.id.drawerAnimation:
                this.presenter.refreshResponse("genre 16");
                break;
            case R.id.drawerComedie:
                this.presenter.refreshResponse("genre 35");
                break;
            case R.id.drawerDocumentary:
                this.presenter.refreshResponse("genre 99");
                break;
            case R.id.drawerDrama:
                this.presenter.refreshResponse("genre 18");
                break;
            case R.id.drawerFamily:
                this.presenter.refreshResponse("genre 10751");
                break;
            case R.id.drawerHorror:
                this.presenter.refreshResponse("genre 27");
                break;
            case R.id.drawerSF:
                this.presenter.refreshResponse("genre 878");
                break;
            case R.id.drawerThriller:
                this.presenter.refreshResponse("genre 53");
                break;
            default:
                this.presenter.refreshResponse("popular");
        }
        drawerLayout.closeDrawers();
    }
}
