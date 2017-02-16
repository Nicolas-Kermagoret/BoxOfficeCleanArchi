package com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.presenters;

import com.example.nicolaskermagoret.boxofficeclean.common.presenters.BasePresenter;
import com.example.nicolaskermagoret.boxofficeclean.getMovieDetails.views.BaseDetailsView;

public interface DetailsBasePresenter extends BasePresenter<BaseDetailsView> {

    void refreshResponse(String search);

}