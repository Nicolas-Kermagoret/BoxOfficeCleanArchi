package com.example.nicolaskermagoret.boxofficeclean.getMovieList.presenters;

import com.example.nicolaskermagoret.boxofficeclean.getMovieList.views.ListFragment;

public interface Presenter extends BasePresenter<ListFragment>{

    void refreshResponse(String search);

}
