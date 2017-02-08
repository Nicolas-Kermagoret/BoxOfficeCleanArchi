package com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters;

public interface BasePresenter<T> {

    void onViewAttached(T view);

    void onViewDetached();

    void onDestroy();

}
