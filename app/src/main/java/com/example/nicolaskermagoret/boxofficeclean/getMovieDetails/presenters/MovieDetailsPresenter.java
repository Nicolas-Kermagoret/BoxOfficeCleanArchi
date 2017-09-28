package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.presenters;

import android.content.Context;

import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.entity.MovieEntityFull;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.usecase.GetMovieDetailBaseUseCase;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels.MovieDetailBaseViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels.MovieDetailsViewModel;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.views.BaseDetailsView;
import com.laimiux.rxnetwork.RxNetwork;

import java.util.Locale;

import rx.Subscription;

public class MovieDetailsPresenter implements DetailsBasePresenter, GetMovieDetailBaseUseCase.UseCaseListener {

    private Context context;
    private GetMovieDetailBaseUseCase getMovieDetailBaseUseCase;
    private boolean connected;

    private BaseDetailsView view;

    private Subscription connectivityChangeSubscription;

    public MovieDetailsPresenter(Context context, GetMovieDetailBaseUseCase getMovieDetailBaseUseCase) {
        this.context = context;
        this.connected = RxNetwork.getConnectivityStatus(this.context);
        this.getMovieDetailBaseUseCase = getMovieDetailBaseUseCase;
        this.getMovieDetailBaseUseCase.setUseCaseListener(this);
    }

    @Override
    public void onViewAttached(BaseDetailsView view) {
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
    public void setResponse(MovieEntityFull movie) {
        this.view.loading(false);
        final MovieDetailBaseViewModel movieDetailBaseViewModel = new MovieDetailsViewModel(movie, context);
        this.view.setResponse(movieDetailBaseViewModel);
    }

    @Override
    public void error(String message) {
        if (this.view != null) {
            this.view.loading(false);
        }
    }

    @Override
    public void endTask() {

    }

    @Override
    public void refreshResponse(String id) {
        this.view.loading(true);
        this.getMovieDetailBaseUseCase.refreshResponse(Locale.getDefault().getLanguage(), id);
    }

}
