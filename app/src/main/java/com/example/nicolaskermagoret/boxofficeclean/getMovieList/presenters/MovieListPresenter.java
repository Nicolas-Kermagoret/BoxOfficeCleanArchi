package com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters;

import android.content.Context;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.usecase.GetMovieListBaseUseCase;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseBaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.BaseListView;
import com.laimiux.rxnetwork.RxNetwork;

import java.util.Locale;

import rx.Subscription;

public class MovieListPresenter implements ListBasePresenter, GetMovieListBaseUseCase.UseCaseListener {

    private Context context;
    private GetMovieListBaseUseCase getMovieListBaseUseCase;
    private boolean connected;

    private BaseListView view;

    private Subscription connectivityChangeSubscription;

    public MovieListPresenter(Context context, GetMovieListBaseUseCase getMovieListBaseUseCase) {
        this.context = context;
        this.connected = RxNetwork.getConnectivityStatus(this.context);
        this.getMovieListBaseUseCase = getMovieListBaseUseCase;
        this.getMovieListBaseUseCase.setUseCaseListener(this);
    }

    @Override
    public void onViewAttached(BaseListView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        this.context = null;
    }

    @Override
    public void setResponse(SearchResultEntity result) {
        this.view.loading(false);
        final ResponseBaseViewModel responseViewModel = new ResponseViewModel(result);
        this.view.setResponse(responseViewModel);
    }

    @Override
    public void error(String message) {
        if (this.view != null) {
            this.view.loading(false);
        }
    }

    @Override
    public void endTask(boolean isEmpty) {
        if (isEmpty) {
            this.view.setEmptyResponse();
        }
    }

    @Override
    public void refreshResponse(String search) {
        this.view.loading(true);
        this.getMovieListBaseUseCase.refreshResponse(Locale.getDefault().getLanguage(), search);
    }
}
