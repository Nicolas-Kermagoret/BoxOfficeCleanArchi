package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.views;

import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.viewmodels.MovieDetailBaseViewModel;

public interface BaseDetailsView {

    void onError(String msg);

    void setResponse(MovieDetailBaseViewModel viewModel);

    void loading(boolean isLoading);

    void onNoNetwork();

}