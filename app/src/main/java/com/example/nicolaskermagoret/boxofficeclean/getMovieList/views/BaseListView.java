package com.example.nicolaskermagoret.boxofficeclean.getMovieList.views;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.viewmodels.ResponseBaseViewModel;

public interface BaseListView {

    void onError(String msg);

    void setResponse(ResponseBaseViewModel viewModel);

    void loading(boolean isLoading);

    void onNoNetwork();

}
