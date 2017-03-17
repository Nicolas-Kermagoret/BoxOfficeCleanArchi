package com.example.nicolaskermagoret.boxofficeclean.getMovieList.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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


    private ListAdapter listAdapter;

    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RestApi restApi = new RestApiImpl();
        final GetMovieListBaseUseCase getMovieListBaseUseCase = new GetMovieList(restApi);
        Bundle args = getArguments();
        String query = args.getString("query");

        this.listBasePresenter = new MovieListPresenter(getContext(), getMovieListBaseUseCase);

        this.setHasOptionsMenu(true);

        this.root = inflater.inflate(R.layout.list_fragment_layout, container, false);
        this.initRecyclerView();
        this.listBasePresenter.onViewAttached(this);
        if (!query.isEmpty()) {
            this.listBasePresenter.refreshResponse(query);
        }

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

    @Override
    public void itemClicked(String id) {
        MovieDetailsActivity.launchMovieDetailsActivity(getContext(), id);
    }

    public void refreshResponse(String query) {
        this.listBasePresenter.refreshResponse(query);
    }
}
